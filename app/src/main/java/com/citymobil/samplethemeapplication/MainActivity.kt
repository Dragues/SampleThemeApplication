package com.citymobil.samplethemeapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(if (false) R.style.TestTheme else R.style.TestTheme2)
        setContentView(R.layout.activity_main)
    }
}
