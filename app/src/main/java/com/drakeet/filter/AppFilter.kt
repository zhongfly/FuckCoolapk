package com.drakeet.filter

import android.os.Build
import android.util.ArraySet
import androidx.annotation.RequiresApi
import com.fuckcoolapk.BuildConfig
import com.fuckcoolapk.utils.LogUtil
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam

/**
 * @author Drakeet Xu
 */
class AppFilter {

    private val callerWhiteList = arrayOf(
            "com.android.systemui",
            "com.miui.home",
            "android.uid.system",
    )

    // Apps in this list will be filtered out
    private val filteredList = arrayOf(
            BuildConfig.APPLICATION_ID,
    )

    fun init(classLoader: ClassLoader) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            LogUtil.d("\n\n# AppFilter\n---\n")

            findAndHookMethod(
                    "com.android.server.pm.AppsFilter", classLoader,
                    "shouldFilterApplication",
                    Int::class.javaPrimitiveType,
                    "com.android.server.pm.SettingBase",
                    "com.android.server.pm.PackageSetting",
                    Int::class.javaPrimitiveType,
                    object : XC_MethodHook() {

                        override fun afterHookedMethod(param: MethodHookParam) {
                            if (param.result == true) return

                            // toString will be faster
                            // val packageName = on(param.args[2]).field("name").get<String>()
                            val targetPkgSetting = param.args[2]
                            val callerSettingBase = param.args[1]

                            val targetPackageName = targetPkgSetting.packageName
                            val callerPackageName = callerSettingBase?.packageName ?: "N/A"

                            if (targetPackageName in filteredList && callerPackageName !in callerWhiteList) {
                                if (!notedSet.contains(callerPackageName)) {
                                    notedSet.add(callerPackageName)
                                    LogUtil.d("Filtered: $callerPackageName → $targetPackageName")
                                }
                                param.result = true
                            }
                        }
                    })
        }
    }

    private val Any.packageName get() = toString().substringAfterLast(" ").substringBefore("/")

    companion object {
        @RequiresApi(Build.VERSION_CODES.M)
        private val notedSet = ArraySet<String>()
    }
}