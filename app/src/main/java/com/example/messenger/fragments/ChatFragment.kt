package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.ChatRecyclerViewAdapter
import com.example.messenger.Message
import com.example.messenger.R
import com.example.messenger.databinding.FragmentChatBinding
import com.example.messenger.utils.OPPONENT
import com.example.messenger.utils.listener
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.opponent
import com.example.messenger.utils.refUserChatRoot
import com.example.messenger.utils.sendMessage
import com.example.messenger.utils.setupMessageListener

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    val messageList = mutableListOf<Message>()
    val chatAdapter = ChatRecyclerViewAdapter()

    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChatBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        opponent = arguments?.getParcelable(OPPONENT)!!
        initToolbar()
        with(binding) {
            binding.chatRV.adapter = chatAdapter
            sendMessageIV.setOnClickListener {
                val text = messageTextET.text.toString()
                messageTextET.text.clear()
                sendMessage(text)
            }
        }
    }

    private fun initToolbar() {
        with(binding) {
            toolbarNickNameTV.text = opponent.nickName
            toolbarAvatarIV.loadImage(opponent.imageURL)
            toolbarChatTB.inflateMenu(R.menu.toolbar_chat_menu)
            toolbarChatTB.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_open_profile) {
                    val bundle = Bundle()
                    bundle.putParcelable(OPPONENT, opponent)
                    findNavController().navigate(
                        R.id.action_chatFragment_to_someUserProfileFragment, bundle
                    )
                }
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        messageList.clear()
        setupMessageListener {
            messageList.add(it)
            chatAdapter.updateList(messageList)
            binding.chatRV.scrollToPosition(messageList.size - 1)
        }
    }

    override fun onStop() {
        super.onStop()
        // Удаление слушателя при остановке фрагмента
        listener?.let {
            refUserChatRoot.removeEventListener(it)
        }
        listener = null
    }

}




