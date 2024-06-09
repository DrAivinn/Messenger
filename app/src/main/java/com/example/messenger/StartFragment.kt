package com.example.messenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentStartBinding
import com.google.android.material.tabs.TabLayoutMediator

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private val fragmentList = listOf(
        ChatsViewPagerFragment(),
        UsersViewPagerFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPagerAdapter = ViewPagerAdapter(this, fragmentList)
        with(binding) {
            toolbar.inflateMenu(R.menu.toolbar_menu)
            viewPagerVP.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayoutTL, viewPagerVP) { tab, position ->
                val listTabNames = listOf("ЧАТЫ", "ПОЛЬЗОВАТЕЛИ")
                tab.text = listTabNames[position]
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}