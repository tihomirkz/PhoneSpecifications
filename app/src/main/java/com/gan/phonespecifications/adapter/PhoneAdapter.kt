package com.gan.phonespecifications.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gan.phonespecifications.DetailsActivity
import com.gan.phonespecifications.R
import com.gan.phonespecifications.data.Phone
import com.gan.phonespecifications.databinding.AdapterPhoneBinding


class PhoneAdapter: RecyclerView.Adapter<MainViewHolder>() {

    private var phonesList = mutableListOf<Phone>()

    fun setPhoneList(phones: List<Phone>) {
        this.phonesList = phones.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = AdapterPhoneBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val phone = phonesList[position]
        holder.binding.name.text = phone.phone_name
        Glide.with(holder.itemView.context).load(phone.image ?: R.drawable.no_image).into(holder.binding.imageview)

        holder.itemView.setOnClickListener {
            val phone = phonesList[position]
            val intent = Intent(it.context, DetailsActivity::class.java)
            intent.putExtra(
                "key",
                phone.slug
            )
            it.context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return phonesList.size
    }
}

class MainViewHolder(val binding: AdapterPhoneBinding) : RecyclerView.ViewHolder(binding.root) {

}
