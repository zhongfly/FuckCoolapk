package com.fuckcoolapk.module

import android.content.Context
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeAllMethods
import com.fuckcoolapk.utils.ktx.replaceAfterAllMethods
import com.fuckcoolapk.utils.ktx.replaceMethod
import de.robv.android.xposed.XposedHelpers

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
        }
    }
}