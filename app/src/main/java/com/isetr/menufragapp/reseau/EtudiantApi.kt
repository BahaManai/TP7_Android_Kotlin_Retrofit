package com.isetr.menufragapp.reseau

import com.isetr.menufragapp.data.Etudiant
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EtudiantApi {

    @GET("etudiants")
    suspend fun getEtudiants(): Response<List<Etudiant>>

    @POST("addEtudiant")
    suspend fun addEtudiant(@Body etudiant: Etudiant): Response<Etudiant>

    @PUT("update")
    suspend fun updateEtudiant(@Body etudiant: Etudiant): Response<Void>

    @DELETE("delete/{cin}")
    suspend fun deleteEtudiant(@Path("cin") cin: Int): Response<Void>
}
