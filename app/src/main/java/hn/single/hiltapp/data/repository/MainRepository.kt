package hn.single.hiltapp.data.repository

import hn.single.hiltapp.data.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun getUsers() = apiHelper.getUsers()

}