package com.example.anyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.anyam.Companion.Companion.rupiah
import com.example.anyam.Room.Cart
import com.example.anyam.Room.CartDB
import com.synnapps.carouselview.CarouselView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailAdapter : AppCompatActivity() {

    companion object{
        const val EXTRA_NAMA = "nama"
        const val EXTRA_HARGA = "harga"
        const val EXTRA_DESKRIPSI = "deskripsi"
        const val EXTRA_GAMBAR = "gambar"
        const val EXTRA_GAMBAR_CAROUSEL = "gambarcarousel"
        const val EXTRA_PENJUALAN = "penjualan"
        const val EXTRA_QUANTITY = "quantity"
    }

    private val db by lazy { CartDB(this) }
    private var cartId = 0

    var quantity: Int = 1
    var harga: Double = 0.0
    var totalHarga: Double = 0.0
    var totalHargaRupiah: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_adapter)

        val tvDataReceivedNama: TextView = findViewById(R.id.nama_item)
        val tvDataReceivedHarga: TextView = findViewById(R.id.harga_item)
        val tvDataReceivedDeskripsi: TextView = findViewById(R.id.deskripsi_item)
//        val tvDataReceivedGambar: ImageView = findViewById(R.id.gambar_item)
        val btnTambah: Button = findViewById(R.id.button_tambah)
        val btnKurang: Button = findViewById(R.id.button_kurang)
        val btnBack: ImageView = findViewById(R.id.back)


        val nama = intent.getStringExtra(EXTRA_NAMA)
            harga = intent.getDoubleExtra(EXTRA_HARGA,0.0)
        val deskripsi = intent.getStringExtra(EXTRA_DESKRIPSI)
        val gambar = intent.getIntExtra(EXTRA_GAMBAR,0)
        val gambarCarousel = intent.getIntArrayExtra(EXTRA_GAMBAR_CAROUSEL)

//        carousel
        val carousel_view: CarouselView = findViewById(R.id.carousel_view)
        carousel_view.pageCount = gambarCarousel.size
        carousel_view.setImageListener{ position, imageView ->
            imageView.setImageResource(gambarCarousel[position])
        }

        val hargaRupiah = rupiah.format(harga)

        tvDataReceivedNama.text = nama
        tvDataReceivedHarga.text = hargaRupiah
        tvDataReceivedDeskripsi.text = deskripsi
        displayTotalHarga(hargaRupiah)


        val btnBeli: Button = findViewById(R.id.beli)
        btnBeli.setOnClickListener{
            totalHarga = quantity * harga
            totalHargaRupiah = rupiah.format((quantity * harga))

            val moveWithDataIntent = Intent(this, CartItem::class.java)
            moveWithDataIntent.putExtra(CartItem.EXTRA_NAMA, nama)
            moveWithDataIntent.putExtra(CartItem.EXTRA_HARGA, hargaRupiah)
            moveWithDataIntent.putExtra(CartItem.EXTRA_SUBTOTAL, totalHarga)
            moveWithDataIntent.putExtra(CartItem.EXTRA_GAMBAR_CART, gambar)
            moveWithDataIntent.putExtra(CartItem.EXTRA_QUANTITY, quantity)
            startActivity(moveWithDataIntent)
        }


        btnBack.setOnClickListener {
            finish()
        }

        btnKurang.isEnabled = false
        btnTambah.setOnClickListener{
            increment(btnTambah, btnKurang)
        }

        btnKurang.setOnClickListener{
            decrement(btnTambah, btnKurang)
        }

        val btnMasukKeranjang : ImageView = findViewById(R.id.btn_masuk_keranjang)
        btnMasukKeranjang.setOnClickListener {
            totalHarga = quantity * harga
            totalHargaRupiah = rupiah.format((quantity * harga))
            val moveWithDataIntent = Intent(this, ShoppingCart::class.java)
//            moveWithDataIntent.putExtra(CartItem.EXTRA_SUBTOTAL, totalHarga)
            startActivity(moveWithDataIntent)
            setupLstener()
            Toast.makeText(this, "berhasil masuk keranjang", Toast.LENGTH_SHORT).show()
        }

    }


    fun increment (tambah: Button, kurang: Button){//perintah tombol tambah
        if(quantity == 100){
            Toast.makeText(this,"pesanan maximal 100",Toast.LENGTH_SHORT).show()
            tambah.isEnabled = false
            kurang.isEnabled = true
        }else {
            quantity += 1
            tambah.isEnabled = true
            kurang.isEnabled = true
            totalHarga = quantity * harga
            totalHargaRupiah = rupiah.format(totalHarga)
            displayTotalHarga(totalHargaRupiah)
            display(this.quantity)
        }
    }

    fun decrement(tambah: Button, kurang:Button){//perintah tombol tambah
        if (quantity == 1){
            Toast.makeText(this,"pesanan minimal 1", Toast.LENGTH_SHORT).show()
            tambah.isEnabled = true
            kurang.isEnabled = false
        }else {
            quantity -= 1
            tambah.isEnabled = true
            kurang.isEnabled = true
            totalHarga = quantity * harga
            totalHargaRupiah = rupiah.format(totalHarga)
             displayTotalHarga(totalHargaRupiah)
            display(quantity)
        }
    }
    private fun display(number : Int) {
        val quantityTextView: TextView = findViewById(R.id.quantity_textview)
        quantityTextView.text = "" + number
    }
    private fun displayTotalHarga(total: String) {
        val tvTotalHarga: TextView = findViewById(R.id.total_harga)
        tvTotalHarga.text = total
    }

    fun setupLstener() {
        val namaBarang = intent.getStringExtra(EXTRA_NAMA)
        val hargaBarang = intent.getDoubleExtra(EXTRA_HARGA,0.0)
        val gambarBarang = intent.getIntExtra(EXTRA_GAMBAR,0)
        val jumlahBarang = quantity
//        val totalHargaBarang = hargaBarang * jumlahBarang

        CoroutineScope(Dispatchers.IO).launch {
            db.cartDao().addCart(
                    Cart(
                            0,
                            namaBarang,
                            hargaBarang,
//                            totalHargaBarang,
                            jumlahBarang,
                            gambarBarang
                    )
            )
        }
    }

}