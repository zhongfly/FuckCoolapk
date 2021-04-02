package com.fuckcoolapk.module

import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.ktx.callMethod
import com.fuckcoolapk.utils.ktx.callStaticMethod
import com.fuckcoolapk.utils.ktx.hookAfterMethod
import com.fuckcoolapk.utils.ktx.hookBeforeMethod

class InsertHeadlineCard {
    fun init() {
        "com.coolapk.market.viewholder.ImageListCardViewHolder"
                .hookBeforeMethod("bindTo", Object::class.java) {
                    if (CoolContext.activity.javaClass.name=="com.coolapk.market.view.splash.SplashActivity"){
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
                                ?.callMethod("setEntities", (entityCard.callMethod("getEntities") as java.util.ArrayList<Any?>)
                                        .add(0,"com.coolapk.market.model.EntityCard".callStaticMethod("builder")
                                                ?.callMethod("setEntityType","image_1")
                                                ?.callMethod("setTitle","233")
                                                ?.callMethod("setPic","https://cdn.jsdelivr.net/gh/lz233/src.lz233.github.io/image/background.jpg")
                                                ?.callMethod("build")))
                                ?.callMethod("build")
                    }
                }
        /*"com.coolapk.market.view.cardlist.MainV8ListFragment"
                .hookBeforeMethod("onRequestResponse", Boolean::class.javaPrimitiveType, "java.util.List") {
                    it.args[1] = (it.args[1] as java.util.ArrayList<Any?>).apply {
                        add(0, "com.coolapk.market.model.HolderItem"
                                .callStaticMethod("newBuilder")
                                ?.callMethod("entityType", "card")
                                ?.callMethod("entityTemplate","imageCard")
                                ?.callMethod("title", "233")
                                ?.callMethod("url","")
                                ?.callMethod("pic","https://cdn.jsdelivr.net/gh/lz233/src.lz233.github.io/image/background.jpg")
                                ?.callMethod("build"))
                    }
                }*/
    }
}