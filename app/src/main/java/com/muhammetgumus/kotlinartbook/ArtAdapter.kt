package com.muhammetgumus.kotlinartbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.muhammetgumus.kotlinartbook.databinding.RecyclerRowBinding

// 1. Adapter'a veri kaynağı olarak bir ArrayList<Art> ekledik.
class ArtAdapter(val artList: ArrayList<Art>) : RecyclerView.Adapter<ArtAdapter.ArtHolder>() {

    // ArtHolder sınıfınız zaten doğruydu, bir değişiklik gerekmedi.
    class ArtHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
    }



    // 2. onCreateViewHolder: Her bir satırın layout'unu (recycler_row.xml) bağlar.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtHolder(binding)
    }

    // 3. onBindViewHolder: Her bir satırın içeriğini doldurur.
    override fun onBindViewHolder(holder: ArtHolder, position: Int) {
        // artList'teki ilgili 'Art' nesnesinin 'artname' özelliğini TextView'e yazar.
        // XML dosyanızdaki TextView'in ID'si 'recycler_row_textview' olmalı.
        holder.binding.recyclerViewtext.text = artList.get(position).name

        // Satıra tıklandığında ne olacağını belirler.
        holder.itemView.setOnClickListener {
            // Tıklanan satırın verisiyle birlikte ArtActivity2'yi açar.
            val intent = Intent(holder.itemView.context, ArtActivity2::class.java)
                 intent.putExtra ("info","old")
                 intent.putExtra("id",artList.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }

    // 4. getItemCount: Listenin toplam eleman sayısını döndürür.
    override fun getItemCount(): Int {
        return artList.size
    }
}
