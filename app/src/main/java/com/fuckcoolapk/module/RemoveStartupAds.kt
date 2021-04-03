package com.fuckcoolapk.module

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.*
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class RemoveStartupAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeStartupAds", false)) {
            "com.coolapk.market.view.splash.FullScreenAdUtils".setReturnConstant("shouldShowAd", Context::class.java,result = false)
            "com.coolapk.market.manager.MainInitHelper".setReturnConstant("isDataLoaded",result = true)
            "com.coolapk.market.view.splash.SplashActivity"
                    .hookBeforeMethod("getLaunchMode"){
                        //(it.thisObject as Activity).callMethod("finishSplashInternal",false)
                        (it.thisObject as Activity).setResult(-1)
                        (it.thisObject as Activity).finish()
                        (it.thisObject as Activity).overridePendingTransition(0,0)
                        it.result = "HOT_BOOT"
                    }
            "com.coolapk.market.view.splash.BaseFullScreenAdActivity"
                    .replaceMethod("doStart"){}
        }
    }}