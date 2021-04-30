package com.fuckcoolapk.module

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.getObjectField
import com.fuckcoolapk.utils.ktx.getObjectFieldAs
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedHelpers

class ModifyGoodsButton {

    fun init() {
        if (OwnSP.ownSP.getBoolean("modifyGoodsButton", false)) {
            "com.coolapk.market.view.main.MainFragment".hookAfterMethod("setDiscoveryLongClick") {
                val mainFragment = it.thisObject
                val mainFragmentBinding = mainFragment.getObjectField("binding")
                val bottomNavigation = mainFragmentBinding?.getObjectField("bottomNavigation")
                val views = bottomNavigation?.getObjectFieldAs<ArrayList<View>>("views")
                views?.forEach { view ->
                    val textView = (view as ViewGroup).getChildAt(1) as TextView
                    if (textView.text == "发现") {
                        textView.text = "发布"
                        view.setOnClickListener {
                            CoolContext.context.startActivity(Intent(
                                    CoolContext.context,
                                    XposedHelpers.findClass(
                                            "com.coolapk.market.view.feedv8.FeedEntranceV8Activity",
                                            CoolContext.classLoader
                                    )
                            ).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
                        }
                        return@forEach
                    }
                }
            }
        }
    }
}
