package com.rusdevapp.apod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.WallpaperManager
import android.graphics.drawable.BitmapDrawable
import com.rusdevapp.apod.databinding.ActivityPhotoBinding
import com.squareup.picasso.Picasso
import java.io.IOException

class PhotoActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding:ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTitleAPOD.text = intent.getStringExtra("title")
        binding.tvExplanationAPOD.text = intent.getStringExtra("explanation")
        Picasso.get().load(intent.getStringExtra("url")).into(binding.imgAPOD)
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