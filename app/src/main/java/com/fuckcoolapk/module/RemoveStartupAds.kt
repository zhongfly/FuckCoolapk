package com.fuckcoolapk.module

import android.content.Context
import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class RemoveStartupAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeStartupAds", false)) {
            "com.coolapk.market.view.splash.FullScreenAdUtils".setReturnConstant("shouldShowAd", Context::class.java,result = false)
        }
    }}