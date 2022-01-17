package com.example.mvvmexample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvmexample.R
import com.example.mvvmexample.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient

class MainActivity : AppCompatActivity() {

    private val TAG = "MAIN_ACTIVITY"
    private lateinit var locationClient: FusedLocationProviderClient

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.clRoot.id, MainFragment.newInstance())
                .commitNow()
        }
    }


}