package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant

class RemoveHotWord {

    fun init(){
        if (OwnSP.ownSP.getBoolean("removeHotWord", false)) {
            "com.coolapk.market.AppSetting".setReturnConstant("getSearchHint", result = listOf(""))
        }
    }
}