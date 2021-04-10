package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.CoolapkSP
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.getAppMode
import com.fuckcoolapk.utils.ktx.setReturnConstant

class ModifyAppMode {
    fun init() {
        if (OwnSP.ownSP.getBoolean("modifyAppMode", false)) {
            when (CoolapkSP.coolapkSP.getString("app_mode","universal")) {
                "community" -> "com.coolapk.market.AppMetadata".setReturnConstant("getAppMode", result = "universal")
                "universal" -> "com.coolapk.market.AppMetadata".setReturnConstant("getAppMode", result = "community")
            }
        }
    }
}