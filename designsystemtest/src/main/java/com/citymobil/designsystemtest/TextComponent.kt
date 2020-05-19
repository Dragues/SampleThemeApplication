package com.citymobil.designsystemtest

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TextComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.ds_text_component_style
) : AppCompatTextView(context, attrs, defStyleAttr)
