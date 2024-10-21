package com.example.gymtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        val PushScreen: Button = findViewById<Button>(R.id.PushBtn)
        PushScreen.setOnClickListener(View.OnClickListener { view ->
            val intentPushScreen = Intent(this, Push_Activity::class.java)
            startActivity(intentPushScreen)
        })

        val PullScreen: Button = findViewById<Button>(R.id.PullBtn)
        PullScreen.setOnClickListener(View.OnClickListener { view ->
            val intentPullScreen = Intent(this, Pull_Activity::class.java)
            startActivity(intentPullScreen)
        })

        val LegScreen: Button = findViewById<Button>(R.id.LegBtn)
        LegScreen.setOnClickListener(View.OnClickListener { view ->
            val intentLegScreen = Intent(this, Leg_Activity::class.java)
            startActivity(intentLegScreen)
        })

        val RoutinesScreen: Button = findViewById<Button>(R.id.ShowRoutinesBtn)
        RoutinesScreen.setOnClickListener(View.OnClickListener { view ->
            val intentLegScreen = Intent(this, Show_Routines::class.java)
            startActivity(intentLegScreen)
        })

    }
}
