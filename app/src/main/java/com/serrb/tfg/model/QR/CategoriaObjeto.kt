package com.serrb.tfg.model.QR

import com.google.gson.annotations.SerializedName

class CategoriaObjeto(
    @SerializedName("id")
    var id : Long?,
    var nombre: String
) {
    constructor(nombre: String)
            : this(null, nombre)
}