package com.serrb.tfg.model.QR

import com.serrb.tfg.model.Respuesta
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EspacioService {

    @POST("/guardarEspacio")
    suspend fun generateEspacio(@Body request: Espacio): Response<Respuesta> // contiene respuesta del servidor*

    @GET("/listarEspacios")
    suspend fun getEspacios(): ArrayList<Espacio>

    @GET("/listarEspaciosPadre/{id}")
    suspend fun getEspaciosPadre(
        @Path("id") idEspacioPadre: Long?
    ): ArrayList<Espacio>

    @GET("/listarCategoriasEsp")
    suspend fun getCategoriasEsp(): ArrayList<CategoriaEspacio>

    @GET("/espacios/{id}")
    suspend fun searchEspacioById(
        @Path("id") idEspacio: Long?
    ):Espacio

    @PUT("/actualizarEspacio")
    suspend fun actualizarEspacio(@Body request:Espacio): Response<Respuesta>//poner respuesta

    @DELETE("/eliminarEspacio/{id}")
    suspend fun  deleteEspacio(
        @Path("id") id: Long?
    )
}