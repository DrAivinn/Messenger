package com.example.messenger.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.messenger.User
import com.example.messenger.UsersListRecyclerViewAdapter
import com.example.messenger.databinding.FragmentUsersListViewPagerBinding
import com.example.messenger.utils.currentUid
import com.example.messenger.utils.refUsersDataBaseRoot
import java.util.Locale

class UsersListViewPagerFragment() : BaseFragment<FragmentUsersListViewPagerBinding>() {
    private val usersListAdapter = UsersListRecyclerViewAdapter()
    private val usersList = mutableListOf<User>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUsersListViewPagerBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            usersListRV.adapter = usersListAdapter
            refUsersDataBaseRoot.get().addOnSuccessListener {
                usersList.clear()
                for (item in it.children) {
                    val user = item.getValue(User::class.java)!!
                    if (user.uid != currentUid) {
                        usersList.add(user)
                    }
                }
                usersListAdapter.upDateUsersList(usersList)
            }
            searchUser()
        }
    }

    private fun FragmentUsersListViewPagerBinding.searchUser() {
        val search = searchTIL.editText as EditText
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
}