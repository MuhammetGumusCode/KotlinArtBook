package com.muhammetgumus.kotlinartbook // 'import' kelimesi kaldırıldı

import android.Manifest // DOĞRU MANIFEST IMPORT EDİLDİ
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.muhammetgumus.kotlinartbook.databinding.ActivityArt2Binding
import java.io.IOException




   class ArtActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityArt2Binding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    var selectedBitmap: Bitmap? = null
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArt2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null)
        registerLauncher()



            val intent = intent
           val info = intent.getStringExtra("info")
           if (info.equals("new")) {

          binding.ArtNameText.setText("")
          binding.ArtistNameText.setText("")
          binding.YearNameText.setText("")
          binding.SaveButton.visibility = View.VISIBLE
          binding.imageView.setImageResource(R.drawable.select)

           }
           else {

             binding.SaveButton.visibility = View.INVISIBLE

           val selectedid = intent.getIntExtra("id",1)
           val cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?", arrayOf(selectedid.toString()))

           val artNameIx = cursor.getColumnIndex("artname")
           val artistNameIx = cursor.getColumnIndex("artistname")
           val yearIx = cursor.getColumnIndex("year")
           val imageIx = cursor.getColumnIndex("image")


           while (cursor.moveToNext()) {
               binding.ArtNameText.setText(cursor.getString(artNameIx))
               binding.ArtistNameText.setText(cursor.getString(artistNameIx))
               binding.YearNameText.setText(cursor.getString(yearIx))

               val byteArray = cursor.getBlob(imageIx)
               val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
               binding.imageView.setImageBitmap(bitmap)


           }

           cursor.close()
      }




    }











    fun savebuttonclicked(view: View) {
       val artName = binding.ArtNameText.text.toString()
        val artistName = binding.ArtistNameText.text.toString()
       val year = binding.YearNameText.text.toString()

          if (selectedBitmap != null && artName.isNotBlank() && artistName.isNotBlank() && year.isNotBlank() ) {

          val smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)


            val outputStream  = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            binding.imageView.setImageBitmap(smallBitmap)
            try {
             //  val database =this.openOrCreateDatabase("Arts",MODE_PRIVATE,null)
                database.execSQL ("CREATE TABLE IF NOT EXISTS arts (id INTEGER PRIMARY KEY AUTOINCREMENT,artname VARCHAR,artistname VARCHAR,year VARCHAR,image BLOB)")


              val sqlString = "INSERT INTO arts (artname,artistname,year,image) VALUES (?,?,?,?)"

              val statement = database.compileStatement(sqlString)
                statement.bindString(1,artName)
                statement.bindString(2,artistName)
                statement.bindString(3,year)
                statement.bindBlob(4,byteArray)
                statement.execute()

             Toast.makeText(this,"Kayıt Başarılı",Toast.LENGTH_LONG).show()


            }catch (e : Exception) {
                e.printStackTrace()
            }

               val intent = Intent(this@ArtActivity2,MainActivity::class.java)
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
               startActivity(intent)

        }
         else {

             Toast.makeText(this,"Lütfen Boşlukları doldurun",Toast.LENGTH_LONG).show()

         }


    }








       fun makeSmallerBitmap (image :Bitmap, maximumSize :Int) :Bitmap {
        var width =image.width
        var height =image.height

        var bitmapRatio : Double = width.toDouble() / height.toDouble()


        if (bitmapRatio>1) {

            width = maximumSize
            val scaledheight = width/bitmapRatio
            height =scaledheight .toInt()
        }
         else {
         height = maximumSize
         val scaledwidth = height*bitmapRatio
         width =scaledwidth .toInt()


           }


           return Bitmap.createScaledBitmap(image,width,height,true)
    }


 fun selectImage(view: View) {
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(this, permissionToRequest) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionToRequest)) {
                Snackbar.make(view, "Permission needed for gallery", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Give Permission") {
                        permissionLauncher.launch(permissionToRequest)
                    }.show()
            } else {
                permissionLauncher.launch(permissionToRequest)
            }
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageData = intentFromResult.data
                    // registerLauncher fonksiyonu içindeki try-catch bloğunu bununla değiştirin

                    try {
                        if (imageData != null) {
                            // if-else bloğunu bir atama ifadesi olarak doğru kullanın

                                val source = ImageDecoder.createSource(this.contentResolver, imageData)
                                selectedBitmap =  ImageDecoder.decodeBitmap(source)


                            // Bitmap başarıyla oluşturulduktan sonra ImageView'e ata
                            binding.imageView.setImageBitmap(selectedBitmap)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Hata: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        Toast.makeText(this, "Görsel işlenirken bir hata oluştu.", Toast.LENGTH_LONG).show()
                    }


                }
            }
        }


        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                activityResultLauncher.launch(intentToGallery)
            } else {
                Toast.makeText(this@ArtActivity2, "Permission needed!", Toast.LENGTH_LONG).show()
            }
        }
    }




    override fun onDestroy() {
        super.onDestroy()
        // Activity yok edilirken veritabanını KAPAT
        database.close()
    }


}
