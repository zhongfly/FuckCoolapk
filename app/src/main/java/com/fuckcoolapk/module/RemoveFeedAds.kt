package com.fuckcoolapk.module

import android.util.Log
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
                        val responseBody = it.args[0]
                        val mediaTypeClass = XposedHelpers.findClass("okhttp3.MediaType", CoolapkContext.classLoader)
                        val responseBodyClass = XposedHelpers.findClass("okhttp3.ResponseBody", CoolapkContext.classLoader)
                        val result = XposedHelpers.callMethod(responseBody, "string") as String
                        val json = JSONObject(result)
//                        Log.e("ejiaogl", json.toString())
                        val dataArray = json.optJSONArray("data")
                        dataArray?.let {
                            //屏蔽自营信息流广告
                            for (i in 0 until dataArray.length()) {
                                if (i == 0) {
                                    val adObject = dataArray.getJSONObject(0)
                                    //去除信息流广告
                                    if (adObject.optString("entityId") == "8639") {
                                        dataArray.remove(0)
                                        continue
                                    }
                                }
                                val dataJson = dataArray.optJSONObject(i)
                                dataJson?.let {
                                    if (dataJson.getString("entityType") == "pear_goods") {
                                        dataArray.remove(i)
                                        return@let
                                    }
                                    if (dataJson.getString("title") == "猜你喜欢") {
                                        dataArray.remove(i)
                                        return@let
                                    }
//                                    Log.e("ejiaogl", dataJson.toString())
                                    val extraData = dataJson.optJSONObject("extraDataArr")
                                    extraData?.let {
                                        val cardPageName = extraData.optString("cardPageName")
                                        if (cardPageName.endsWith("_AD")) dataArray.remove(i)
                                        return@let
                                    }
//                                    if (dataJson.toString().contains("goods_buy_url")) dataArray.remove(i)
                                }
                            }
                            json.put("data", dataArray)
                        }
                        val mediaType = XposedHelpers.callStaticMethod(mediaTypeClass, "parse", "application/json")
                        it.args[0] = XposedHelpers.callStaticMethod(responseBodyClass, "create", mediaType, json.toString())
                    }
        }
    }
}
