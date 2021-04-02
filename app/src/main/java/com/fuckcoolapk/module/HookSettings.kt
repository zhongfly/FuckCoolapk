package com.fuckcoolapk.module

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import com.fuckcoolapk.BuildConfig
import com.fuckcoolapk.MODULE_TARGET_VERSION
import com.fuckcoolapk.PACKAGE_NAME
import com.fuckcoolapk.SP_NAME
import com.fuckcoolapk.utils.*
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.callStaticMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import com.fuckcoolapk.view.*
import io.noties.markwon.Markwon
import org.json.JSONObject
import java.io.File
import java.util.*
import kotlin.random.Random
import kotlin.system.exitProcess

class HookSettings {
    lateinit var settingActivity: Activity
    lateinit var dataList: java.util.List<Any>
    private var isOpen = false

    fun init() {
        "com.coolapk.market.view.settings.VXSettingsFragment"
                .hookBeforeMethod("initData") {
                    settingActivity = CoolContext.activity
                    val fuckCoolapkHolderItem = "com.coolapk.market.model.HolderItem"
                            .callStaticMethod("newBuilder")
                            ?.callMethod("entityType", "holder_item")
                            ?.callMethod("string", if (OwnSP.ownSP.getBoolean("agreeEULA", false)) "Fuck Coolapk" else "Fuck Coolapk（未激活）")
                            ?.callMethod("intValue", 233)
                            ?.callMethod("build")
                    val lineHolderItem = "com.coolapk.market.model.HolderItem"
                            .callStaticMethod("newBuilder")
                            ?.callMethod("entityType", "holder_item")
                            ?.callMethod("intValue", 14)
                            ?.callMethod("build")
                    dataList = (it.thisObject.callMethod("getDataList") as java.util.List<Any>).apply {
                        add(fuckCoolapkHolderItem)
                        add(lineHolderItem)
                    }
                }
        "com.coolapk.market.view.settings.VXSettingsFragment\$onCreateViewHolder$1"
                .hookBeforeMethod("onItemClick", "androidx.recyclerview.widget.RecyclerView\$ViewHolder", View::class.java) {
                    val intValue = dataList.get(it.args[0].callMethod("getAdapterPosition") as Int).callMethod("getIntValue")
                    if ((intValue == 233) and !isOpen) {
                        if (OwnSP.ownSP.getBoolean("agreeEULA", false)) {
                            showSettingsDialog()
                        } else {
                            GetUtil().sendGet("https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@master/EULA.md") { result: String -> showEulaDialog(result) }
                        }
                    }
                }
    }

