package com.example.androidniubizintegration

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface SecurityService {
    @GET("security")
    fun getToken(@HeaderMap headers: Map<String, String>): Call<String>
}