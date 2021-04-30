package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod

class CheckFeedStatus {
    fun init() {
        if (OwnSP.ownSP.getBoolean("checkFeedStatus", false)) {
            "com.coolapk.market.model.Feed"
                    .hookAfterMethod("getUserName") {
                        if (it.thisObject.callMethod("getBlockStatus") as Int != 0)
                            it.result = "${it.result as String} [已被折叠]"
                    }
        }
    }
}