    private fun showSettingsDialog() {
        val dialogBuilder = AlertDialog.Builder(settingActivity)
        dialogBuilder.setView(ScrollView(settingActivity).apply {
            overScrollMode = 2
            addView(LinearLayout(settingActivity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 10f), dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 5f))
                addView(FuckTextView.Builder(CoolContext.context) {
                    text = "Fxxx Cxxxxxx"
                    size = FuckTextView.titleSize
                    //setOnClickListener { settingActivity.startActivity(Chucker.getLaunchIntent(CoolContext.context)) }
                }.build())
                addView(FuckTextView.Builder {
                    text = "${BuildConfig.VERSION_NAME} ${BuildConfig.VERSION_CODE} ${BuildConfig.BUILD_TYPE} ${if (OwnSP.ownSP.getBoolean("isXpatch", false)) " for xpatch" else ""}\nTarget Version: $MODULE_TARGET_VERSION"
                }.build())
                addView(AdjustImageView.Builder {
                    setUrl("https://cdn.jsdelivr.net/gh/ejiaogl/FuckCoolapk@316/art/316-cover.png") {}
                    setOnClickListener { settingActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ejiaogl/FuckCoolapk/issues/13"))) }
                }.build())
                addView(FuckTextView.Builder {
                    text = "功能"
                    color = getColorFixWithHashtag(::getColorAccent)
                    size = FuckTextView.title2Size
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除启动广告"
                    key = "removeStartupAds"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除启动广告（Alpha）"
                    key = "removeStartupAds2"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除信息流广告（Alpha）"
                    key = "removeFeedAds"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除帖子下方广告（Alpha）"
                    key = "removeBannerAds"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除底部按钮（Alpha）"
                    key = "hideBottomButton"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "去除动态审核水印"
                    key = "removeAuditWatermark"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "允许在应用列表内卸载酷安"
                    key = "allowUninstallCoolapk"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "在应用详情页显示更多信息"
                    key = "showAppDetail"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "对动态开启 Markdown（Alpha）"
                    key = "enableMarkdown"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "对私信开启反和谐"
                    toastText = "通过自动替换相似字来达到反和谐的效果，不能保证一定有效。\n请勿滥用，请勿用于除私信外的其他地方，否则后果自负。"
                    key = "antiMessageCensorship"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "加强私信反和谐效果"
                    toastText = "需同时开启「对私信开启反和谐」"
                    key = "enhanceAntiMessageCensorship"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "管理员模式"
                    toastText = "仅供娱乐，不会有实际效果。\n慎重开启，开启后很有可能导致你号没了！"
                    key = "adminMode"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "关闭链接追踪"
                    key = "disableURLTracking"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "关闭 Umeng"
                    key = "disableUmeng"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "关闭 Bugly"
                    key = "disableBugly"
                }.build())
                addView(FuckTextView.Builder {
                    text = "自定义水印"
                    setOnClickListener { showWaterMarkDialog() }
                }.build())
                addView(FuckTextView.Builder {
                    text = "其他"
                    color = getColorFixWithHashtag(::getColorAccent)
                    size = FuckTextView.title2Size
                }.build())
                addView(FuckSwitch.Builder {
                    text = "检查更新"
                    defaultState = true
                    key = "checkUpdate"
                }.build())
                addView(FuckTextView.Builder {
                    text = "调试"
                    color = getColorFixWithHashtag(::getColorAccent)
                    size = FuckTextView.title2Size
                }.build())
                addView(FuckSwitch.Builder {
                    text = "对 酷安 进行脱壳"
                    toastText = "不适用于较新的 Android 版本。\n重启应用后开始脱壳，文件存放在 /data/data/com.coolapk.market/fuck_coolapk_shell。"
                    key = "shouldShelling"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "输出调试 Toast"
                    key = "showLogToast"
                }.build())
                addView(FuckTextView.Builder {
                    text = "生成随机 Token"
                    setOnClickListener {
                        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText("token", getAS()))
                        LogUtil.toast("已复制到剪贴板（Call Native Method)")
                    }
                    setOnLongClickListener {
                        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText("token", getAS2()))
                        LogUtil.toast("已复制到剪贴板")
                        true
                    }
                }.build())
                if (BuildConfig.DEBUG)
                    addView(FuckTextView.Builder {
                        text = "初始化状态"
                        setOnClickListener {
                            File("/data/data/$PACKAGE_NAME/shared_prefs/${SP_NAME}.xml").delete()
                            exitProcess(0)
                        }
                    }.build())
                addView(FuckTextView.Builder {
                    text = "信息"
                    color = getColorFixWithHashtag(::getColorAccent)
                    size = FuckTextView.title2Size
                }.build())
                addView(FuckTextView.Builder {
                    text = "背景故事"
                    url = "https://github.com/ejiaogl/FuckCoolapk/wiki/Background-information"
                }.build())
                addView(FuckTextView.Builder {
                    text = "Telegram"
                    url = "https://t.me/fuck_coolapk"
                }.build())
                addView(FuckTextView.Builder {
                    text = "GitHub"
                    url = "https://github.com/ejiaogl/FuckCoolapk"
                }.build())
                addView(FuckTextView.Builder {
                    text = "FAQ"
                    url = "https://github.com/ejiaogl/FuckCoolapk/wiki/FAQ"
                }.build())
            })
        })
        dialogBuilder.setPositiveButton("重启应用") { dialogInterface: DialogInterface, i: Int -> exitProcess(0) }
        dialogBuilder.show().apply {
            getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
        }
    }

    private fun showWaterMarkDialog() {
        val waterMarkImageView = AdjustImageView.Builder {
            refreshWaterMarkImageView(this)
        }.build()
        val dialogBuilder = AlertDialog.Builder(settingActivity)
        dialogBuilder.setView(ScrollView(settingActivity).apply {
            overScrollMode = 2
            addView(LinearLayout(settingActivity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 10f), dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 5f))
                addView(FuckTextView.Builder(CoolContext.context) {
                    text = "自定义水印"
                    size = FuckTextView.titleSize
                }.build())
                addView(FuckTextView.Builder {
                    text = "切换后重启应用生效，请务必确保能生成预览再投入使用。"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "开启自定义水印"
                    key = "enableCustomWatermark"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "全覆盖水印"
                    defaultState = true
                    key = "enableTileWatermark"
                }.build())
                addView(FuckSwitch.Builder {
                    text = "图片水印"
                    key = "enablePictureWatermark"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "水印文字（留空即为用户名）"
                    key = "waterMarkText"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "图片绝对位置"
                    key = "waterMarkPicturePath"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "相对图片横坐标（0-1）"
                    key = "waterMarkPositionX"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "相对图片纵坐标（0-1）"
                    key = "waterMarkPositionY"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "选转角度（0-360）"
                    key = "waterMarkRotation"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "透明度（0-255）"
                    key = "waterMarkAlpha"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "文字颜色（#xxxxxx）"
                    key = "waterMarkTextColor"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "文字大小（dp）"
                    key = "waterMarkTextSize"
                }.build())
                addView(FuckEditText.Builder {
                    hint = "图片相对大小（0-1）"
                    key = "waterMarkPictureSize"
                }.build())
                addView(waterMarkImageView)
            })
        })
        dialogBuilder.setPositiveButton("刷新预览", null)
        dialogBuilder.show().apply {
            getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
            getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener { refreshWaterMarkImageView(waterMarkImageView) }
        }
    }

    private fun showEulaDialog(eula: String) {
        val markwon = Markwon.builder(settingActivity).build()
        var time = 30
        val dialogBuilder = AlertDialog.Builder(settingActivity)
        dialogBuilder.setView(ScrollView(settingActivity).apply {
            overScrollMode = 2
            addView(LinearLayout(settingActivity).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 10f), dp2px(CoolContext.context, 20f), dp2px(CoolContext.context, 5f))
                val message = FuckTextView.Builder{}.build()
                markwon.setMarkdown(message, eula)
                addView(message)
            })
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
        negativeButton.setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
        positiveButton.setTextColor(Color.parseColor(getColorFixWithHashtag(::getColorAccent)))
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

    private fun refreshWaterMarkImageView(waterMarkImageView: AdjustImageView) = waterMarkImageView
            .setUrl("https://cdn.jsdelivr.net/gh/t0HiiBwn/CoolapkPhoto@main/${(OwnSP.ownSP.getInt("configPhotoIndexStart",0)..OwnSP.ownSP.getInt("configPhotoIndexEnd",24)).random()}.jpg") {
                try {
                    doWaterMark(it).setToImageView(it)
                } catch (e: Throwable) {
                    LogUtil.toast("生成水印时出现错误")
                    LogUtil.e(e)
                }
            }
}
