package com.example.anyam

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import com.example.anyam.databinding.ActivityShoppingCartBinding

class ShoppingCartActivity : AppCompatActivity() {

    private val db by lazy { CartDB(this) }
    lateinit var cartAdapter: ShoppingCartAdapter
    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        loadData()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val cart = db.cartDao().getCart()
            withContext((Dispatchers.Main)) {
                cartAdapter.setData(cart)
                if (cart.isNullOrEmpty()) {
                    binding.empty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            cartAdapter.setData(db.cartDao().getCart())
            withContext(Dispatchers.Main) {
                cartAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = ShoppingCartAdapter(
                arrayListOf(),
                object : ShoppingCartAdapter.OnAdapterListener {
                    override fun onClick(cart: Cart) {
                        val hargaBarangRupiah = rupiah.format(cart.hargaBarang)
                        val hargaSubTotal = cart.hargaBarang * cart.jumlahBarang
                        startActivity(Intent(applicationContext, CartItemActivity::class.java)
                                .putExtra(CartItemActivity.EXTRA_NAMA, cart.namaBarang)
                                .putExtra(CartItemActivity.EXTRA_HARGA, hargaBarangRupiah)
                                .putExtra(CartItemActivity.EXTRA_SUBTOTAL, hargaSubTotal)
                                .putExtra(CartItemActivity.EXTRA_GAMBAR_CART, cart.gambarBarang)
                                .putExtra(CartItemActivity.EXTRA_QUANTITY, cart.jumlahBarang))
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

    private fun deleteAlert(cart: Cart) {
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
