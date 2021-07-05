package com.example.anyam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.anyam.Companion.Companion.rupiah

class GridItemAdapter(val listItem: ArrayList<DataModelItem>): RecyclerView.Adapter<GridItemAdapter.GridViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridItemAdapter.GridViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_view_item, parent,false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = listItem[position]

        Glide.with(holder.itemView.context)
            .load(listItem[position].gambarCarosel[0])
            .apply(RequestOptions().override(1000,1000))
            .into(holder.imgGambar)

        holder.tvNama.text = item.nama
        holder.tvHarga.text = rupiah.format(item.harga)
        holder.tvPenjualan.text = item.penjualan.toString()

        holder.itemView.setOnClickListener {onItemClickCallback.onItemClicked(listItem[holder.adapterPosition]) }

    }



    inner class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvNama: TextView = itemView.findViewById(R.id.nama_item)
        var tvHarga: TextView = itemView.findViewById(R.id.harga_item)
        var imgGambar: ImageView = itemView.findViewById(R.id.img_item_cardview)
        var tvPenjualan: TextView = itemView.findViewById(R.id.penjualan_item)
    }

    interface OnItemClickCallback {
        fun onItemClicked (data: DataModelItem)
    }

}