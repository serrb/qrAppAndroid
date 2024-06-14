package com.serrb.tfg.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serrb.tfg.Retrofit.Retrofit
import com.serrb.tfg.model.QR.CategoriaEspacio
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.model.QR.EspacioService
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.model.Respuesta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EspaciosViewModel: ViewModel() {

    //VARIABLE PARA INSTANCIAR EL SERVICIO
    val service: EspacioService by lazy {
        Retrofit.retrofit.create(EspacioService::class.java)
    }

    private val _listaEspacios = MutableLiveData<List<Espacio>>()
    val listaEspacios: LiveData<List<Espacio>> = _listaEspacios

    private val _listaEspacios2 = MutableLiveData<Espacio>()
    val listaEspacios2: LiveData<Espacio> = _listaEspacios2

    private val _listaCategorias = MutableLiveData<List<CategoriaEspacio>>()
    val listaCategorias: LiveData<List<CategoriaEspacio>> = _listaCategorias

    private val _respuesta = MutableLiveData<Respuesta>()
    val respuesta: LiveData<Respuesta> = _respuesta

    private val _espacioById = MutableLiveData<Espacio>()
    val espacioById: LiveData<Espacio> = _espacioById

    val listaEspaciosPadre = MutableLiveData<List<Espacio>>()

    //VALIDACION
    var nombreErrMsg: MutableState<String> = mutableStateOf("")

    var mensaje:String = ""

    fun getEspacios() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getEspacios()
            withContext(Dispatchers.Main){
                _listaEspacios.value = response
            }
        }
    }
    fun getCategoriaEspacios() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getCategoriasEsp()
            withContext(Dispatchers.Main){
                _listaCategorias.value = response
            }
        }
    }

    fun getEspaciosPadre(id: Long?) {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getEspaciosPadre(id)
            withContext(Dispatchers.Main){
                _listaEspacios.value = response
            }
        }
    }
    fun getEspacioPorId(id: Long?) {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.searchEspacioById(id)
            println("holahola" + response)
            withContext(Dispatchers.Main){
                _espacioById.value = response
            }
        }
    }

    fun getNombreEspacioPadre(idEspacioPadre: Long?): String {
        // Lógica para obtener el nombre del espacio padre por su ID
        // Esto podría ser una búsqueda en la lista de espacios o una llamada a otro método del ViewModel
        val espacioPadre = listaEspacios.value?.find { it.id == idEspacioPadre }
        return espacioPadre?.nombre ?: "Sin espacio padre"
    }

    fun guardarEspacio(espacio:Espacio) {
        println("guardarEspacio2222222222222222")
        println(espacio)
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.generateEspacio(espacio)
            withContext(Dispatchers.Main) {
                println(response)
                _respuesta.value = response.body()

            }
        }
    }

    // Obtener el nombre completo de la jerarquía de un espacio
    fun getNombreCompletoEspacio(idEspacio: Long?): String {
        if (idEspacio == null) return "Espacio no encontrado"
        val espacio = listaEspacios.value?.find { it.id == idEspacio }
        return if (espacio?.idEspacioPadre != null) {
            "${espacio.nombre} > ${getNombreCompletoEspacio(espacio.idEspacioPadre)}"
        } else {
            espacio?.nombre ?: ""
        }
    }

    fun actualizarEspacio(espacio : Espacio) {
        println("update Espacio")
        println(espacio)
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.actualizarEspacio(espacio)
            withContext(Dispatchers.Main){
                if (response.isSuccessful) {
                    _respuesta.value = response.body()
                    println(_respuesta.value)
                } else {
                    mensaje = "Error al Actualizar Espacio"
                }
            }
        }
    }

    fun deleteEspacio(id: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.deleteEspacio(id)
            getEspacios()
        }
    }

    // Método para verificar si un espacio tiene subespacios
    private fun tieneSubespacios(idEspacio: Long): Boolean {
        return _listaEspacios.value?.any { it.idEspacioPadre == idEspacio } ?: false
    }

    // Método para eliminar un espacio si no tiene subespacios
    fun eliminarEspacioSiNoTieneSubespacios(id: Long) {
        if (tieneSubespacios(id)) {
            // Mostrar un mensaje de error o notificar al usuario
            Log.d("EspaciosViewModel", "El espacio tiene subespacios y no puede ser eliminado.")
        } else {
           deleteEspacio(id)
            Log.d("EspaciosViewModel", "El espacio se ha eliminado.")
        }
    }
}