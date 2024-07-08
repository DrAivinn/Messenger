package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.adapters.ChatRecyclerViewAdapter
import com.example.messenger.models.Message
import com.example.messenger.R
import com.example.messenger.databinding.FragmentChatBinding
import com.example.messenger.utils.AddChildEventListener
import com.example.messenger.utils.OPPONENT
import com.example.messenger.utils.loadImage
import com.example.messenger.utils.opponent
import com.example.messenger.utils.refUserChatRoot
import com.example.messenger.utils.refUsersMessagesRoot
import com.example.messenger.utils.sendMessage
import com.example.messenger.utils.user
import com.google.firebase.database.ChildEventListener

class ChatFragment : BaseFragment<FragmentChatBinding>() {
    private lateinit var listener: ChildEventListener


    override fun getFragmentBinding(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentChatBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        opponent = arguments?.getParcelable(OPPONENT)!!
        initToolbar()
        initRecyclerView()
        with(binding) {
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

    private fun initRecyclerView() {

        val chatAdapter = ChatRecyclerViewAdapter()
        binding.chatRV.adapter = chatAdapter
        refUserChatRoot = refUsersMessagesRoot.child(user.uid).child(opponent.uid)
        listener = AddChildEventListener {
            val message = it.getValue(Message::class.java)
            if (message != null) {
                chatAdapter.updateList(message)
                binding.chatRV.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
        refUserChatRoot.addChildEventListener(listener as AddChildEventListener)

    }

    override fun onStop() {
        super.onStop()
        // Удаление слушателя при остановке фрагмента
        refUserChatRoot.removeEventListener(listener)
    }
}




