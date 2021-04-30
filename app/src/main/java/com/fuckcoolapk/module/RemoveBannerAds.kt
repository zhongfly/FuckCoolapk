package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant

class RemoveBannerAds {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeBannerAds", false)) {
            "com.coolapk.market.model.\$\$AutoValue_Feed".setReturnConstant("getDetailSponsorCard", result = null)
        }
    }
}
