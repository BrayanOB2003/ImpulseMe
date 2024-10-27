package com.example.impulseme

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.example.impulseme.databinding.ActivityMainBinding
import com.example.impulseme.services.NotificationServices

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val service = NotificationServices(this, "notification_test")
        var count = 0
        binding.button.setOnClickListener {
            service.createSimpleNotification(count++, "Recuerdame", "Solo tienes que entrar...")
        }
    }


}