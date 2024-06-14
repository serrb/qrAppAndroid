package com.serrb.tfg.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.serrb.tfg.model.QR.QRService
import coil.compose.rememberImagePainter


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.serrb.tfg.Retrofit.Retrofit
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.model.Respuesta
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import java.io.IOException
import java.util.Locale


class QRViewModel:ViewModel(){

    //VARIABLE PARA INSTANCIAR EL SERVICIO
    val service: QRService by lazy {
        Retrofit.retrofit.create(QRService::class.java)
    }
    var mensaje:String = ""

    private val _respuesta = MutableLiveData<Respuesta>()
    val respuesta: LiveData<Respuesta> = _respuesta

    private val _respuestaId = MutableLiveData<Long>()
    val respuestaId:LiveData<Long> = _respuestaId

    private val _respuestaGeneracionQR = MutableLiveData<String?>()
    val respuestaGeneracionQR: LiveData<String?> get() = _respuestaGeneracionQR

    private val _listaQRs = MutableLiveData<List<QR>>()
    val listaQRs: LiveData<List<QR>> = _listaQRs

    private val _qrByid = MutableLiveData<QR>()
    val qrByid: LiveData<QR> = _qrByid

    //VALIDACION
    var nombreErrMsg: MutableState<String> = mutableStateOf("")
    var propietarioErrMsg: MutableState<String> = mutableStateOf("")
    var descripcionErrMsg: MutableState<String> = mutableStateOf("")
    var emailErrMsg: MutableState<String> = mutableStateOf("")
    var telefonoErrMsg: MutableState<String> = mutableStateOf("")
    var direccionErrMsg: MutableState<String> = mutableStateOf("")
    var recompensaErrMsg: MutableState<String> = mutableStateOf("")


    fun guardarQR(qr: QR) {
        println("holi holi holi holi")
        val response = runBlocking {
            service.guardarQR(qr) // Llamada síncrona utilizando runBlocking
        }
        _respuestaId.value = response.body()
    }


    suspend fun generateQR(id: Long?): Bitmap {
            return withContext(Dispatchers.IO) {
                val responseBody: ResponseBody = service.generateQR(id)
                // Leer los bytes de la respuesta
                val bytes: ByteArray = responseBody.bytes()
                // Convertimos los bytes en un objeto Bitmap utilizando BitmapFactory
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    ?: throw IOException("Error al decodificar los bytes de la imagen")
                bitmap
            }
        }

        fun generarQRLocalmente(texto: Long?): Bitmap? {
            val qrCodeWriter = QRCodeWriter()

            val gson = Gson()
            val json = gson.toJson(texto)

            val bitMatrix = qrCodeWriter.encode(json, BarcodeFormat.QR_CODE, 512, 512)

            val width = bitMatrix.width
            val height = bitMatrix.height
            val pixels = IntArray(width * height)

            for (y in 0 until height) {
                val offset = y * width
                for (x in 0 until width) {
                    pixels[offset + x] =
                        if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
                }
            }

            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            bmp.setPixels(pixels, 0, width, 0, 0, width, height)

            return bmp

        }

        fun getQRs() {
            viewModelScope.launch(Dispatchers.IO) {
                val response = service.getQRs()
                withContext(Dispatchers.Main) {
                    _listaQRs.value = response
                }
            }
        }

        fun actualizarQR(qr: QR) {
            println("update QR")
            println(qr)
            viewModelScope.launch(Dispatchers.IO) {
                val response = service.actualizarQR(qr)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        _respuesta.value = response.body()
                        println(_respuesta.value)
                    } else {
                        mensaje = "Error al Crear QR"
                    }
                }
            }
        }

        fun buscarQrById(id: Long?) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = service.searchQrById(id)
                withContext(Dispatchers.Main) {
                    _qrByid.value = response.body()
                }
            }
        }

        fun deleteQr(id: Long?) {
            viewModelScope.launch(Dispatchers.IO) {
                val response = service.deleteQR(id)
                getQRs()
            }
        }


    }