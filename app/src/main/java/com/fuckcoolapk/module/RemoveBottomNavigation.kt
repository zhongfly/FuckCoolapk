package com.fuckcoolapk.module

import android.content.Context
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.getAppMode
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import java.util.*

//com.coolapk.market.view.main.NavigationHelper

class RemoveBottomNavigation {
    private val removeList by lazy {
        if (getAppMode() == "community") {
            listOf(OwnSP.ownSP.getBoolean("removeBottomNavigationHome", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationMobileBar", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationDiscovery", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationCenter", false))
        } else {
            listOf(OwnSP.ownSP.getBoolean("removeBottomNavigationHome", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationMobileBar", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationDiscovery", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationAppAndGame", false),
                    OwnSP.ownSP.getBoolean("removeBottomNavigationCenter", false))
        }
    }

    fun init() {
        "com.coolapk.market.view.main.NavigationHelper".hookAfterMethod("getAHBottomNavigationItems", Context::class.java) {
            val list = it.result as ArrayList<*>
            it.result = ArrayList<Any?>().apply {
                list.forEachIndexed { index, item ->
                    if (!removeList[index]) add(item)
                }
            }
        }
        "com.coolapk.market.view.main.MainFragment"
                .hookBeforeMethod("onTabSelected", Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType) {
                    val tabInt = (it.args[0] as Int)
                    var newTabInt = -1
                    for (i in -1 until tabInt) {
                        newTabInt++
                        while (removeList[newTabInt]) {
                            newTabInt++
                        }
                    }
                    it.args[0] = newTabInt
                }
    }
}
