package com.example.roomdatabase.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.UserAdapter
import com.example.roomdatabase.database.UserDatabase
import com.example.roomdatabase.databinding.FragmentUserListBinding

class UserListFragment : Fragment(R.layout.fragment_user_list) {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private val userAdapter by lazy { UserAdapter() }
    private val userDatabase by lazy { UserDatabase(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserListBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.rv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUpdateFragment)
        }
        userAdapter.setList(userDatabase.dao.getAllUsers().toMutableList())
        userAdapter.onClick = {
            val bundle = bundleOf("user" to it)
            findNavController().navigate(R.id.action_userListFragment_to_detailFragment, bundle)
        }

    }

}