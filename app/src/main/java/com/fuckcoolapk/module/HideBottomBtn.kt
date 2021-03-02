package com.fuckcoolapk.module

import android.widget.LinearLayout
import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class HideBottomBtn {
    fun init() {
        if (OwnSP.ownSP.getBoolean("hideBottomBtn", false)) {
            XposedHelpers.findClass("com.aurelhubert.ahbottomnavigation.AHBottomNavigation", CoolapkContext.classLoader)
                    .hookBeforeMethod("addItems", List::class.java) {
                        val bottomBtnList = it.args[0] as MutableList<Any>
                        for (i in bottomBtnList.indices) {
                            val btn = bottomBtnList[i]
                            val title = XposedHelpers.getObjectField(btn, "title") as String
                            if ("首页" != title && "我" != title) bottomBtnList.removeAt(i)
                        }
                        XposedHelpers.setObjectField(it.thisObject, "items", bottomBtnList)
                    }

            XposedHelpers.findClass("com.aurelhubert.ahbottomnavigation.AHBottomNavigation", CoolapkContext.classLoader)
                    .hookBeforeMethod("updateItems", Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType) {
                        var i = it.args[0] as Int
                        if (i == 1) i = 4
                        it.args[0] = i
                    }

        }
    }
}
