package com.newactivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.newactivity.databinding.ActivityMainBinding
import com.newactivity.databinding.LayoutActionBarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var actionBar: LayoutActionBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        actionBar = root.actionBar

        actionBar.back.setOnClickListener { onBackPressed() }
        actionBar.previousScreen.setOnClickListener { onBackPressed() }
    }
}