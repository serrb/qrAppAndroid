package com.serrb.tfg.model.QR

import com.google.gson.annotations.SerializedName

data class Espacio (
    @SerializedName("id")
    var id : Long?,
    var nombre: String,
    var idEspacioPadre: Long?,
    var idCategoria:Long
){
    constructor(nombre: String, idEspacioPadre:Long,idCategoria: Long)
            : this(null, nombre, idEspacioPadre,idCategoria)
}