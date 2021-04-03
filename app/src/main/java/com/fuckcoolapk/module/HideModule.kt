package com.fuckcoolapk.module

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.ArraySet
import androidx.annotation.RequiresApi
import com.fuckcoolapk.BuildConfig
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.ktx.*
import de.robv.android.xposed.XposedHelpers
import java.util.*

class HideModule {
    fun init() {
        "com.coolapk.market.util.LocalApkUtils"
                .hookAfterMethod("getAppList", PackageManager::class.java, Boolean::class.javaPrimitiveType) {
                    val appList = it.result as ArrayList<Any>
                    val newAppList = ArrayList<Any>()
                    for (i in appList.indices) {
                        val app = appList[i]
                        if ((app.callMethod("getPackageName") as String) != BuildConfig.APPLICATION_ID) newAppList.add(app)
                    }
                    it.result = newAppList
                }
        "com.coolapk.market.receiver.PackageReceiverImpl"
                .replaceMethod("onPackageAdded", Context::class.java, Intent::class.java, String::class.java) {
                    if ((it.args[2] as String) != BuildConfig.APPLICATION_ID){
                        LogUtil.d("onPackageAdded Add ${it.args[2] as String}")
                        "com.coolapk.market.util.PendingAppsUtils".callStaticMethod("doAddAction",it.args[0],it.args[2])
                    }else{
                        LogUtil.d("onPackageAdded Filter ${it.args[2] as String}")
                    }
                }
        /*"com.coolapk.market.receiver.PackageReceiverImpl"
                .hookBeforeMethod("onPackageAdded", Context::class.java, Intent::class.java, String::class.java) {
                    if ((it.args[2] as String) == BuildConfig.APPLICATION_ID) {
                        it.result = null
                        return@hookBeforeMethod
                    }
                }*/
    }
}