package com.example.roomdatabase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.database.UserDatabase
import com.example.roomdatabase.database.UserEntity
import com.example.roomdatabase.databinding.FragmentDetailBinding
import com.example.roomdatabase.util.snackBar


class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: UserEntity
    private val database by lazy { UserDatabase(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            toolBar.title = user.name
            textData.text = "${user.name}\n${user.lastName}\n${user.age}"
        }
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnDelete.setOnClickListener {
            showDialog()
        }
        binding.btnUpdate.setOnClickListener {
            val user = bundleOf("user" to user)
            findNavController().navigate(R.id.action_detailFragment_to_addUpdateFragment, user)
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete")
            setMessage("Do you want delete this user?")
            setPositiveButton("Yes") { di, _ ->
                database.dao.deleteUser(user)
                snackBar("Deleted")
                findNavController().popBackStack()
            }
            setNegativeButton("No", null)
        }.create().show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}