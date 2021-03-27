package com.fuckcoolapk.module

import android.view.View
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.getObjectField
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedHelpers

class RemoveAuditWatermark {
    fun init() {
        if (OwnSP.ownSP.getBoolean("removeAuditWatermark", false)) {
            "com.coolapk.market.binding.ViewBindingAdapters"
                    .hookAfterMethod("updateFeed", "com.coolapk.market.widget.ForegroundTextView", "com.coolapk.market.model.Feed") {
                        if (it.args[0] != null) {
                            val foregroundTextView = (it.args[0] as View)
                            foregroundTextView.visibility = View.GONE
                        }
                    }
            "com.coolapk.market.view.feed.FeedDetailActivityV8"
                    .hookAfterMethod("installForegroundTextView", "com.coolapk.market.model.Feed") {
                        val foregroundTextView = it.thisObject.getObjectField("foregroundTextView") as? View
                                ?: return@hookAfterMethod
                        foregroundTextView.visibility = View.GONE
                    }
        }
    }
}