package com.fuckcoolapk.utils

import android.app.Activity
import android.content.Context
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.callStaticMethod
import de.robv.android.xposed.XposedHelpers
import kotlin.math.absoluteValue

object CoolContext {
    var isXpatch = false
    lateinit var context: Context
    lateinit var classLoader: ClassLoader
    lateinit var activity: Activity
    val loginSession by lazy { "com.coolapk.market.manager.DataManager".callStaticMethod("getInstance")?.callMethod("getLoginSession")!! }
    val appTheme by lazy { "com.coolapk.market.AppHolder".callStaticMethod("getAppTheme")!! }
}