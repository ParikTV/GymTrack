package com.example.gymtracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Pull_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pull)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val MsgAceptar: Button = findViewById<Button>(R.id.btnAceptar)
        MsgAceptar.setOnClickListener(View.OnClickListener { view ->
        })
        val Show_Exercises_Screen: Button = findViewById<Button>(R.id.btnVerEjercicios)
        Show_Exercises_Screen.setOnClickListener(View.OnClickListener { view ->
            val intentShow_Exercises_Screen = Intent(this, Show_Exercises::class.java)
            startActivity(intentShow_Exercises_Screen)
        })
        val Show_Routines_Screen: Button = findViewById<Button>(R.id.btnVerRutinas)
        Show_Routines_Screen.setOnClickListener(View.OnClickListener { view ->
            val intentShow_Routines_Screen = Intent(this, Show_Routines::class.java)
            startActivity(intentShow_Routines_Screen)
        })
    }
}