package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.replaceAfterAllMethods

class DisableUmeng {
    fun init() {
        if (OwnSP.ownSP.getBoolean("disableUmeng", false)) {
            "com.umeng.commonsdk.UMConfigure"
                    .replaceAfterAllMethods("init") {
                        null
                    }
            "com.umeng.commonsdk.UMConfigure"
                    .replaceAfterAllMethods("preInit") {
                        null
                    }
            /*"com.umeng.commonsdk.utils.UMUtils"
                    .replaceMethod("getSystemProperty", String::class.java, String::class.java){}*/
        }
    }
}
