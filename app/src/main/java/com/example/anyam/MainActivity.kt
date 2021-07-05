package com.example.anyam

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvItem: RecyclerView
    private var list: ArrayList<DataModelItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItem = findViewById(R.id.rv_item)
        rvItem.setHasFixedSize(true)

        list.addAll(DataItem.listData)
        showRecyclerGrid()

        val shoppingCart: ImageView = findViewById(R.id.shopping_cart)
        shoppingCart.setOnClickListener {
            val intent = Intent(this, ShoppingCart::class.java)
            startActivity(intent)
        }

        val listMenu: ImageView = findViewById(R.id.list_menu)
        listMenu.setOnClickListener {
            val intent = Intent(this, ListMenu::class.java)
            startActivity(intent)
        }
    }

    private fun showRecyclerGrid(){
        rvItem.layoutManager = GridLayoutManager(this,2)
        val gridItemAdapter = GridItemAdapter(list)
        rvItem.adapter = gridItemAdapter

        gridItemAdapter.setOnItemClickCallback(object : GridItemAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataModelItem) {
                showSelectedItem(data)
            }
        })
    }

    private fun showSelectedItem(item: DataModelItem){
        val moveWithDataIntent = Intent(this, DetailAdapter::class.java)
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_NAMA, item.nama)
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_HARGA, item.harga)
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_DESKRIPSI, item.deskripsi)
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_GAMBAR, item.gambarCarosel[0])
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_GAMBAR_CAROUSEL, item.gambarCarosel)
        moveWithDataIntent.putExtra(DetailAdapter.EXTRA_PENJUALAN, item.penjualan)
        startActivity(moveWithDataIntent)
    }
}