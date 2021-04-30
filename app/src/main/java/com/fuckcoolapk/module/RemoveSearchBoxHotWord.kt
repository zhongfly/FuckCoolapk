package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant

class RemoveSearchBoxHotWord {

    fun init(){
        if (OwnSP.ownSP.getBoolean("removeSearchBoxHotWord", false)) {
            "com.coolapk.market.AppSetting".setReturnConstant("getSearchHint", result = listOf(""))
        }
    }
}