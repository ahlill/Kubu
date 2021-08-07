package com.example.anyam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.anyam.API.ResultsItemSubdistrict
import com.example.anyam.databinding.ActivityAddressBinding
import com.example.anyam.API.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding
    lateinit var dataPengiriman: SharedPreferences

    var nameProvinceTujuan: String = ""
    var nameCityTujuan: String = ""
    var nameSubdistrictTujuan: String = ""

    var dataCityTujuan = mutableListOf<String?>()
    var dataSubdistrictTujuan = mutableListOf<String?>()

    var alamatTujuan: ResultsItemSubdistrict? = null

    var namaPenerima: String = ""
    var alamatLengkap: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dataPengiriman = getSharedPreferences("data_pengiriman", Context.MODE_PRIVATE)

        tampilkanDataProvinsiTujuan()

        binding.btnSimpanAlamat.setOnClickListener {

            namaPenerima = binding.etNamaPenerima.text.toString()
            alamatLengkap = binding.etAlamatLengkap.text.toString()
            when {
                namaPenerima.isBlank() -> Toast.makeText(applicationContext, "isi nama", Toast.LENGTH_SHORT).show()
                alamatLengkap.isBlank() -> Toast.makeText(applicationContext, "isi alamat", Toast.LENGTH_SHORT).show()
                alamatTujuan?.subdistrictId.isNullOrEmpty() -> Toast.makeText(applicationContext, "pilih alamat", Toast.LENGTH_SHORT).show()
                else -> {
                    simpanDataPengiriman()
                    finish()
                }
            }

        }
    }

    private fun tampilkanDataProvinsiTujuan() {
        val postProvince = DataRepository.createProvince()
        postProvince.getPosts().enqueue(object : Callback<PostModelProvince> {
            override fun onResponse(call: Call<PostModelProvince>, response: Response<PostModelProvince>) {
                if (response.isSuccessful) {
                    val dataObject: List<ResultsItemProvince>? = response.body()?.rajaongkir?.results
                    val dataProvinceTujuan = mutableListOf<String?>()
                    val dataProvinceId = mutableListOf<String?>()

                    for (i in dataObject!!) {
                        dataProvinceTujuan.add(i.province)
                        dataProvinceId.add(i.provinceId)
                    }

                    val spinnerProvince: Spinner = findViewById(R.id.spinnerProvinceTujuan)
                    val adapter = ArrayAdapter(this@AddressActivity, R.layout.support_simple_spinner_dropdown_item, dataProvinceTujuan)
                    spinnerProvince.adapter = adapter

                    spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            nameProvinceTujuan = spinnerProvince.selectedItem.toString()
                            val idProvince = spinnerProvince.selectedItemId.toInt()
                            dataCityTujuan = mutableListOf<String?>()
                            dataSubdistrictTujuan = mutableListOf<String?>()

                            reloadSpinnerCityTujuan()
                            reloadSpinnerSubdistrictTujuan()
                            dataProvinceId.get(idProvince)?.let { tampilkanDataCityTujuan(it.toInt()) }


                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostModelProvince>, t: Throwable) {

            }
        })
    }

    private fun tampilkanDataCityTujuan(idProvince: Int) {
        val postCity = DataRepository.createCity()
        postCity.getPosts(idProvince).enqueue(object : Callback<PostModelCity> {
            override fun onResponse(call: Call<PostModelCity>, response: Response<PostModelCity>) {
                if (response.isSuccessful) {
                    val dataObject: List<ResultsItemCity>? = response.body()?.rajaongkir?.results
                    val dataCityId = mutableListOf<String?>()

                    for (i in dataObject?.indices!!) {
                        dataCityTujuan.add(dataObject[i].cityName)
                        dataCityId.add(dataObject[i].cityId)
                    }

                    val spinnerCity: Spinner = findViewById(R.id.spinnerCityTujuan)
                    val adapter = ArrayAdapter(this@AddressActivity, R.layout.support_simple_spinner_dropdown_item, dataCityTujuan)
                    spinnerCity.adapter = adapter

                    spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            nameCityTujuan = spinnerCity.selectedItem.toString()
                            val idCity = spinnerCity.selectedItemId.toInt()
                            dataSubdistrictTujuan = mutableListOf<String?>()

                            reloadSpinnerSubdistrictTujuan()
                            dataCityId[idCity]?.let { tampilkanDataSubdistrictTujuan(it.toInt()) }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostModelCity>, t: Throwable) {
            }
        })
    }

    private fun tampilkanDataSubdistrictTujuan(city: Int) {
        val postSubdistrict = DataRepository.createSubdistrict()
        postSubdistrict.getPosts(city).enqueue(object : Callback<PostModelSubdistrict> {
            override fun onResponse(call: Call<PostModelSubdistrict>, response: Response<PostModelSubdistrict>) {
                if (response.isSuccessful) {
                    val dataObject: List<ResultsItemSubdistrict>? = response.body()?.rajaongkir?.results
                    val dataSubdistrictId = mutableListOf<String?>()

                    for (i in dataObject?.indices!!) {
                        dataSubdistrictTujuan.add(dataObject[i].subdistrictName)
                        dataSubdistrictId.add(dataObject[i].subdistrictId)
                    }

                    val spinnerSubdistrict: Spinner = findViewById(R.id.spinnerSubdistrictTujuan)
                    val adapter = ArrayAdapter(this@AddressActivity, R.layout.support_simple_spinner_dropdown_item, dataSubdistrictTujuan)
                    spinnerSubdistrict.adapter = adapter

                    spinnerSubdistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            nameSubdistrictTujuan = spinnerSubdistrict.selectedItem.toString()
                            alamatTujuan = ResultsItemSubdistrict()
                            for (i in dataObject?.indices) {
                                if (dataObject[i].subdistrictName.equals(nameSubdistrictTujuan)) {
                                    alamatTujuan = dataObject[i]
                                }
                            }
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PostModelSubdistrict>, t: Throwable) {
            }

        })
    }

    private fun simpanDataPengiriman(){
        val dataPengiriman: SharedPreferences.Editor = dataPengiriman.edit()
        dataPengiriman.putString("nama_penerima", namaPenerima)
        dataPengiriman.putString("subdistrict_id", alamatTujuan?.subdistrictId)
        dataPengiriman.putString("province_id", alamatTujuan?.provinceId)
        dataPengiriman.putString("province", alamatTujuan?.province)
        dataPengiriman.putString("city_id", alamatTujuan?.cityId)
        dataPengiriman.putString("city", alamatTujuan?.city)
        dataPengiriman.putString("type", alamatTujuan?.type)
        dataPengiriman.putString("subdistrict_name", alamatTujuan?.subdistrictName)
        dataPengiriman.putString("alamat_lengkap", alamatLengkap)
        dataPengiriman.apply()
    }

    private fun reloadSpinnerCityTujuan() {
        val spinnerCity: Spinner = findViewById(R.id.spinnerCityTujuan)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dataCityTujuan)
        spinnerCity.adapter = adapter
    }

    private fun reloadSpinnerSubdistrictTujuan() {
        val spinnerSubdistrict: Spinner = findViewById(R.id.spinnerSubdistrictTujuan)
        val adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, dataSubdistrictTujuan)
        spinnerSubdistrict.adapter = adapter
    }
}