package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class EnableAdminMode {
    fun init() {
        if (OwnSP.ownSP.getBoolean("adminMode", false)) {
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("getCanCreateNewVote", true)
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("getCanUseAdvancedVoteOptions", true)
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("isLoginAdmin", true)
            "com.coolapk.market.local.LoginSession".setReturnConstant("isAdmin", true)
        }
    }
}