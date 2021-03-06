package com.fuckcoolapk.module

import android.text.Html
import android.widget.TextView
import com.fuckcoolapk.utils.CoolapkContext
import com.fuckcoolapk.utils.LogUtil
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.ktx.*
import de.robv.android.xposed.XposedHelpers
import org.commonmark.Extension
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer
import java.util.*

class EnableMarkdown {
    fun init() {
        if (OwnSP.ownSP.getBoolean("enableMarkdown", false)) {
            val extensions = listOf(StrikethroughExtension.create())
            val parser = Parser.builder().extensions(extensions).build()
            val renderer = HtmlRenderer.builder().extensions(extensions).build()
            "com.coolapk.market.binding.TextViewBindingAdapters"
                    .hookAfterMethod("setTextViewLinkable", TextView::class.java, String::class.java, "java.lang.Integer", String::class.java, Boolean::class.javaObjectType, Html.ImageGetter::class.java, String::class.java) {}
            "com.coolapk.market.util.LinkTextUtils"
                    .hookBeforeMethod("convert", String::class.java, Int::class.javaPrimitiveType, Html.ImageGetter::class.java) {
                        val string = it.args[0] as String
                        it.args[0] = renderer.render(parser.parse(string))
                        //LogUtil.d(it.args[0] as String)
                    }
            "android.text.Html"
                    .hookBeforeMethod("fromHtml", String::class.java, Html.ImageGetter::class.java, Html.TagHandler::class.java) {}
        }
    }
}