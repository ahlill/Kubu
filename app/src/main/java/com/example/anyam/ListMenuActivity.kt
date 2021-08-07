package com.example.anyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView

class ListMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_menu)

        val orderan: FrameLayout = findViewById(R.id.orderan)
        orderan.setOnClickListener{
            val intent = Intent(this, BelanjaanTerbeli::class.java)
            startActivity(intent)
        }

        val contact: CardView = findViewById(R.id.contact)
        contact.setOnClickListener{
            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }

        val bantuan: CardView = findViewById(R.id.bantuan)
        bantuan.setOnClickListener {
            val intent = Intent(this, BantuanActivity::class.java)
            startActivity(intent)
        }

        val info: CardView = findViewById(R.id.info)
        info.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }


    }
}