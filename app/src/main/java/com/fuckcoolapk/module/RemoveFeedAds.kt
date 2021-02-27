package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

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
        }
    }
}