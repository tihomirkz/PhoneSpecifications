package com.gan.phonespecifications

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gan.phonespecifications.data.DetailData
import com.gan.phonespecifications.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = MainViewModel()


        try {
            val value = intent.getStringExtra("key")
            viewModel.getDetails(MainRepository(retrofitService).getDetails(value!!))
        } catch (e: Exception) {
            e.printStackTrace()
        }

        viewModel.details.observe(this, Observer {
            addDetails(it)
            progressDialog.isVisible = false
        })

        viewModel.errorMessage.observe(this, Observer {
            progressDialog.isVisible = false
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun addDetails(it: DetailData) {
        Glide.with(this).load(it.thumbnail ?: R.drawable.no_image).into(thumbnail)
        brand.text = it.brand
        model.text =  it.phone_name
        os.text =  it.os
        release_date.text =  it.release_date
        storage.text =  it.storage
    }
}