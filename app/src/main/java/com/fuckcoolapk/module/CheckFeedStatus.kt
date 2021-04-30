package com.fuckcoolapk.module

import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod

class CheckFeedStatus {
    fun init() {
        if (OwnSP.ownSP.getBoolean("checkFeedStatus", false)) {
            "com.coolapk.market.model.Feed"
                    .hookBeforeMethod("getUserName") {
                        if (it.thisObject.callMethod("getBlockStatus") as Int != 0) {
                            it.result = "动态已折叠，酷安nmsl"
                        }
                    }
            "com.coolapk.market.model.Feed"
                    .hookAfterMethod("getDeviceTitle"){
                        LogUtil.d(it.result as String)
                    }
        }
    }
}
