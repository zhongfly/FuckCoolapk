package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.replaceAfterAllMethods

class DisableBugly {
    fun init() {
        if (OwnSP.ownSP.getBoolean("disableBugly", false)) {
            "com.tencent.bugly.crashreport.CrashReport"
                    .replaceAfterAllMethods("initCrashReport") {
                        null
                    }
        }
    }
}