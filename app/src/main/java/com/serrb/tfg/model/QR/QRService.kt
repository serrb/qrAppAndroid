package com.serrb.tfg.model.QR

import android.media.Image
import androidx.compose.ui.graphics.ImageBitmap
import com.serrb.tfg.model.Respuesta
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface QRService {

    @POST("/guardarQR")
    suspend fun guardarQR(@Body request: QR): Response<Long> // contiene respuesta del servidor*

    @POST("/generarQR/{id}")
    suspend fun generateQR( @Path("id") id: Long?): ResponseBody // contiene respuesta del servidor*

    @GET("/listarQRs")
    suspend fun getQRs(): ArrayList<QR>

    @GET("/QRs/{id}")
    suspend fun searchQrById(
        @Path("id") idQr: Long?
    ): Response<QR>

    @PUT("/actualizarQR")
    suspend fun actualizarQR(@Body request:QR):Response<Respuesta>//poner respuesta

    @DELETE("/eliminarQR/{id}")
    suspend fun  deleteQR(
        @Path("id") id: Long?
    )
}