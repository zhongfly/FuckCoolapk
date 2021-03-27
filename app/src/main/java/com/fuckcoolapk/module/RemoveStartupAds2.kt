package com.fuckcoolapk.module

import android.content.Context
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class RemoveStartupAds2 {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeStartupAds2", false)) {
            val cDataManager = XposedHelpers.findClass("com.coolapk.market.manager.DataManager", CoolContext.classLoader)
            val dataManager = XposedHelpers.callStaticMethod(cDataManager, "getInstance")
            val prefEditor = XposedHelpers.callMethod(dataManager, "getPreferencesEditor")
            XposedHelpers.callMethod(prefEditor, "putLong", "SPLASH_AD_LAST_SHOW", System.currentTimeMillis())
            XposedHelpers.callMethod(prefEditor, "apply")
        }
    }}
