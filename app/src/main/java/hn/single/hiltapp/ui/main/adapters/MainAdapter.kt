package hn.single.hiltapp.ui.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hn.single.hiltapp.data.model.User
import hn.single.hiltapp.databinding.ItemLayoutBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.ItemViewHolder>() {

    private var users = mutableListOf<User>()

    fun addData(list: List<User>) {
        users.addAll(list)
    }


    inner class ItemViewHolder(
        private val binding: ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(user: User) {
            binding.textViewUserName.text = user.name
            binding.textViewUserEmail.text = user.email
            Glide.with(binding.imageViewAvatar.context)
                .load(user.avatar)
                .into(binding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemLayoutBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(users[position])
    }
}