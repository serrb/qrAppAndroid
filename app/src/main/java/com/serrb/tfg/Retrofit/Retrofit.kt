package com.serrb.tfg.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

object Retrofit {
    val gson = GsonBuilder().setLenient()
        /*.registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())*/
        .create()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}