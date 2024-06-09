package com.example.messenger

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentUsersViewPagerBinding

class UsersViewPagerFragment() : Fragment() {
    private var _binding: FragmentUsersViewPagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchUser: EditText = binding.searchTIL.editText as EditText
        searchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val searchText = s.toString().toLowerCase(Locale.getDefault())
//                val filteredList = originalList.filter {
//                    it.name.toLowerCase(Locale.getDefault()).contains(searchText)
//                }
//                adapter.updateList(filteredList)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}