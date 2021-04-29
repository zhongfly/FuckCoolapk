package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant

class DisableUpdateRemind {
    fun init(){
        if (OwnSP.ownSP.getBoolean("disableUpdateRemind", false)){
            "com.coolapk.market.CoolMarketApplication".setReturnConstant("checkCoolapkUpgradeInfo", result = null)
        }
    }
}
