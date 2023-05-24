package com.example.roomdatabase.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.MainActivity
import com.example.roomdatabase.R
import com.example.roomdatabase.database.UserDatabase
import com.example.roomdatabase.database.UserEntity
import com.example.roomdatabase.databinding.FragmentAddUpdateBinding
import com.example.roomdatabase.util.snackBar


class AddUpdateFragment : Fragment(R.layout.fragment_add_update) {
    private var _binding: FragmentAddUpdateBinding? = null
    private val binding get() = _binding!!
    private var user: UserEntity? = null
    private val userDatabase by lazy { UserDatabase(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getParcelable("user")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddUpdateBinding.bind(view)
        (activity as MainActivity).supportActionBar?.hide()
        initViews()
    }

    private fun initViews() {
        println("@@@$user")
        if (user != null) {
            binding.apply {
                toolBar.title = "Update User"
                btn.text = "Update"
                name.setText(user?.name)
                lastName.setText(user?.lastName)
                age.setText(user?.age.toString())

            }
        } else {
            binding.toolBar.title = "Add User"
            binding.btn.text = "Create"
        }
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.btn.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val lastName = binding.lastName.text.toString().trim()
            val age = binding.age.text.toString().trim().toInt()
            if (user == null) {
                userDatabase.dao.saveUser(
                    UserEntity(
                        name = name,
                        lastName = lastName,
                        age = age
                    )
                )
                snackBar("User saved")
                findNavController().popBackStack()
            } else {
                userDatabase.dao.updateUser(
                    UserEntity(user?.id!!, name, lastName, age)
                )
                snackBar("User Updated")
                findNavController().navigate(R.id.action_addUpdateFragment_to_userListFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}