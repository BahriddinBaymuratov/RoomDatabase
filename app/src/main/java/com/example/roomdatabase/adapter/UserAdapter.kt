package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.database.UserEntity
import com.example.roomdatabase.databinding.UserLayoutBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    lateinit var onClick: (UserEntity) -> Unit
    private var userList = mutableListOf<UserEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(private val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            with(binding) {
                name.text = user.name
                index.text = adapterPosition.plus(1).toString()
            }
            itemView.setOnClickListener {
                onClick(user)
            }
        }
    }

    fun setList(userList: MutableList<UserEntity>) {
        this.userList = userList
        notifyDataSetChanged()
    }
}