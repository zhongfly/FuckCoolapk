package com.fuckcoolapk.module

import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.setReturnConstant
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedHelpers

class EnableAdminMode {
    fun init() {
        if (OwnSP.ownSP.getBoolean("adminMode", false)) {
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("getCanCreateNewVote", result = true)
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("getCanUseAdvancedVoteOptions", result = true)
            "com.coolapk.market.manager.UserPermissionChecker".setReturnConstant("isLoginAdmin", result = true)
            "com.coolapk.market.local.LoginSession".setReturnConstant("isAdmin", result = true)
        }
    }
}