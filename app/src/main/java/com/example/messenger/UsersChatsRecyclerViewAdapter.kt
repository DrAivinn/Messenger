package com.example.messenger

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.UsersChatsItemBinding
import com.example.messenger.utils.loadImage


class UsersChatsRecyclerViewAdapter(val onClick: (User) -> Unit) :
    RecyclerView.Adapter<UsersChatsRecyclerViewAdapter.UsersChatsViewHolder>() {
    class UsersChatsViewHolder(val binding: UsersChatsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var usersList = mutableListOf<User>()

    @SuppressLint("NotifyDataSetChanged")
    fun upDateUsersList(list: MutableList<User>) {
        usersList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersChatsViewHolder {
        val binding = UsersChatsItemBinding.inflate(LayoutInflater.from(parent.context))
        return UsersChatsViewHolder(binding)
    }

    override fun getItemCount() = usersList.size

    override fun onBindViewHolder(holder: UsersChatsViewHolder, position: Int) {
        with(holder.binding) {
            val user = usersList[position]
            userNickNameTV.text = user.nickName
            userImageIV.loadImage(user.imageURL)
            root.setOnClickListener { onClick(user) }
        }
    }
}