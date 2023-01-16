package com.example.birthdaybuddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val birthdayBuddyButton = findViewById<Button>(R.id.Birthdaybuddybutton);
        val calendarButton = findViewById<Button>(R.id.Calendarbutton)
        val notificationsButton = findViewById<Button>(R.id.Notificationsbutton)

        birthdayBuddyButton.setOnClickListener {
            val intent = Intent(this, ContactOverview::class.java)
            startActivity(intent)
        }
        calendarButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Calendar::class.java)
            startActivity(intent)
        }
        notificationsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, Notifications::class.java)
            startActivity(intent)
        }
    }
}