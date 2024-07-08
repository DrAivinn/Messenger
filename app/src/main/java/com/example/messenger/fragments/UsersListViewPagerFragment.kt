package com.example.messenger.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.User
import com.example.messenger.UsersListRecyclerViewAdapter
import com.example.messenger.databinding.FragmentUsersListViewPagerBinding
import com.example.messenger.utils.AddChildEventListener
import com.example.messenger.utils.OPPONENT
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.refUsersDataBaseRoot
import java.util.Locale

class UsersListViewPagerFragment() : BaseFragment<FragmentUsersListViewPagerBinding>() {
    private lateinit var listener: AddChildEventListener
    val usersListAdapter = UsersListRecyclerViewAdapter { onClick(it) }
    private val usersList = mutableListOf<User>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUsersListViewPagerBinding.inflate(inflater, container, false)

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
        listener = AddChildEventListener {
            val user = it.getValue(User::class.java)
            if (user != null && user.uid != currentUid) {
                val updated = updateUser(user)
                if (!updated) {
                    usersList.add(user)
                    usersListAdapter.upDateUsersList(usersList)
                }
            }
        }
        refUsersDataBaseRoot.addChildEventListener(listener)
    }

    private fun searchUser() {
        val search = binding.searchTIL.editText as EditText
        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().lowercase(Locale.getDefault())
                val filteredList = usersList.filter {
                    it.nickName.lowercase(Locale.getDefault()).contains(searchText)
                }
                usersListAdapter.upDateUsersList(filteredList as MutableList)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun onClick(user: User) {
        val bundle = Bundle()
        bundle.putParcelable(OPPONENT, user)
        findNavController().navigate(R.id.action_startFragment_to_chatFragment, bundle)
    }

    override fun onPause() {
        super.onPause()
        refUsersDataBaseRoot.removeEventListener(listener)
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        searchUser()
    }

    override fun onStop() {
        super.onStop()
        refUsersDataBaseRoot.removeEventListener(listener)
    }
}