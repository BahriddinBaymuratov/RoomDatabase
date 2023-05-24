package com.example.roomdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserTable ORDER BY id DESC")// table ichidan id sini osish tartibida olish
    fun getAllUsers(): List<UserEntity>

    @Update
    fun updateUser(userEntity: UserEntity)

    @Query("UPDATE UserTable SET name = :name WHERE id = :id")
    fun updateUserName(id: Int, name: String) // faqat ismini update qilish

    @Delete
    fun deleteUser(userEntity: UserEntity)

    @Query("DELETE FROM UserTable WHERE id = :id")
    fun deleteById(id: Int)// faqat id bilan ochiradi

    @Query("DELETE FROM  UserTable")
    fun clearUserTable() // UserTable ni ochirish

    @Query("SELECT * FROM UserTable WHERE id = :id")
    fun getUserById(id: Int): UserEntity // 1 dona user olish

}