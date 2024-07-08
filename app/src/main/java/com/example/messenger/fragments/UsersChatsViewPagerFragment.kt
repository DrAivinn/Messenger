package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.models.User
import com.example.messenger.adapters.UsersChatsRecyclerViewAdapter
import com.example.messenger.databinding.FragmentUsersChatsViewPagerBinding
import com.example.messenger.utils.AddChildEventListener
import com.example.messenger.utils.OPPONENT
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.refUsersDataBaseRoot
import com.example.messenger.utils.refUsersMessagesRoot

class UsersChatsViewPagerFragment() : BaseFragment<FragmentUsersChatsViewPagerBinding>() {
    private lateinit var messageListener: AddChildEventListener
    private lateinit var usersListener: AddChildEventListener
    val usersListAdapter = UsersChatsRecyclerViewAdapter { onClick(it) }
    private val usersList = mutableListOf<User>()
    val keys = mutableListOf<String>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUsersChatsViewPagerBinding.inflate(inflater, container, false)

    override fun onResume() {
        super.onResume()
        getUsersIDs()
        initRecyclerView()

    }

    override fun onPause() {
        super.onPause()
        refUsersDataBaseRoot.removeEventListener(usersListener)
        refUsersMessagesRoot.removeEventListener(messageListener)
    }

    private fun updateUser(newUser: User): Boolean {
        val index = usersList.indexOfFirst { it.uid == newUser.uid }
        return if (index != -1) {
            usersList[index] = newUser
            usersListAdapter.notifyItemChanged(index)
            true
        } else {
            false
        }
    }

    private fun initRecyclerView() {
        binding.usersListRV.adapter = usersListAdapter
        usersListener = AddChildEventListener {
            val user = it.getValue(User::class.java)
            if (user != null && keys.contains(user.uid) && user.uid != currentUid) {
                val updated = updateUser(user)
                if (!updated) {
                    usersList.add(user)
                    usersListAdapter.upDateUsersList(usersList)
                }
            }

        }
        refUsersDataBaseRoot.addChildEventListener(usersListener)
    }

    private fun onClick(user: User) {
        val bundle = Bundle()
        bundle.putParcelable(OPPONENT, user)
        findNavController().navigate(R.id.action_startFragment_to_chatFragment, bundle)
    }

    fun getUsersIDs() {
        messageListener = AddChildEventListener { snapShot ->
            snapShot.children.forEach {
                keys.add(it.key.toString())
                initRecyclerView()
            }
        }

        refUsersMessagesRoot.addChildEventListener(messageListener)
    }
}
