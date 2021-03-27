package com.fuckcoolapk.module

import android.text.TextUtils
import android.view.View
import com.fuckcoolapk.PACKAGE_NAME
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook

class AllowUninstallCoolapk {
    fun init() {
        if (OwnSP.ownSP.getBoolean("allowUninstallCoolapk", false)) {
            var textUtilsHook: XC_MethodHook.Unhook? = null
            "com.coolapk.market.view.appmanager.MobileAppFragment\$DataAdapter\$onCreateViewHolder\$1"
                    .hookBeforeMethod("onItemClick", "androidx.recyclerview.widget.RecyclerView\$ViewHolder", View::class.java) {
                        textUtilsHook = "android.text.TextUtils".hookBeforeMethod("equals", CharSequence::class.java, CharSequence::class.java) {
                            if ((it.args[0] as String) == PACKAGE_NAME) {
                                it.result = false
                                LogUtil.toast("干得漂亮（")
                            }
                        }
                    }
            "com.coolapk.market.view.appmanager.MobileAppFragment\$DataAdapter\$onCreateViewHolder\$1"
                    .hookAfterMethod("onItemClick", "androidx.recyclerview.widget.RecyclerView\$ViewHolder", View::class.java) {
                        textUtilsHook?.unhook()
                    }
        }
    }
}