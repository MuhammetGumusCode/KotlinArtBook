package com.muhammetgumus.kotlinartbook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.muhammetgumus.kotlinartbook.databinding.ActivityMainBinding
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var artlist : ArrayList<Art>
    private lateinit var artAdapter : ArtAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


             artlist = ArrayList<Art>()
             artAdapter = ArtAdapter (artlist)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = artAdapter

    }







    // Bu fonksiyonu MainActivity sınıfının içine ekle (örneğin onCreate'in altına)
    override fun onResume() {
        super.onResume()

        // Veritabanından veri çekme işlemini buraya taşı
        try {
            // Her seferinde listeyi temizle ki veriler çiftlenmesin
            artlist.clear()

            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null)
            val cursor = database.rawQuery("SELECT * FROM arts", null)
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")
            val imageIx = cursor.getColumnIndex("image")



            while (cursor.moveToNext()) {
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val image = cursor.getBlob(imageIx)
                val art = Art(name, id, image)
                artlist.add(art)
            }

            // Adapter'a verilerin değiştiğini bildir, böylece ekran güncellenir
            artAdapter.notifyDataSetChanged()
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflater
        val menuınflater = menuInflater
        menuınflater.inflate (R.menu.art_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

          if (item.itemId == R.id.art_item ) {
         val intent  = Intent(this@MainActivity, ArtActivity2::class.java)
              intent.putExtra ("info","new")
              startActivity(intent)
          }
        return super.onOptionsItemSelected(item)

    }



}