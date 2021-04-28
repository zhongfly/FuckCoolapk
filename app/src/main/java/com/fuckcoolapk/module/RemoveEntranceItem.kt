package com.fuckcoolapk.module

import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.getObjectField
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import de.robv.android.xposed.XposedBridge
import java.util.*

class RemoveEntranceItem {

    companion object{
        val itemMap = mapOf(
            "酷图" to "removeEntranceItemCoolPic",
            "提问" to "removeEntranceItemQuestion",
            "二手" to "removeEntranceItemSecondHand",
            "扫一扫" to "removeEntranceItemScan",
            "投票" to "removeEntranceItemVote",
            "话题" to "removeEntranceItemTopic",
            "好物" to "removeEntranceItemGoods",
            "好物单" to "removeEntranceItemGoodsList",
            "应用集" to "removeEntranceItemAppSet"
        )
    }

    private fun shouldHide(name: String) = OwnSP.ownSP.getBoolean(itemMap[name], false)

    fun init(){
        "com.coolapk.market.view.feedv8.FeedEntranceV8Activity\$onCreate\$3".hookAfterMethod("invoke"){
            val itemList = it.result as ArrayList<Object>
            val newList = mutableListOf<Object>()
            for (item in itemList){
                if (shouldHide(item.getObjectField("title") as String)){
                    continue
                }
                newList.add(item)
            }
            it.result = newList
        }
    }
}