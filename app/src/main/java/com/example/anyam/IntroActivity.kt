package com.example.anyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageClickListener

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val potoProfil = intArrayOf(
            R.drawable.profil1,
            R.drawable.profil2,
            R.drawable.profil3
        )

//        carousel
        val carousel_view: CarouselView = findViewById(R.id.carousel_view)

        carousel_view.pageCount = potoProfil.size
        carousel_view.setImageListener{ position, imageView ->
            imageView.setImageResource(potoProfil[position])
        }

        val btnLogin: Button = findViewById(R.id.login)
        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}