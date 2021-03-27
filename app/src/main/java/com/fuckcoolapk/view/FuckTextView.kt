package com.fuckcoolapk.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.widget.TextView
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.dp2px
import com.fuckcoolapk.utils.isNightMode
import com.fuckcoolapk.utils.sp2px

class FuckTextView(context: Context) : TextView(context) {

    var size: Float
        get() = textSize
        set(value) {
            textSize = value
        }
    var color = if (isNightMode(getContext())) "#ffffff" else "#000000"
        set(value) = setTextColor(Color.parseColor(value))
    var url = ""
        set(value) = setOnClickListener { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(value))) }

    init {
        setPadding(dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f))
        setTextColor(Color.parseColor(color))
    }

    class Builder(private val mContext: Context = CoolContext.context, private val block: FuckTextView.() -> Unit) {
        fun build() = FuckTextView(mContext).apply(block)
    }

    companion object {
        val titleSize = sp2px(CoolContext.context, 10f).toFloat()
        val title2Size = sp2px(CoolContext.context, 8f).toFloat()
        val textSize = sp2px(CoolContext.context, 6f).toFloat()
    }
}