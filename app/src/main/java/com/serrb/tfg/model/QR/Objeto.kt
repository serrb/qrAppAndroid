package com.serrb.tfg.model.QR

import com.google.gson.annotations.SerializedName

data class Objeto (
    @SerializedName("id")
    var id : Long?,
    var nombre: String,
    var foto : String?,
    var color: String,
    var idCategoria: Long,
    var marca :String,
    var cantidad: Int,
    var precio : Double,
    var descripcion : String,
    var idEspacio : Long
){
    constructor(
        nombre: String, foto: String?, color: String, idCategoria: Long,
        marca: String, cantidad: Int, precio: Double, descripcion: String, idEspacio:Long)
            : this(null, nombre, foto, color, idCategoria, marca, cantidad, precio, descripcion, idEspacio)
}

