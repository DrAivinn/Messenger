package com.example.messenger.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.messenger.R
import com.example.messenger.ViewPagerAdapter
import com.example.messenger.databinding.FragmentStartBinding
import com.example.messenger.utils.auth
import com.example.messenger.utils.initUserCoroutine
import com.example.messenger.utils.user
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StartFragment : BaseFragment<FragmentStartBinding>() {

    private val fragmentList = listOf(
        UsersChatsViewPagerFragment(),
        UsersListViewPagerFragment()
    )

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentStartBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.Main).launch {
            initUserCoroutine()
            initUi()
        }
    }

    private fun initUi() {
        val viewPagerAdapter = ViewPagerAdapter(this, fragmentList)
        with(binding) {
            toolbar.inflateMenu(R.menu.toolbar_menu)
            toolbar.title = user.nickName
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_exit -> {
                        auth.signOut()
                        findNavController().navigate(R.id.action_startFragment_to_authFragment)
                    }

                    R.id.menu_about -> {}
                    R.id.menu_open_profile -> {
                        findNavController().navigate(R.id.action_startFragment_to_editProfileFragment)
                    }
                }
                true
            }
            viewPagerVP.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayoutTL, viewPagerVP) { tab, position ->
                val listTabNames = listOf("ЧАТЫ", "ПОЛЬЗОВАТЕЛИ")
                tab.text = listTabNames[position]
            }.attach()
        }
    }
}