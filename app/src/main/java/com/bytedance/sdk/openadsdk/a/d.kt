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
import android.os.Binder
import android.os.Bundle
import android.os.Looper
import android.widget.LinearLayout
import android.widget.ScrollView
import com.fuckcoolapk.BuildConfig
import com.fuckcoolapk.PACKAGE_NAME
import com.fuckcoolapk.module.*
import com.fuckcoolapk.utils.*
import com.fuckcoolapk.utils.ktx.MethodHookParam
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterAllMethods
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.view.TextViewForHook
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.sfysoft.android.xposed.shelling.XposedShelling
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import io.noties.markwon.Markwon
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.system.exitProcess


class d : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        if (lpparam?.packageName == PACKAGE_NAME) {
            try {
                if (XposedHelpers.findClassIfExists("com.wrapper.proxyapplication.WrapperProxyApplication", lpparam.classLoader) != null) {
                    XposedHelpers.findClass("com.wrapper.proxyapplication.WrapperProxyApplication", lpparam.classLoader)
                            .hookAfterMethod("attachBaseContext", Context::class.java) {
                                //获取 context
                                CoolapkContext.context = it.args[0] as Context
                                //获取 classloader
                                CoolapkContext.classLoader = CoolapkContext.context.classLoader
                                init(lpparam, it)
                            }
                } else if (XposedHelpers.findClassIfExists("com.wind.xpatch.proxy.XpatchProxyApplication", lpparam.classLoader) != null) {
                    XposedHelpers.findClass("com.wind.xpatch.proxy.XpatchProxyApplication", lpparam.classLoader)
                            .hookAfterMethod("attachBaseContext", Context::class.java) {
                                //获取 context
                                CoolapkContext.context = it.args[0] as Context
                                //获取 classloader
                                CoolapkContext.classLoader = CoolapkContext.context.classLoader
                                init(lpparam, it)
                            }
                } else {
                    XposedHelpers.findAndHookMethod(Application::class.java, "attach", Context::class.java, object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            super.afterHookedMethod(param)
                            //获取 context
                            CoolapkContext.context = param.args[0] as Context
                            //获取 classloader
                            CoolapkContext.classLoader = CoolapkContext.context.classLoader
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
        FileUtil.getParamAvailability(param, Binder.getCallingPid())
        LogUtil.d(CoolapkContext.context.packageName)
        //获取 activity
        XposedHelpers.findClass("android.app.Instrumentation", CoolapkContext.classLoader)
                .hookAfterAllMethods("newActivity") { activityParam ->
                    CoolapkContext.activity = activityParam.result as Activity
                    LogUtil.d("Current activity: ${CoolapkContext.activity.javaClass}")
                }
        //首次使用&Appcenter&检查更新
        try {
            XposedHelpers.findClass("com.coolapk.market.view.main.MainActivity", CoolapkContext.classLoader)
                    .hookAfterMethod("onCreate", Bundle::class.java) {
                        //appcenter
                        AppCenter.start(CoolapkContext.activity.application, "44ab5622-fbcb-4fcd-9eff-04dab0061d30", Analytics::class.java, Crashes::class.java)
                        if (CoolapkContext.loginSession.callMethod("isLogin") as Boolean) {
                            Analytics.trackEvent("user ${CoolapkContext.loginSession.callMethod("getUserName") as String}", HashMap<String, String>().apply {
                                put("userName", CoolapkContext.loginSession.callMethod("getUserName") as String)
                                put("UID", CoolapkContext.loginSession.callMethod("getUid") as String)
                                put("isAdmin", (CoolapkContext.loginSession.callMethod("isAdmin") as Boolean).toString())
                            })
                        }
                        //首次使用
                        if (OwnSP.ownSP.getBoolean("isFirstUse", true)) {
                            val normalDialog = AlertDialog.Builder(CoolapkContext.activity)
                            normalDialog.setTitle("欢迎")
                            normalDialog.setMessage("你来了？\n这是一份送给316和423的礼物。其功能是默认关闭的，如需使用，请转到设置页打开。")
                            normalDialog.setOnDismissListener { OwnSP.set("isFirstUse", false) }
                            normalDialog.show()
                        }
                        //检查更新
                        if (OwnSP.ownSP.getBoolean("checkUpdate",true)){
                            OkHttpClient.Builder().build().newCall(Request.Builder()
                                    .url("https://api.github.com/repos/ejiaogl/FuckCoolapk/releases/latest")
                                    .get()
                                    .build())
                                    .enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            LogUtil.e(e)
                                        }

                                        override fun onResponse(call: Call, response: Response) {
                                            try {
                                            val jsonObject = JSONObject(response.body.string())
                                            if ((jsonObject.getString("tag_name").toInt() > BuildConfig.VERSION_CODE) and (!jsonObject.getBoolean("prerelease"))) {
                                                Looper.prepare()
                                                val normalDialog = AlertDialog.Builder(CoolapkContext.activity)
                                                normalDialog.setTitle("Fuck Coolapk 有新版本可用")
                                                normalDialog.setMessage("${jsonObject.getString("name")}\n${jsonObject.getString("body")}")
                                                normalDialog.setPositiveButton("查看") { dialogInterface: DialogInterface, i: Int -> CoolapkContext.activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/releases"))) }
                                                normalDialog.show().getButton(Dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
                                                Looper.loop()
                                            }
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
        //hook 设置
        HookSettings().init()
        if (OwnSP.ownSP.getBoolean("agreeEULA", false)) {
            //脱壳
            XposedShelling.runShelling(lpparam)
            //去除开屏广告
            RemoveStartupAds().init()
            //去除信息流广告
            RemoveFeedAds().init()
            //去除底部多余按钮
            HideBottomButton().init()
            //允许在应用列表内卸载酷安
            AllowUninstallCoolapk().init()
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
        }
    }
}

fun showEulaDialog(activity: Activity, eula: String) {
    val markwon = Markwon.builder(CoolapkContext.activity).build()
    var time = 30
    val dialogBuilder = AlertDialog.Builder(activity)
    val linearLayout = LinearLayout(activity).apply {
        orientation = LinearLayout.VERTICAL
        setPadding(dp2px(CoolapkContext.context, 20f), dp2px(CoolapkContext.context, 10f), dp2px(CoolapkContext.context, 20f), dp2px(CoolapkContext.context, 5f))
        //addView(TextViewForHook(CoolapkContext.activity, "Fuck Coolapk 最终用户许可协议与隐私条款", TextViewForHook.titleSize, null))
        val message = TextViewForHook(CoolapkContext.activity, "", TextViewForHook.textSize, null)
        markwon.setMarkdown(message, eula)
        addView(message)
    }
    dialogBuilder.setView(ScrollView(CoolapkContext.activity).apply {
        overScrollMode = 2
        addView(linearLayout)
    })
    dialogBuilder.setNegativeButton("不同意") { dialogInterface: DialogInterface, i: Int ->
        LogUtil.toast("请转到 Xposed 管理器关闭此模块")
        Thread {
            Thread.sleep(1500)
            exitProcess(0)
        }.start()
    }
    dialogBuilder.setPositiveButton("我已阅读并同意本协议") { dialogInterface: DialogInterface, i: Int ->
        OwnSP.set("agreeEULA", true)
        LogUtil.toast("重新启动 酷安 后生效")
        Thread {
            Thread.sleep(1500)
            exitProcess(0)
        }.start()
    }
    dialogBuilder.setCancelable(false)
    val dialog = dialogBuilder.show()
    val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
    val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
    when (isNightMode(CoolapkContext.context)) {
        false -> run {
            negativeButton.setTextColor(Color.BLACK)
            positiveButton.setTextColor(Color.BLACK)
        }
        true -> run {
            negativeButton.setTextColor(Color.WHITE)
            positiveButton.setTextColor(Color.WHITE)
        }
    }
    positiveButton.isClickable = false
    Thread {
        do {
            positiveButton.post { positiveButton.text = "我已阅读并同意本协议 (${time}s)" }
            Thread.sleep(1000)
        } while (--time != 0)
        positiveButton.post {
            positiveButton.text = "我已阅读并同意本协议"
            positiveButton.isClickable = true
        }
    }.start()
}
