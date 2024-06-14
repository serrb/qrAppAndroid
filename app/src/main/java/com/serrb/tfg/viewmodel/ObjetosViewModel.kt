package com.serrb.tfg.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serrb.tfg.Retrofit.Retrofit
import com.serrb.tfg.model.QR.CategoriaObjeto
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.model.QR.ObjetoService
import com.serrb.tfg.model.Respuesta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ObjetosViewModel: ViewModel() {

    //VARIABLE PARA INSTANCIAR EL SERVICIO
    val service: ObjetoService by lazy {
        Retrofit.retrofit.create(ObjetoService::class.java)
    }

    private val _listaObjetos = MutableLiveData<List<Objeto>>()
    val listaObjetos: LiveData<List<Objeto>> = _listaObjetos

    private val _listaObjetos2 = MutableLiveData<Objeto>()
    val listaObjetos2: LiveData<Objeto> = _listaObjetos2

    private val _listaObjetosIdCat = MutableLiveData<List<Objeto>>()
    val listaObjetosIdCat: LiveData<List<Objeto>> get() = _listaObjetosIdCat

    private val _listaCategorias = MutableLiveData<List<CategoriaObjeto>>()
    val listaCategorias: LiveData<List<CategoriaObjeto>> = _listaCategorias

    private val _respuesta = MutableLiveData<Respuesta>()
    val respuesta: LiveData<Respuesta> = _respuesta

    private val _objetoById = MutableLiveData<Objeto>()
    val objetoById: LiveData<Objeto> = _objetoById

    private val _objetoById2 = MutableLiveData<CategoriaObjeto>()
    val objetoById2: LiveData<CategoriaObjeto> = _objetoById2

    //buscar objetos
    //Search
    private val _objetoBuscar = MutableLiveData<List<Objeto>>()
    val objetoBuscar: LiveData<List<Objeto>> = _objetoBuscar



    var mensaje:String = ""

    //VALIDACION

    //VALIDACION
    var nombreObjetoMsg: MutableState<String> = mutableStateOf("")
    var colorErrMsg: MutableState<String> = mutableStateOf("")
    var marcaErrMsg: MutableState<String> = mutableStateOf("")
    var cantidadErrMsg: MutableState<String> = mutableStateOf("")
    var precioErrMsg: MutableState<String> = mutableStateOf("")
    var descripcionErrMsg: MutableState<String> = mutableStateOf("")

    fun getObjetos() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getObjetos()
            withContext(Dispatchers.Main){
                _listaObjetos.value = response
            }
        }
    }

    fun getCategoriaObjetos() {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getCategoriasObj()
            withContext(Dispatchers.Main){
                _listaCategorias.value = response
            }
        }
    }

    fun getObjetosByIdPadre(id: Long?) {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getObjetosPadre(id)
            withContext(Dispatchers.Main){
                _listaObjetos.value = response
            }
        }
    }

    fun getObjetoByIdObjeto(id: Long?) {
            viewModelScope.launch (Dispatchers.IO) {
                val response = service.searchObjetoById(id)
                println("holahola" + response)
                withContext(Dispatchers.Main){
                    _objetoById.value = response
                }
            }
    }

    fun getCategoriaById(id: Long?) {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.searchCategoriaById(id)
            println("holahola" + response)
            withContext(Dispatchers.Main){
                _objetoById2.value = response
            }
        }
    }

    fun guardarObjeto(objeto: Objeto) {
        println("guardarObjeto2222222222222222")
        println(objeto)
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.guardarObjeto(objeto)
            withContext(Dispatchers.Main) {
                println(response)
                _respuesta.value = response.body()

            }
        }
    }

    fun actualizarObjeto(objeto : Objeto) {
        println("update Objeto")
        println(objeto)
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.actualizarObjeto(objeto)
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

    fun deleteObjeto(id: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.deleteObjeto(id)
            getObjetos()
        }
    }

    //buscar objetos por categoria
    fun getObjetosByIdCategoria(id: Long?) {
        viewModelScope.launch (Dispatchers.IO) {
            val response = service.getObjetosIdCategoria(id)
            withContext(Dispatchers.Main){
                _listaObjetosIdCat.value = response
            }
        }
    }

}