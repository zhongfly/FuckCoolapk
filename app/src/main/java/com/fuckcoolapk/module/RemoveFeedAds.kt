package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XposedHelpers
import org.json.JSONObject

class RemoveFeedAds {
    private val onAdLoadListener = "com.coolapk.market.view.ad.OnAdLoadListener"
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeFeedAds", false)) {
            XposedHelpers.findClass("com.coolapk.market.view.ad.toutiao.TTFeedSelfDrawAd", CoolapkContext.classLoader)
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            XposedHelpers.findClass("com.coolapk.market.view.ad.tencent.GDTFeedSelfDrawAD", CoolapkContext.classLoader)
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            XposedHelpers.findClass("com.coolapk.market.view.ad.toutiao.TTFeedAd", CoolapkContext.classLoader)
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            XposedHelpers.findClass("com.coolapk.market.view.ad.tencent.GDTFeedAd", CoolapkContext.classLoader)
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            XposedHelpers.findClass("com.coolapk.market.view.ad.tencent.GDTFeedAd2", CoolapkContext.classLoader)
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            XposedHelpers.findClass("com.coolapk.market.remote.EntityListResponseBodyConverter", CoolapkContext.classLoader)
                    .hookBeforeMethod("convert", "okhttp3.ResponseBody") {
                        var responseBody = it.args[0]
                        val cMediaType = XposedHelpers.findClass("okhttp3.MediaType", responseBody.javaClass.classLoader)
                        val cResponseBody = XposedHelpers.findClass("okhttp3.ResponseBody", responseBody.javaClass.classLoader)
                        var result = XposedHelpers.callMethod(responseBody, "string") as String
                        val json = JSONObject(result)
                        val dataArr = json.getJSONArray("data")
                        val adObj = dataArr.getJSONObject(0)
                        if ("8639" == adObj.getString("entityId")) {
                            dataArr.remove(0)
                            json.put("data", dataArr)
                        }
                        result = json.toString()
                        val mediaType = XposedHelpers.callStaticMethod(cMediaType, "parse", "application/json")
                        responseBody = XposedHelpers.callStaticMethod(cResponseBody, "create", mediaType, result)
                        it.args[0] = responseBody
                    }
        }
    }
}