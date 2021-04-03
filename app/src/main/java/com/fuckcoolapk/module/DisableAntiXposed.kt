package com.fuckcoolapk.module

import com.fuckcoolapk.utils.ktx.replaceMethod
import com.fuckcoolapk.utils.ktx.setReturnConstant
import de.robv.android.xposed.XposedHelpers

class DisableAntiXposed {
    fun init() {
        "com.coolapk.market.util.XposedUtils".setReturnConstant("hasXposed",result = false)
        "com.coolapk.market.util.XposedUtils".replaceMethod("disableXposed") { null }
    }
}