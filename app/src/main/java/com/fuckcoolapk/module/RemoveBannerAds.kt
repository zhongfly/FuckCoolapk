package com.fuckcoolapk.module

import android.util.Log
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.*
import de.robv.android.xposed.XposedHelpers

class RemoveBannerAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeBannerAds", false)) {
            "com.coolapk.market.model.\$\$AutoValue_Feed".setReturnConstant("getDetailSponsorCard", result = null)
        }
    }
}
