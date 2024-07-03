package com.example.messenger.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.messenger.databinding.FragmentUsersChatsViewPagerBinding

class UsersChatsViewPagerFragment() : BaseFragment<FragmentUsersChatsViewPagerBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentUsersChatsViewPagerBinding.inflate(inflater, container, false)
}
