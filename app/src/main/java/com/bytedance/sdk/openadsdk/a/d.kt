package com.bytedance.sdk.openadsdk.a

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import com.drakeet.filter.AppFilter
import com.fuckcoolapk.BuildConfig
import com.fuckcoolapk.PACKAGE_NAME
import com.fuckcoolapk.module.*
import com.fuckcoolapk.utils.*
import com.fuckcoolapk.utils.ktx.*
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.sfysoft.android.xposed.shelling.XposedShelling
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class d : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName == PACKAGE_NAME) {
            try {
                if (XposedHelpers.findClassIfExists("com.wrapper.proxyapplication.WrapperProxyApplication", lpparam.classLoader) != null) {
                    "com.wrapper.proxyapplication.WrapperProxyApplication"
                            .hookAfterMethod("attachBaseContext", Context::class.java, classLoader = lpparam.classLoader) {
                                //获取 context
                                CoolContext.context = it.args[0] as Context
                                //获取 classloader
                                CoolContext.classLoader = CoolContext.context.classLoader
                                init(lpparam, it)
                            }
                } else if (XposedHelpers.findClassIfExists("com.wind.xpatch.proxy.XpatchProxyApplication", lpparam.classLoader) != null) {
                    "com.wind.xpatch.proxy.XpatchProxyApplication"
                            .hookAfterMethod("attachBaseContext", Context::class.java, classLoader = lpparam.classLoader) {
                                //获取 context
                                CoolContext.context = it.args[0] as Context
                                //获取 classloader
                                CoolContext.classLoader = CoolContext.context.classLoader
                                init(lpparam, it)
                                OwnSP.set("isXpatch", true)
                            }
                } else {
                    XposedHelpers.findAndHookMethod(Application::class.java, "attach", Context::class.java, object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            super.afterHookedMethod(param)
                            //获取 context
                            CoolContext.context = param.args[0] as Context
                            //获取 classloader
                            CoolContext.classLoader = CoolContext.context.classLoader
                            init(lpparam, param)
                        }
                    })
                }
            } catch (e: Throwable) {
                LogUtil.e(e)
            }
        }
    }

    private fun init(lpparam: XC_LoadPackage.LoadPackageParam, param: MethodHookParam) {
        //检查太极
        //FileUtil.getParamAvailability(param, Binder.getCallingPid())
        LogUtil.d(CoolContext.context.packageName)
        //获取 activity
        XposedHelpers.findClass("android.app.Instrumentation", CoolContext.classLoader)
                .hookAfterAllMethods("newActivity") { activityParam ->
                    CoolContext.activity = activityParam.result as Activity
                    LogUtil.d("Current activity: ${CoolContext.activity.javaClass}")
                }
        try {
            XposedHelpers.findClass("com.coolapk.market.view.main.MainActivity", CoolContext.classLoader)
                    .hookAfterMethod("onCreate", Bundle::class.java) {
                        val okHttpClient = OkHttpClient.Builder().build()
                        //appcenter
                        AppCenter.start(CoolContext.activity.application, "44ab5622-fbcb-4fcd-9eff-04dab0061d30", Analytics::class.java, Crashes::class.java)
                        if (CoolContext.loginSession.callMethod("isLogin") as Boolean) {
                            Analytics.trackEvent("user ${CoolContext.loginSession.callMethod("getUserName") as String}", HashMap<String, String>().apply {
                                put("userName", CoolContext.loginSession.callMethod("getUserName") as String)
                                put("UID", CoolContext.loginSession.callMethod("getUid") as String)
                                put("isAdmin", (CoolContext.loginSession.callMethod("isAdmin") as Boolean).toString())
                            })
                        }
                        //首次使用
                        if (OwnSP.ownSP.getBoolean("isFirstUse", true)) {
                            val normalDialog = AlertDialog.Builder(CoolContext.activity)
                            normalDialog.setTitle("欢迎")
                            normalDialog.setMessage("你来了？\n这是一份送给316和423的礼物。其功能是默认关闭的，如需使用，请转到设置页打开。")
                            normalDialog.setOnDismissListener { OwnSP.set("isFirstUse", false) }
                            normalDialog.show()
                        }
                        //获取配置
                        if (true) {//debug
                            //if ((System.currentTimeMillis() - OwnSP.ownSP.getLong("lastGetConfigTime", 0)) >= 86400000) {
                            //检查更新
                            if (OwnSP.ownSP.getBoolean("checkUpdate", true)) {
                                okHttpClient.newCall(Request.Builder()
                                        .url("https://api.github.com/repos/ejiaogl/FuckCoolapk/releases/latest")
                                        .get()
                                        .build())
                                        .enqueue(object : Callback {
                                            override fun onFailure(call: Call, e: IOException) {
                                                LogUtil.e(e)
                                            }

                                            override fun onResponse(call: Call, response: Response) {
                                                try {
                                                    val jsonObject = JSONObject(response.body!!.string())
                                                    if ((jsonObject.getString("tag_name").toInt() > BuildConfig.VERSION_CODE) and (!jsonObject.getBoolean("prerelease"))) {
                                                        Looper.prepare()
                                                        val normalDialog = AlertDialog.Builder(CoolContext.activity)
                                                        normalDialog.setTitle("Fuck Coolapk 有新版本可用")
                                                        normalDialog.setMessage("${jsonObject.getString("name")}\n${jsonObject.getString("body")}")
                                                        normalDialog.setPositiveButton("查看") { dialogInterface: DialogInterface, i: Int -> CoolContext.activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/releases"))) }
                                                        normalDialog.show().getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
                                                        Looper.loop()
                                                    }
                                                } catch (e: Throwable) {
                                                    LogUtil.e(e)
                                                }
                                            }
                                        })
                            }
                            okHttpClient.newCall(Request.Builder()
                                    .url("https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@master/config.json")
                                    .get()
                                    .build())
                                    .enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            LogUtil.e(e)
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            try {
                                                val jsonObject = JSONObject(response.body!!.string())
                                                OwnSP.set("configPhotoIndexStart", jsonObject.getJSONArray("photoIndex")[0])
                                                OwnSP.set("configPhotoIndexEnd", jsonObject.getJSONArray("photoIndex")[1])
                                                OwnSP.set("configBannerCard", jsonObject.getJSONArray("bannerCard").toString())
                                                OwnSP.set("lastGetConfigTime", System.currentTimeMillis())
                                            } catch (e: Throwable) {
                                                LogUtil.e(e)
                                            }
                                        }
                                    })
                        }
                    }
        } catch (e: Throwable) {
            LogUtil.e(e)
        }
        //关闭反 xposed
        DisableAntiXposed().init()
        //隐藏模块
        HideModule().init()
        //AppFilter by Darkeet
        AppFilter().init(lpparam.classLoader)
        //hook 设置
        HookSettings().init()
        if (OwnSP.ownSP.getBoolean("agreeEULA", false)) {
            //脱壳
            XposedShelling.runShelling(lpparam)
            //去除启动广告
            RemoveStartupAds().init()
            //去除信息流广告
            RemoveFeedAds().init()
            //去除帖子下方广告
            RemoveBannerAds().init()
            //去除底部多余按钮
            HideBottomButton().init()
            //允许在应用列表内卸载酷安
            AllowUninstallCoolapk().init()
            //插入头条 banner
            InsertHeadlineCard().init()
            //显示更详细的 App 信息
            ShowAppDetail().init()
            //关闭友盟
            DisableUmeng().init()
            //关闭 Bugly
            DisableBugly().init()
            //开启管理员模式
            EnableAdminMode().init()
            //去除动态审核的水印
            RemoveAuditWatermark().init()
            //临时去除图片水印
            ModifyPictureWatermark().init()
            //开启频道自由编辑
            EnableChannelEdit().init()
            //对私信开启反和谐
            AntiMessageCensorship().init()
            //关闭链接追踪
            DisableURLTracking().init()
            //开启动态 Markdown
            EnableMarkdown().init()
            //点击开发者平台直接跳转网页
            ModifyDeveloperCenter().init()
            //显示酷安 Debug Log
            ShowCoolapkDebugLog().init()
        }
    }
}
