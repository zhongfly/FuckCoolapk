package com.fuckcoolapk.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
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
        set(value) = setOnClickListener { CoolContext.activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(value))) }

    init {
        setPadding(dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f))
        setTextColor(Color.parseColor(color))
    }

    class Builder(private val mContext: Context = CoolContext.context, private val block: FuckTextView.() -> Unit) {
        fun build() = FuckTextView(mContext).apply(block)
    }

    class FastBuilder(private val mContext: Context = CoolContext.context, private val mText: String, private val mSize: Float? = null, private val mColor: String? = null, private val mUrl: String? = null, private val mOnClickListener: ((View) -> Unit)? = null) {
        fun build() = FuckTextView(mContext).apply {
            text = mText
            mSize?.let { size = it }
            mColor?.let { color = it }
            mUrl?.let { url = it }
            mOnClickListener?.let { setOnClickListener(it) }
        }
    }

    companion object {
        val titleSize = sp2px(CoolContext.context, 10f).toFloat()
        val title2Size = sp2px(CoolContext.context, 8f).toFloat()
        val textSize = sp2px(CoolContext.context, 6f).toFloat()
    }
}