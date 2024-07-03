package com.example.messenger

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.UsersListItemBinding
import com.example.messenger.utils.loadImage

class UsersListRecyclerViewAdapter :
    RecyclerView.Adapter<UsersListRecyclerViewAdapter.UsersListViewHolder>() {
    class UsersListViewHolder(val binding: UsersListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    var usersList = mutableListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun upDateUsersList(list: MutableList<User>) {
        usersList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val binding = UsersListItemBinding.inflate(LayoutInflater.from(parent.context))
        return UsersListViewHolder(binding)
    }

    override fun getItemCount() = usersList.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {

        with(holder.binding) {
            val user = usersList[position]
            userNickNameTV.text = user.nickName
            userImageIV.loadImage(user.imageUri)
        }
    }
}
