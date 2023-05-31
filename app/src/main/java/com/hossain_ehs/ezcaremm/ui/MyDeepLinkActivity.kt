package com.hossain_ehs.ezcaremm.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.hossain_ehs.ezcaremm.R
import com.hossain_ehs.ezcaremm.databinding.ActivityMyDeepLinkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyDeepLinkActivity : AppCompatActivity() {
   private lateinit var binding: ActivityMyDeepLinkBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyDeepLinkBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.apply {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.mNavHostFragment2)
                        as NavHostFragment
            val navController = navHostFragment.findNavController()

            val deeplink = NavDeepLinkRequest.Builder.fromUri(
                Uri.parse(
                    getString(com.hossain_ehs.core.R.string.deep_link_to_alarm)
                )
            ).build()
            navController.navigate(deeplink)//
        }

    }
}