package com.fuckcoolapk.module

import android.content.Context
import android.net.Uri
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callStaticMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import com.fuckcoolapk.utils.ktx.replaceMethod
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.net.URLDecoder
import java.net.URLEncoder

class DisableURLTracking {
    fun init() {
        //if (OwnSP.ownSP.getBoolean("disableURLTracking", false)) {
        "com.coolapk.market.manager.ActionManager"
            .hookBeforeMethod("startBrowserActivity", Context::class.java, String::class.java) {
                val url = it.args[1] as String
                it.args[1] = if ("https://www.coolapk.com/link" in url) URLDecoder.decode(url.substring(url.indexOf("https://www.coolapk.com/link") + 33), "utf-8") else url

            }
        "com.coolapk.market.util.UriActionUtils\$WebUriAction".replaceMethod("takeAction", Context::class.java, Uri::class.java, Array<Object>::class.java){
            "com.coolapk.market.manager.ActionManager".callStaticMethod("startBrowserActivity", it.args[0], (it.args[1] as Uri).toString())
        }
        //}
    }
}