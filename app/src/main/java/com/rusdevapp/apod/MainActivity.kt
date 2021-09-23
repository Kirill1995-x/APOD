package com.rusdevapp.apod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.rusdevapp.apod.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(){

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState==null) {
            binding.tvTitleAPOD.text = intent.getStringExtra("title")
            binding.tvExplanationAPOD.text = intent.getStringExtra("explanation")
            Picasso.get().load(intent.getStringExtra("url")).into(binding.imgAPOD)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString("title", binding.tvTitleAPOD.text.toString())
        outState.putString("explanation", binding.tvExplanationAPOD.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.tvTitleAPOD.text = savedInstanceState.getString("title")
        binding.tvExplanationAPOD.text = savedInstanceState.getString("explanation")
    }
}