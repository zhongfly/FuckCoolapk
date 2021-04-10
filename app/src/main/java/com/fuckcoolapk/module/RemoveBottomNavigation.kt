package com.fuckcoolapk.module

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.getAppMode
import com.fuckcoolapk.utils.ktx.*
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import java.util.ArrayList

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
    private val tabIntList by lazy {
        if (getAppMode() == "community") {
            MutableList(4) { it }
        } else {
            MutableList(5) { it }
        }
    }

    fun init() {
        "com.coolapk.market.view.main.NavigationHelper"
                .hookAfterMethod("getAHBottomNavigationItems", Context::class.java) {
                    val list = it.result as ArrayList<Any>
                    it.result = if (getAppMode() == "community") {
                        ArrayList<Any?>().apply {
                            if (removeList[0]) for (i in 1..tabIntList.lastIndex) tabIntList[i]-- else add(list[0])
                            if (removeList[1]) for (i in 2..tabIntList.lastIndex) tabIntList[i]-- else add(list[1])
                            if (removeList[2]) for (i in 3..tabIntList.lastIndex) tabIntList[i]-- else add(list[2])
                            if (removeList[3]) for (i in 4..tabIntList.lastIndex) tabIntList[i]-- else add(list[3])
                        }
                    } else {
                        ArrayList<Any?>().apply {
                            if (removeList[0]) for (i in 1..tabIntList.lastIndex) tabIntList[i]-- else add(list[0])
                            if (removeList[1]) for (i in 2..tabIntList.lastIndex) tabIntList[i]-- else add(list[1])
                            if (removeList[2]) for (i in 3..tabIntList.lastIndex) tabIntList[i]-- else add(list[2])
                            if (removeList[3]) for (i in 4..tabIntList.lastIndex) tabIntList[i]-- else add(list[3])
                            if (removeList[4]) for (i in 5..tabIntList.lastIndex) tabIntList[i]-- else add(list[4])
                        }
                    }
                }
        "com.coolapk.market.view.main.MainFragment"
                .hookBeforeMethod("onTabSelected", Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType) {
                    val tabInt = (it.args[0] as Int)
                    for (i in tabIntList.indices) if (tabInt == tabIntList[i]) it.args[0] = i
                }
    }
}
