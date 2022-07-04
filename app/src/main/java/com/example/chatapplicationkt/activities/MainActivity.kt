package com.example.chatapplicationkt.activities

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import com.example.chatapplicationkt.R
import com.example.chatapplicationkt.databinding.ActivityMainBinding
import com.example.chatapplicationkt.utils.Constants
import com.example.chatapplicationkt.utils.PreferenceManager

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null
    private var preferenceManager: PreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        preferenceManager = PreferenceManager()
        loadUserDetails()
    }

    private fun loadUserDetails() {
        binding?.textName?.text = preferenceManager?.getString(Constants.KEY_NAME)
        /*val bytes = Base64.decode(preferenceManager?.getString(Constants.KEY_IMAGE), Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        binding?.imageProfile?.setImageBitmap(bitmap)*/
    }
}