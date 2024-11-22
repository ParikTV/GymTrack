package com.example.gymtracker

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gymtracker.util.Util

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val PushScreen: Button = findViewById(R.id.PushBtn)
        PushScreen.setOnClickListener {
            Util.openActivity(this, Push_Activity::class.java)
        }

        val PullScreen: Button = findViewById(R.id.PullBtn)
        PullScreen.setOnClickListener {
            Util.openActivity(this, Pull_Activity::class.java)
        }

        val LegScreen: Button = findViewById(R.id.LegBtn)
        LegScreen.setOnClickListener {
            Util.openActivity(this, Leg_Activity::class.java)
        }

        val RoutinesScreen: Button = findViewById(R.id.ShowRoutinesBtn)
        RoutinesScreen.setOnClickListener {
            Util.openActivity(this, Show_Routines::class.java)
        }
    }
}
