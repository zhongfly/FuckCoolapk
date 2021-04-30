package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant

class EnableChannelEdit {
    fun init() {
        if (OwnSP.ownSP.getBoolean("enableChannelEdit", false)) {
            "com.coolapk.market.view.main.channel.Channel".setReturnConstant("isFixed", result = false)
        }
    }
}