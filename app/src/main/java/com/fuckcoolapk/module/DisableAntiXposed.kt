package com.fuckcoolapk.module

import com.fuckcoolapk.utils.ktx.replaceMethod
import de.robv.android.xposed.XposedHelpers

class DisableAntiXposed {
    fun init() = "com.coolapk.market.util.XposedUtils".replaceMethod("disableXposed") { null }
}