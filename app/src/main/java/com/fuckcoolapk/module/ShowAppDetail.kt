package com.fuckcoolapk.module

import android.graphics.Color
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.getObjectField
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import okhttp3.ResponseBody
import org.json.JSONObject
import java.util.*

class ShowAppDetail {
    fun init() {
        if (OwnSP.ownSP.getBoolean("showAppDetail",false)){
            var responseBody = ""
            var okHttpHook: XC_MethodHook.Unhook? = null
            "com.coolapk.market.manager.DataManager$1"
                    .hookBeforeMethod("call", "okhttp3.ResponseBody") {
                        okHttpHook = "okhttp3.ResponseBody"
                                .hookAfterMethod("string") {
                                    responseBody = it.result as String
                                }
                    }
            "com.coolapk.market.manager.DataManager$1"
                    .hookAfterMethod("call", "okhttp3.ResponseBody") {
                        okHttpHook!!.unhook()
                    }

            "com.coolapk.market.view.node.app.AppNodeViewPart"
                    .hookAfterMethod("onBindToContent", "com.coolapk.market.model.ServiceApp") {
                        val data = JSONObject(responseBody).getJSONObject("data")
                        val appHeaderView = (it.thisObject.callMethod("getBinding")!!.getObjectField("headerContainer") as LinearLayout).getChildAt(1) as LinearLayout
                        (appHeaderView.getChildAt(1) as TextView).apply {
                            text = "$text\n${data.optString("pubStatusText")} ${if (data.optInt("isDownloadFromYybByPy") == 1) "从应用宝服务器下载" else ""}"
                        }
                    }
        }
    }
}