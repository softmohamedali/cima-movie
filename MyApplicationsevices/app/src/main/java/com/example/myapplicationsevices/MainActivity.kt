package com.example.myapplicationsevices

import android.app.NotificationChannel
import android.app.NotificationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myapplicationsevices.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding:ActivityMainBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNotification.setOnClickListener {
            createNotification()
        }
    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            val channel=NotificationChannel("noti","myfirstNotifiction",NotificationManager.IMPORTANCE_DEFAULT)
            channel.description="my channel description"
            val notifimanger=getSystemService(NotificationManager::class.java)
            notifimanger.createNotificationChannel(channel)
        }
        val notifi=NotificationCompat.Builder(this,"noti")

        notifi.setContentTitle("contentt title")
            .setContentText("content text")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_launcher_background)

        val notificatiomc=NotificationManagerCompat.from(this)
        notificatiomc.notify(10,notifi.build())
    }
}
