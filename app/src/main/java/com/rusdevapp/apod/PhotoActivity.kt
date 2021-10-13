package com.rusdevapp.apod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import com.rusdevapp.apod.databinding.ActivityPhotoBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.IOException
import java.lang.Exception

class PhotoActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding:ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitleAPOD.text = intent.getStringExtra("title")
        binding.tvExplanationAPOD.text = intent.getStringExtra("explanation")
        Picasso.get()
               .load(intent.getStringExtra("url"))
               .error(R.drawable.nophoto)
               .into(binding.imgAPOD, object:Callback
               {
                   override fun onSuccess()
                   {
                       binding.tvTitleAPOD.visibility=View.VISIBLE
                       binding.btnSetWallpaper.visibility=View.VISIBLE
                       binding.tvExplanationAPOD.visibility=View.VISIBLE
                       binding.pbPhoto.visibility=View.GONE
                   }
                   override fun onError(e: Exception?)
                   {
                       binding.pbPhoto.visibility=View.GONE
                   }
               })
        binding.btnSetWallpaper.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id)
        {
            R.id.btnSetWallpaper -> selectWallpaper()
        }
    }

    private fun selectWallpaper() {
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(this)
        try {
            val bitmapDrawable:BitmapDrawable = binding.imgAPOD.drawable as BitmapDrawable
            wallpaperManager.setBitmap(bitmapDrawable.bitmap)
        }
        catch (e:IOException) {
            e.printStackTrace()
        }

    }
}