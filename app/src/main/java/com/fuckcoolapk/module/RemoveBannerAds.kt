package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedHelpers

class RemoveBannerAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeBannerAds", false)) {
            XposedHelpers.findClass("com.coolapk.market.view.feed.reply.FeedDetailFragmentV8", CoolapkContext.classLoader)
                    .hookAfterMethod("updateHeaderData") {
                        val dataList = XposedHelpers.callMethod(it.thisObject, "getDataList") as List<Any>
                        XposedHelpers.callMethod(dataList, "remove", dataList.size - 1)
                    }
            
            XposedHelpers.findClass("com.coolapk.market.view.feed.reply.FeedArticleDetailFragment", CoolapkContext.classLoader)
                    .hookAfterMethod("updateHeaderData") {
                        val dataList = XposedHelpers.callMethod(it.thisObject, "getDataList") as List<Any>
                        XposedHelpers.callMethod(dataList, "remove", dataList.size - 1)
                    }
        }
    }
}
