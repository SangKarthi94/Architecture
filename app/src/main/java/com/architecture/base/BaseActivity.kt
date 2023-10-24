package com.architecture.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.architecture.R

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}