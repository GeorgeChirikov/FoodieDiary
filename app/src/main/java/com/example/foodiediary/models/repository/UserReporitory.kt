package com.example.foodiediary.models.repository

import androidx.lifecycle.LiveData
import com.example.foodiediary.models.data.dao.UserDao
import com.example.foodiediary.models.data.entity.User
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val allUsers: Flow<List<User>> = userDao.getAllUsers()

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
