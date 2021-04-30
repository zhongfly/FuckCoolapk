package com.fuckcoolapk.view

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.fuckcoolapk.utils.CoolContext
import com.fuckcoolapk.utils.OwnSP
import com.fuckcoolapk.utils.dp2px
import com.fuckcoolapk.utils.isNightMode

class FuckEditText(context: Context) : EditText(context) {
    var color = if (isNightMode(getContext())) "#ffffff" else "#000000"
        set(value) = setTextColor(Color.parseColor(value))
    var sharedPreferences = OwnSP.ownSP
    private val editor by lazy { sharedPreferences.edit() }
    var key = ""
        set(value) {
            if (sharedPreferences.getString(value, "") != "") setText(sharedPreferences.getString(value, ""))
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.toString() == "") editor.remove(value) else editor.putString(value, s.toString())
                    editor.apply()
                }
            })
        }

    init {
        setPadding(dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f), dp2px(getContext(), 10f))
        setTextColor(Color.parseColor(color))
    }

    class Builder(private val mContext: Context = CoolContext.context, private val block: FuckEditText.() -> Unit) {
        fun build() = FuckEditText(mContext).apply(block)
    }

    class FastBuilder(private val mContext: Context = CoolContext.context, private val mHint: String? = null, private val mKey: String) {
        fun build() = FuckEditText(mContext).apply {
            mHint?.let { hint = it }
            key = mKey
        }
    }
}