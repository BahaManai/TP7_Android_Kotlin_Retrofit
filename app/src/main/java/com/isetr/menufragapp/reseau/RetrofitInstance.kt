package com.isetr.menufragapp.reseau

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    const val API_PORT = 8081
    const val BASE_URL = "http://10.0.2.2:$API_PORT/api/"
    //private const val BASE_URL = "https://gestion-etudiants-atwo.onrender.com/"
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
// Fournit l'implémentation de l'interface EtudiantApi
        val api: EtudiantApi by lazy {
            retrofit.create(EtudiantApi::class.java)
        }
    }