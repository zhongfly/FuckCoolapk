package com.fuckcoolapk.module

import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.findClass
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import java.util.*

class ShowCoolapkDebugLog {
    fun init() {
        if (OwnSP.ownSP.getBoolean("showCoolapkDebugLog", false)) {
            val logUtils = "com.coolapk.market.util.LogUtils".findClass()
            logUtils.hookBeforeMethod("v", String::class.java, Array<Object>::class.java) {
                LogUtil.v("From Coolapk: ${String.format(it.args[0] as String, *(it.args[1] as Array<Any>))}")
            }
            logUtils.hookBeforeMethod("d", String::class.java, Array<Object>::class.java) {
                LogUtil.d("From Coolapk: ${String.format(it.args[0] as String, *(it.args[1] as Array<Any>))}")
            }
            logUtils.hookBeforeMethod("i", String::class.java, Array<Object>::class.java) {
                LogUtil.i("From Coolapk: ${String.format(it.args[0] as String, *(it.args[1] as Array<Any>))}")
            }
            logUtils.hookBeforeMethod("w", String::class.java, Array<Object>::class.java) {
                LogUtil.w("From Coolapk: ${String.format(it.args[0] as String, *(it.args[1] as Array<Any>))}")
            }
            logUtils.hookBeforeMethod("e", String::class.java, Array<Object>::class.java) {
                LogUtil.e("From Coolapk: ${String.format(it.args[0] as String, *(it.args[1] as Array<Any>))}")
            }
        }
    }
}