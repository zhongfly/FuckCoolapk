package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class RemoveFeedAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeFeedAds", false)) {
            XposedHelpers.findAndHookMethod("com.coolapk.market.view.ad.toutiao.TTFeedSelfDrawAd", CoolapkContext.classLoader, "load", "com.coolapk.market.view.ad.OnAdLoadListener", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    XposedHelpers.setIntField(param.thisObject, "state", 1)
                }
            })

            XposedHelpers.findAndHookMethod("com.coolapk.market.view.ad.tencent.GDTFeedSelfDrawAD", CoolapkContext.classLoader, "load", "com.coolapk.market.view.ad.OnAdLoadListener", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    XposedHelpers.setIntField(param.thisObject, "state", 1)
                }
            })

            XposedHelpers.findAndHookMethod("com.coolapk.market.view.ad.toutiao.TTFeedAd", CoolapkContext.classLoader, "load", "com.coolapk.market.view.ad.OnAdLoadListener", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    XposedHelpers.setIntField(param.thisObject, "state", 1)
                }
            })

            XposedHelpers.findAndHookMethod("com.coolapk.market.view.ad.tencent.GDTFeedAd2", CoolapkContext.classLoader, "load", "com.coolapk.market.view.ad.OnAdLoadListener", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    XposedHelpers.setIntField(param.thisObject, "state", 1)
                }
            })

            XposedHelpers.findAndHookMethod("com.coolapk.market.view.ad.tencent.GDTFeedAd", CoolapkContext.classLoader, "load", "com.coolapk.market.view.ad.OnAdLoadListener", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    XposedHelpers.setIntField(param.thisObject, "state", 1)
                }
            })
        }
    }
}