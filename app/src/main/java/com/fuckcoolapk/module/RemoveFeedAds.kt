package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XposedHelpers
import org.json.JSONArray
import org.json.JSONObject

class RemoveFeedAds {
    private val onAdLoadListener = "com.coolapk.market.view.ad.OnAdLoadListener"

    private val removeList = listOf(
        "猜你喜欢",
        "酷友在搜的优惠券",
        "什么值得买",
    )

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
                        val mediaTypeClass = XposedHelpers.findClass("okhttp3.MediaType", CoolContext.classLoader)
                        val responseBodyClass = XposedHelpers.findClass("okhttp3.ResponseBody", CoolContext.classLoader)
                        val result = XposedHelpers.callMethod(responseBody, "string") as String
                        val json = JSONObject(result)
//                        LogUtil.i("NA:$json")
                        val dataArray = json.optJSONArray("data")
//                        LogUtil.i("NA:$dataArray")
//                        val adList: MutableList<Int> = arrayListOf()
                        val newDataArray = JSONArray()

                        dataArray?.let {
                            //屏蔽自营信息流广告
                            var index = 0
                            for (i in 0 until dataArray.length()) {
                                val dataJson = dataArray.optJSONObject(i)
                                //去除信息流广告
                                if (dataJson.optString("entityId") == "8639" || dataJson.optString("title") == "猜你喜欢") {
                                    continue
                                }
                                val extraData = dataJson.optJSONObject("extraDataArr")
                                if (extraData != null) {
                                    val cardPageName = extraData.optString("cardPageName")
                                    if (cardPageName.endsWith("_AD", ignoreCase = true)) {
                                        continue
                                    }
                                }
                                if (dataJson.toString().contains("_goods",ignoreCase = true)) {
                                    continue
                                }
                                newDataArray.put(index, dataJson)
                                index++
                            }
//                            LogUtil.i("NA_new:$newDataArray")
                            json.put("data", newDataArray)
                        }
                        val mediaType = XposedHelpers.callStaticMethod(mediaTypeClass, "parse", "application/json")
                        it.args[0] = XposedHelpers.callStaticMethod(responseBodyClass, "create", mediaType, json.toString())
                    }
            "com.coolapk.market.view.main.DataListFragment".hookAfterMethod("modifyDataBeforeHandle", List::class.java, Boolean::class.java){
                val newList = mutableListOf<Any>()
                for (item in it.result as List<*>){
                    if (item!!.callMethod("getEntityType") as String == "pear_goods"){
                        continue
                    }
                    for (i in removeList){
                        if (i in item.callMethod("getTitle") as String){
                            continue
                        }
                    }
                    if ((item.callMethod("getTitle") as String).isEmpty()){
                        continue
                    }
                    newList.add(item)
                }
                it.result = newList
            }
        }
    }
}
