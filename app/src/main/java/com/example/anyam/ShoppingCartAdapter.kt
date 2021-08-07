package com.example.anyam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anyam.Room.Cart
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_shopping_cart_adapter.view.*
import kotlinx.android.synthetic.main.activity_shopping_cart_adapter.view.harga_item
import kotlinx.android.synthetic.main.activity_shopping_cart_adapter.view.nama_item

class ShoppingCartAdapter(private val carts: ArrayList<Cart>, private val listener: OnAdapterListener): RecyclerView.Adapter<ShoppingCartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_shopping_cart_adapter, parent, false)
        )
    }

    override fun getItemCount() = carts.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = carts[position]
        holder.view.nama_item.text = cart.namaBarang
        holder.view.harga_item.text = Companion.rupiah.format(cart.hargaBarang)
        holder.view.gambar_item.setImageResource(cart.gambarBarang)
        holder.view.quantity_item.text = cart.jumlahBarang.toString()

        holder.view.hapus_cart.setOnClickListener {
            listener.onDelete(cart)
        }

        holder.view.setOnClickListener{
            listener.onClick(cart)
        }
    }

    class CartViewHolder(val view: View): RecyclerView.ViewHolder(view)

    fun setData(list: List<Cart>){
        carts.clear()
        carts.addAll(list)
    }

    interface OnAdapterListener {
        fun onClick(note: Cart)
        fun onDelete(cart: Cart)
    }
}