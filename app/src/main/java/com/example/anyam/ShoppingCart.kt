package com.example.anyam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anyam.Room.Cart
import com.example.anyam.Room.CartDB
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.anyam.Companion.Companion.rupiah

class ShoppingCart: AppCompatActivity(){


    private val db by lazy { CartDB(this) }
    lateinit var cartAdapter: ShoppingCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_cart)
        setupRecyclerView()

        val btnBack: ImageView = findViewById(R.id.back)
        btnBack.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch{
            val cart = db.cartDao().getCart()
            Log.d("ShoppingCart", "data $cart")
            withContext((Dispatchers.Main)){
                cartAdapter.setData(cart)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        CoroutineScope(Dispatchers.IO).launch {
            cartAdapter.setData(db.cartDao().getCart())
            withContext(Dispatchers.Main) {
                cartAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView () {

        cartAdapter = ShoppingCartAdapter(
            arrayListOf(),
            object : ShoppingCartAdapter.OnAdapterListener {
                override fun onClick(cart: Cart) {
                    val hargaBarangRupiah = rupiah.format(cart.hargaBarang)
                    val hargaSubTotal = cart.hargaBarang * cart.jumlahBarang
                    startActivity(Intent(applicationContext, CartItem::class.java)
                            .putExtra(CartItem.EXTRA_NAMA, cart.namaBarang)
                            .putExtra(CartItem.EXTRA_HARGA, hargaBarangRupiah)
                            .putExtra(CartItem.EXTRA_SUBTOTAL, hargaSubTotal)
                            .putExtra(CartItem.EXTRA_GAMBAR_CART, cart.gambarBarang)
                            .putExtra(CartItem.EXTRA_QUANTITY, cart.jumlahBarang))
                }

                override fun onDelete(cart: Cart) {
                    deleteAlert(cart)
                }

            })
        list_cart.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = cartAdapter
        }

    }

    private fun deleteAlert(cart: Cart){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Konfirmasi Hapus")
            setMessage("Yakin hapus belanjaan ?")
            setNegativeButton("Batal") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Hapus") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.cartDao().deleteCart(cart)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        dialog.show()
    }
}