package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod

class RemoveSearchActivityItem {
    fun init(){
        "com.coolapk.market.view.cardlist.EntityListFragment".hookAfterMethod("modifyDataBeforeHandle", List::class.java, Boolean::class.java){
            val newList = mutableListOf<Any>()
            loop@ for (item in it.result as List<*>) {
                if (item == null) continue
                when (item.callMethod("getEntityTemplate") as? String){
                    "hotSearch" -> {
                        if (OwnSP.ownSP.getBoolean("hotSearch", false)){
                            continue@loop
                        }
                        newList.add(item)
                    }
                    "searchHotListCard" -> {
                        if (OwnSP.ownSP.getBoolean("searchHotListCard", false)){
                            continue@loop
                        }
                        newList.add(item)
                    }
                    else -> newList.add(item)
                }
            }
            it.result = newList
        }
    }
}