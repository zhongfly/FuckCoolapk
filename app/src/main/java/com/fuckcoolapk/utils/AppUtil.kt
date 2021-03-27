package com.fuckcoolapk.utils

import android.content.Context
import android.content.res.Configuration
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.callStaticMethod
import de.robv.android.xposed.XposedHelpers
import java.util.*
import kotlin.math.absoluteValue


fun dp2px(context: Context, dpValue: Float): Int = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
fun sp2px(context: Context, spValue: Float): Int = (spValue * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()
fun getAS(uuid: String = UUID.randomUUID().toString()) = "com.coolapk.market.util.AuthUtils".callStaticMethod("getAS", CoolContext.context, UUID.randomUUID().toString()) as String
fun isNightMode(context: Context): Boolean = (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
inline fun getColorFix(block: () -> String): String {
    var string = block()
    while (string.length < 6) {
        string = "0$string"
    }
    return string
}

inline fun getColorFixWithHashtag(block: () -> String): String = "#${getColorFix(block)}"
fun getColorPrimary(): String = (XposedHelpers.findClass("com.coolapk.market.util.ColorUtils", CoolContext.classLoader).callStaticMethod("adjustAlpha", CoolContext.appTheme.callMethod("getColorPrimary"), 0f) as Int).absoluteValue.toString(16)
fun getColorAccent(): String = (XposedHelpers.findClass("com.coolapk.market.util.ColorUtils", CoolContext.classLoader).callStaticMethod("adjustAlpha", CoolContext.appTheme.callMethod("getColorAccent"), 0f) as Int).absoluteValue.toString(16)
fun getTextColor(): String = if (CoolContext.appTheme.callMethod("isDayTheme") as Boolean) {
    if (CoolContext.appTheme.callMethod("isLightColorTheme") as Boolean) {
        "000000"
    } else {
        "ffffff"
    }
} else {
    "ffffff"
}

fun reverseColor(mString: String): String = "${(255 - mString.substring(0, 2).toInt(16)).toString(16)}${(255 - mString.substring(2, 4).toInt(16)).toString(16)}${(255 - mString.substring(4, 6).toInt(16)).toString(16)}"