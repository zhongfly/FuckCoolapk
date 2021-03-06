package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import com.fuckcoolapk.utils.ktx.setIntField
import de.robv.android.xposed.XposedHelpers
import org.json.JSONObject

class RemoveFeedAds {
    private val onAdLoadListener = "com.coolapk.market.view.ad.OnAdLoadListener"
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeFeedAds", false)) {
            "com.coolapk.market.view.ad.toutiao.TTFeedSelfDrawAd"
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            "com.coolapk.market.view.ad.tencent.GDTFeedSelfDrawAD"
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            "com.coolapk.market.view.ad.toutiao.TTFeedAd"
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            "com.coolapk.market.view.ad.tencent.GDTFeedAd"
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }
            "com.coolapk.market.view.ad.tencent.GDTFeedAd2"
                    .hookBeforeMethod("load", onAdLoadListener) {
                        XposedHelpers.setIntField(it.thisObject, "state", 1)
                    }

            "com.coolapk.market.remote.EntityListResponseBodyConverter"
                    .hookBeforeMethod("convert", "okhttp3.ResponseBody") {
                        val responseBody = it.args[0]
                        val mediaTypeClass = XposedHelpers.findClass("okhttp3.MediaType", CoolapkContext.classLoader)
                        val responseBodyClass = XposedHelpers.findClass("okhttp3.ResponseBody", CoolapkContext.classLoader)
                        val result = XposedHelpers.callMethod(responseBody, "string") as String
                        val json = JSONObject(result)
                        val dataArray = json.optJSONArray("data")
                        if (dataArray != null && dataArray.length() > 0) {
                            val adObject = dataArray.getJSONObject(0)
                            //去除信息流广告
                            if (adObject.optString("entityId") == "8639") {
                                dataArray.remove(0)
                            }
                            //屏蔽自营信息流广告
                            for (i in 0 until dataArray.length()) {
                                val extraData = dataArray.getJSONObject(i).optJSONObject("extraDataArr")
                                extraData?.let {
                                    val cardPageName = extraData.optString("cardPageName")
                                    if (cardPageName.endsWith("_AD")) dataArray.remove(i)
                                }
                            }
                            json.put("data", dataArray)
                            val mediaType = XposedHelpers.callStaticMethod(mediaTypeClass, "parse", "application/json")
                            it.args[0] = XposedHelpers.callStaticMethod(responseBodyClass, "create", mediaType, json.toString())
                        } else {
                            val mediaType = XposedHelpers.callStaticMethod(mediaTypeClass, "parse", "application/json")
                            it.args[0] = XposedHelpers.callStaticMethod(responseBodyClass, "create", mediaType, result)
                        }
                    }
        }
    }
}
