package com.example.ivanjuradom08practica.Interfaz

import com.example.ivanjuradom08practica.Entidad.Marcas

import retrofit2.Call
import retrofit2.http.GET

interface JsonApi {

    @GET("courses.json")
    fun getDataFromJson(): Call<List<Marcas>>
}
