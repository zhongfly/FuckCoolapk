package com.fuckcoolapk.module

import android.content.Intent
import android.view.View
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedHelpers
import java.util.ArrayList

class ModifyGoodsButton {

    fun init(){
        if (OwnSP.ownSP.getBoolean("modifyGoodsButton", false)){
            "com.coolapk.market.view.main.MainFragment".hookAfterMethod("setDiscoveryLongClick") {
                val mainFragment = it.thisObject
                val mainFragmentBinding =
                    XposedHelpers.getObjectField(mainFragment, "binding") as Object
                val bottomNavigation =
                    XposedHelpers.getObjectField(mainFragmentBinding, "bottomNavigation") as Object
                val views =
                    XposedHelpers.getObjectField(bottomNavigation, "views") as ArrayList<View>
                var int = 1
                for (view in views) {
                    if (int == 3) {
                        view.setOnClickListener {
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
                    int++
                }
            }
        }
    }
}