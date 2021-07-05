package com.example.anyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.anyam.Companion.Companion.rupiah

class CartItem : AppCompatActivity() {
    companion object{
        const val EXTRA_NAMA = "nama"
        const val EXTRA_HARGA = "harga"
        const val EXTRA_SUBTOTAL = "subtotal"
        const val EXTRA_GAMBAR_CART = "gambarcart"
        const val EXTRA_QUANTITY = "jumlah_barang"
    }

    val ongkir: Double = 10000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_item)

        val tvDataReceivedNama: TextView = findViewById(R.id.nama_item)
        val tvDataReceivedHarga: TextView = findViewById(R.id.harga_item)
        val tvDataReceivedSubtotal: TextView = findViewById(R.id.sub_total)
        val tvDataReceivedGambar: ImageView = findViewById(R.id.gambar_chart)
        val tvQuantity: TextView = findViewById(R.id.quantity_item)
        val tvTotalJumlah: TextView = findViewById(R.id.total_jumlah)
        val btnBack: ImageView = findViewById(R.id.back)
        val btnCheckOut: Button = findViewById(R.id.checkout)
        val btnTujuanPengiriman: CardView = findViewById(R.id.tujuan_pengiriman)
        val btnMetodePembayaran: CardView = findViewById(R.id.metode_pembayaran)

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val harga = intent.getStringExtra(EXTRA_HARGA)
        val subTotal = intent.getDoubleExtra(EXTRA_SUBTOTAL,0.0)
        val gambar = intent.getIntExtra(EXTRA_GAMBAR_CART,0)
        val quantity = intent.getIntExtra(EXTRA_QUANTITY,0)

        val totalJumlah = subTotal+ongkir

        tvDataReceivedNama.text = nama
        tvDataReceivedHarga.text = harga
        tvDataReceivedSubtotal.text = rupiah.format(subTotal)
        tvTotalJumlah.text = rupiah.format((totalJumlah))
        tvQuantity.text = quantity.toString()
        tvDataReceivedGambar.setImageResource(gambar)

        btnTujuanPengiriman.setOnClickListener {
            val intent = Intent(this, TujuanPengiriman::class.java)
            startActivity(intent)
        }

        btnMetodePembayaran.setOnClickListener {
            val intent = Intent(this, MetodePembayaran::class.java)
            startActivity(intent)
        }

        btnCheckOut.setOnClickListener {
            Toast.makeText(this, "Selamat pesanan anda sudah di teruskan ke penjual, segera lakukan pembayaran $totalJumlah dan tunggu konfirmasi dari penjual hingga maksimal 2 hari", Toast.LENGTH_LONG).show()
            val intent = Intent(this, TaskSuccess::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            finish()
        }

    }
}