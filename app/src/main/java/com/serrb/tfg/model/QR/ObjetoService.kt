package com.serrb.tfg.model.QR

import com.serrb.tfg.model.Respuesta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ObjetoService {

    @POST("/guardarObjeto")
    suspend fun guardarObjeto(@Body objeto: Objeto): Response<Respuesta> // contiene respuesta del servidor*

    @GET("/listarObjetos")
    suspend fun getObjetos(): ArrayList<Objeto>

    @GET("/listarObjetosIdEspacio/{id}")
    suspend fun getObjetosPadre(
        @Path("id") idEspacio: Long?
    ): ArrayList<Objeto>

    @GET("/listarObjetosIdCategoria/{id}")
    suspend fun getObjetosIdCategoria(
        @Path("id") idCategoria: Long?
    ): ArrayList<Objeto>

    @GET("/listarCategoriasObj")
    suspend fun getCategoriasObj(): ArrayList<CategoriaObjeto>

    @GET("/objetos/{id}")
    suspend fun searchObjetoById(
        @Path("id") idObjeto: Long?
    ): Objeto

    @GET("/categorias/{id}")
    suspend fun searchCategoriaById(
        @Path("id") idCategoria: Long?
    ): CategoriaObjeto

    @PUT("/actualizarObjeto")
    suspend fun actualizarObjeto(@Body request:Objeto): Response<Respuesta>//poner respuesta

    @DELETE("/eliminarObjeto/{id}")
    suspend fun  deleteObjeto(
        @Path("id") id: Long?
    )
}