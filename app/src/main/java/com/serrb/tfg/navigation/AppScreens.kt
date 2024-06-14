package com.serrb.tfg.navigation

sealed class AppScreens (var route: String){
    object NuevoQr: AppScreens("NuevoQr");
    object NuevoQrObjeto: AppScreens("NuevoQrObjeto");
    object QrGenerado: AppScreens("QrGenerado");
    object QrDescargar: AppScreens("QrDescargar");
    object Menu: AppScreens("Menu");
    object MenuQr: AppScreens("MenuQr");
    object MenuInventario: AppScreens("MenuInventario");
    object ListaQr: AppScreens("ListaQr");
    object ActualizarQr: AppScreens("ActualizarQr")
    object EspacioPadre: AppScreens("EspacioPadre")
    object Subespacio: AppScreens("Subespacio")
    object NuevoEspacio: AppScreens("NuevoEspacio")
    object NuevoEspacioPadre: AppScreens("NuevoEspacioPadre")
    object ActualizarEspacio: AppScreens("ActualizarEspacio")
    object NuevoObjeto: AppScreens("NuevoObjeto")
    object ListaObjetos: AppScreens("ListaObjetos")
    object ActualizarObjeto: AppScreens("ActualizarObjeto")

}