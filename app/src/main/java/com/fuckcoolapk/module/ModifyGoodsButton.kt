package com.fuckcoolapk.module

import android.content.Intent
import android.view.View
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedHelpers
import java.util.*

class ModifyGoodsButton {

    fun init() {
        if (OwnSP.ownSP.getBoolean("modifyGoodsButton", false)) {
            "com.coolapk.market.view.main.MainFragment".hookAfterMethod("setDiscoveryLongClick") {
                val mainFragment = it.thisObject
                val mainFragmentBinding =
                        XposedHelpers.getObjectField(mainFragment, "binding") as Any
                val bottomNavigation =
                        XposedHelpers.getObjectField(mainFragmentBinding, "bottomNavigation") as Any
                val views =
                        XposedHelpers.getObjectField(bottomNavigation, "views") as ArrayList<*>
                (views[2] as View).setOnClickListener {
                    val intent = Intent(
                            CoolContext.context,
                            XposedHelpers.findClass(
                                    "com.coolapk.market.view.feedv8.FeedEntranceV8Activity",
                                    CoolContext.classLoader
                            )
                    )
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    CoolContext.context.startActivity(intent)
                }
            }
        }
    }
}
