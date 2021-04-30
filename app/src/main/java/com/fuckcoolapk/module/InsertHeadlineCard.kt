package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.callStaticMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class InsertHeadlineCard {
    fun init() {
        "com.coolapk.market.viewholder.ImageListCardViewHolder"
                .hookBeforeMethod("bindTo", Object::class.java) {
                    if ((CoolContext.activity.javaClass.name == "com.coolapk.market.view.splash.SplashActivity") or (CoolContext.activity.javaClass.name == "com.coolapk.market.view.main.MainActivity")) {
                        val entityCard = it.args[0]
                        it.args[0] = "com.coolapk.market.model.EntityCard"
                                .callStaticMethod("builder")
                                ?.callMethod("setEntityId", entityCard.callMethod("getEntityId"))
                                ?.callMethod("setEntityFixed", entityCard.callMethod("getEntityFixed"))
                                ?.callMethod("setEntityTemplate", entityCard.callMethod("getEntityTemplate"))
                                ?.callMethod("setEntityType", entityCard.callMethod("getEntityType"))
                                ?.callMethod("setExtraData", entityCard.callMethod("getExtraData"))
                                ?.callMethod("setId", entityCard.callMethod("getId"))
                                ?.callMethod("setLastUpdate", entityCard.callMethod("getLastUpdate"))
                                ?.callMethod("setEntities", (entityCard.callMethod("getEntities") as ArrayList<Any?>)
                                        .apply {
                                            val jsonArray = JSONArray(OwnSP.ownSP.getString("configBannerCard","[]"))
                                            for (i in 0 until jsonArray.length()){
                                                val jsonObject = jsonArray.getJSONObject(i)
                                                add(if (jsonObject.getBoolean("countDown")) size-jsonObject.getInt("position") else jsonObject.getInt("position"), "com.coolapk.market.model.EntityCard"
                                                        .callStaticMethod("builder")
                                                        ?.callMethod("setEntityType", jsonObject.getString("entityType"))
                                                        ?.callMethod("setTitle", jsonObject.getString("title"))
                                                        ?.callMethod("setUrl", jsonObject.getString("url"))
                                                        ?.callMethod("setPic", jsonObject.getString("pic"))
                                                        ?.callMethod("build"))
                                            }
                                        }
                                     )
                                ?.callMethod("build")
                    }
                }
    }
}