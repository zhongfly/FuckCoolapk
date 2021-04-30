package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.replaceMethod

class DisableUpdateDialog {
    fun init() {
        if (OwnSP.ownSP.getBoolean("disableUpdateDialog", false) or CoolContext.isXpatch) {
            "com.coolapk.market.CoolMarketApplication".replaceMethod("checkCoolapkUpgradeInfo") {}
        }
    }
}
