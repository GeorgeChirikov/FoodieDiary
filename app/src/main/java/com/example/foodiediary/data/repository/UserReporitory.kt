package com.example.foodiediary.data.repository

import androidx.lifecycle.LiveData
import com.example.foodiediary.data.dao.UserDao
import com.example.foodiediary.models.entity.User

class UserRepository(private val userDao: UserDao) {

    val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    suspend fun getUserById(userId: Long): User? {
        return userDao.getUserById(userId)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}
