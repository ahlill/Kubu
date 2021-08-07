package com.example.anyam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.anyam.API.CostItem
import com.example.anyam.Companion.Companion.rupiah
import com.example.anyam.databinding.ActivityCartItemBinding
//import com.example.latihanspinner.API.CostItem
import com.example.anyam.API.DataModelCost
import com.example.anyam.API.DataRepository
import com.example.anyam.API.PostModelCost
import com.example.anyam.AddressActivity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartItemActivity : AppCompatActivity() {

    lateinit var dataPengiriman: SharedPreferences
    lateinit var binding: ActivityCartItemBinding
    companion object{
        const val EXTRA_NAMA = "nama"
        const val EXTRA_HARGA = "harga"
        const val EXTRA_SUBTOTAL = "subtotal"
        const val EXTRA_GAMBAR_CART = "gambarcart"
        const val EXTRA_QUANTITY = "jumlah_barang"
    }

    var totalJumlah: Double = 0.0
    var subTotal: Double = 0.0
    var ongkir: Double = 0.0
    var etd: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartItemBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dataPengiriman = getSharedPreferences("data_pengiriman", Context.MODE_PRIVATE)

//        val origin: String = "285" // id banda aceh, syiah kuala.
//        val originType: String = "subdistrict"
//        val destination: String? = dataPengiriman.getString("subdistrict_id", "")
//        val destinationType: String = "subdistrict"
//        val weight: Int = 300
//        val courier: String = "pos"
//        tampilkanOngkosPengiriman(origin, originType, destination, destinationType, weight, courier)

        val nama = intent.getStringExtra(EXTRA_NAMA)
        val harga = intent.getStringExtra(EXTRA_HARGA)
        subTotal = intent.getDoubleExtra(EXTRA_SUBTOTAL, 0.0)
        val gambar = intent.getIntExtra(EXTRA_GAMBAR_CART, 0)
        val quantity = intent.getIntExtra(EXTRA_QUANTITY, 0)

        Log.d("total", subTotal.toString())


//        binding.tvNamaPenerima.text = dataPengiriman.getString("nama_penerima", "Tidak ada nama")
//        binding.tvAlamat.text = dataPengiriman.getString("alamat_lengkap", "Tambahakan alamat")
        binding.tvDataReceivedNama.text = nama
        binding.tvDataReceivedHarga.text = harga
        binding.tvDataReceivedSubtotal.text = rupiah.format(subTotal)
        binding.tvQuantity.text = quantity.toString()
        binding.tvDataReceivedGambar.setImageResource(gambar)

        binding.btnTujuanPengiriman.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivity(intent)
        }

        binding.btnMetodePembayaran.setOnClickListener {
            val intent = Intent(this, MetodePembayaran::class.java)
            startActivity(intent)
        }

        binding.btnCheckOut.setOnClickListener {
            Toast.makeText(
                this,
                "Selamat pesanan anda sudah di teruskan ke penjual, segera lakukan pembayaran $totalJumlah dan tunggu konfirmasi dari penjual hingga maksimal 2 hari",
                Toast.LENGTH_LONG
            ).show()
            val intent = Intent(this, TaskSuccess::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        val origin: String = "285" // id banda aceh, syiah kuala.
        val originType: String = "subdistrict"
        val destination: String? = dataPengiriman.getString("subdistrict_id", "")
        val destinationType: String = "subdistrict"
        val weight: Int = 300
        val courier: String = "pos"

        binding.tvNamaPenerima.text = dataPengiriman.getString("nama_penerima", "Tidak ada nama")
        binding.tvAlamat.text = dataPengiriman.getString("alamat_lengkap", "Tambahakan alamat")
        tampilkanOngkosPengiriman(origin, originType, destination, destinationType, weight, courier)
//        Toast.makeText(applicationContext, "resume", Toast.LENGTH_SHORT).show()
        super.onResume()
    }

    private fun tampilkanOngkosPengiriman(origin: String, originType: String, destination: String?, destinationType: String, weight: Int, courier: String) {
        val postCost = DataRepository.createCost()
        Log.d("origin", "$origin, $originType, $destination, $destinationType, $weight, $courier")
        postCost.getPosts(DataModelCost(origin, originType, destination, destinationType, weight, courier)).enqueue(object : Callback<PostModelCost> {
            override fun onResponse(call: Call<PostModelCost>, response: Response<PostModelCost>) {
                if (response.isSuccessful) {
                    val dataObject: CostItem? = response.body()?.rajaongkir?.results?.get(0)?.costs?.get(0)?.cost?.get(0)
                    val err = response.body()?.rajaongkir?.status?.code

                    ongkir = dataObject?.value?.toDouble() ?: 0.0
                    etd = dataObject?.etd?.toString() ?: ""

                    totalJumlah = subTotal + ongkir

                    binding.tvOngkosKirim.text = rupiah.format(ongkir)
                    binding.tvTotalJumlah.text = rupiah.format(totalJumlah)


                    Log.d("ongir", "$ongkir, $etd")
                }
            }
            override fun onFailure(call: Call<PostModelCost>, t: Throwable) {
            }

        })
    }

}