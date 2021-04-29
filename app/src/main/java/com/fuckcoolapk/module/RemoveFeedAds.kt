package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XposedHelpers

class RemoveFeedAds {
    private val onAdLoadListener = "com.coolapk.market.view.ad.OnAdLoadListener"

    private val removeList = listOf(
            "猜你喜欢", // Title过滤
            "酷友在搜的优惠券", // Title过滤
            "什么值得买", // Title过滤
            "优选配件", // Title过滤
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
            "com.coolapk.market.view.cardlist.EntityRemoveHelper".hookAfterMethod("modifyData", List::class.java, Boolean::class.java) {
                val newList = mutableListOf<Any>()
                loop@ for (item in it.result as List<*>) {
                    if (item == null) continue
                    val entityType = item.callMethod("getEntityType") as? String
                    val title = item.callMethod("getTitle") as? String
                    val extraData = item.callMethod("getExtraData")?.callMethod("toString") as? String
                            ?: item.callMethod("getExtraData") as? String
                    val url = item.callMethod("getUrl") as? String
                    when {
                        entityType?.contains("_goods", ignoreCase = true) ?: false -> continue@loop
                        removeList.any { items ->
                            title?.contains(items) ?: false
                        } -> continue@loop
                        extraData?.contains("_GOODS", ignoreCase = true) ?: false -> continue@loop
                        url?.contains("pearGoods", ignoreCase = true) ?: false -> continue@loop
                        else -> newList.add(item)
                    }
                }
                it.result = newList
            }
        }
    }
}
