package com.example.messenger

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemMessageIncomingBinding
import com.example.messenger.databinding.ItemMessageOutgoingBinding
import com.example.messenger.utils.TYPE_INCOMING
import com.example.messenger.utils.TYPE_OUTGOING
import com.example.messenger.utils.user

class ChatRecyclerViewAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class OutgoingMessageViewHolder(val binding: ItemMessageOutgoingBinding) :
        RecyclerView.ViewHolder(binding.root)

    class IncomingMessageViewHolder(val binding: ItemMessageIncomingBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var messageList: List<Message> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Message>) {
        messageList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) =
        if (messageList[position].from == user.nickName) TYPE_OUTGOING else TYPE_INCOMING

    override fun getItemCount() = messageList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_OUTGOING) {
            val binding =
                ItemMessageOutgoingBinding.inflate(LayoutInflater.from(parent.context))
            OutgoingMessageViewHolder(binding)
        } else {
            val binding =
                ItemMessageIncomingBinding.inflate(LayoutInflater.from(parent.context))
            IncomingMessageViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messageList[position]
        when (holder) {
            is OutgoingMessageViewHolder -> holder.binding.outgoingMessageTV.text =
                message.body

            is IncomingMessageViewHolder -> holder.binding.incomingMessageTV.text =
                message.body
        }
    }
}