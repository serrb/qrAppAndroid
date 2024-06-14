package com.serrb.tfg.model.QR

import com.google.gson.annotations.SerializedName

data class QR (
    @SerializedName("id")
    var id: Long?,
    var nombreQR: String, // definimos variable
    var propietario:String,
    var descripcion: String,
    var emailCliente:String,
    var telefono:String,
    var direccion:String,
    var recompensa:Double,
    var idObjeto : Long?,
    var qrImagePath:String?,

    ){
    constructor(nombreQR: String, propietario:String, descripcion: String, emailCliente: String, telefono: String, direccion: String, recompensa: Double, idObjeto:Long?, qrImagePath: String?)
            : this(null, nombreQR,propietario, descripcion, emailCliente, telefono, direccion, recompensa, idObjeto,qrImagePath)
}

