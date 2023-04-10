package hn.single.hiltapp.data.api

import hn.single.hiltapp.data.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
}