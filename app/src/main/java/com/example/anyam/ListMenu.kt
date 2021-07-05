package com.example.anyam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView

class ListMenu : AppCompatActivity() {
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
            val intent = Intent(this, Contacts::class.java)
            startActivity(intent)
        }

        val bantuan: CardView = findViewById(R.id.bantuan)
        bantuan.setOnClickListener {
            val intent = Intent(this, Bantuan::class.java)
            startActivity(intent)
        }

        val info: CardView = findViewById(R.id.info)
        info.setOnClickListener {
            val intent = Intent(this, Info::class.java)
            startActivity(intent)
        }

        val back: ImageView = findViewById(R.id.back)
        back.setOnClickListener {
            finish()
        }


    }
}