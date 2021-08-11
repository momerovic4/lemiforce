package com.example.lemiforce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun odaberiKategoriju(view: View){
        var intent = Intent(this,OdabirKategorije::class.java)
        startActivity(intent)
    }
}