package com.example.lemiforce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var floatButton: View = findViewById(R.id.btnOdaberiKategoriju)
        floatButton.setOnClickListener {
            odaberiKategoriju()
        }
    }

    fun odaberiKategoriju(){
        var intent = Intent(this,OdabirKategorije::class.java)
        startActivity(intent)
    }
}