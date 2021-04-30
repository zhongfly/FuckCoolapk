package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod

class CheckFeedBlock {
    fun init(){
        if (OwnSP.ownSP.getBoolean("checkFeedBlock", false)){
            "com.coolapk.market.model.Feed".hookBeforeMethod("getUserName"){
                if (it.thisObject.callMethod("getBlockStatus") as Int != 0){
                    it.result = "动态已折叠，酷安nmsl"
                }
            }
        }
    }
}
