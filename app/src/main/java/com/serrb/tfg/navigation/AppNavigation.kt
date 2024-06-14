package com.serrb.tfg.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.Greeting
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.viewmodel.QRViewModel
import com.serrb.tfg.views.ActualizarEspacio
import com.serrb.tfg.views.ActualizarObjeto
import com.serrb.tfg.views.ActualizarQr
import com.serrb.tfg.views.EspacioPadre
import com.serrb.tfg.views.ListaQrs
import com.serrb.tfg.views.MenuInicio
import com.serrb.tfg.views.MenuInventario
import com.serrb.tfg.views.MenuQr
import com.serrb.tfg.views.NuevoEspacio
import com.serrb.tfg.views.NuevoEspacioPadre
import com.serrb.tfg.views.NuevoObjeto
import com.serrb.tfg.views.NuevoQr
import com.serrb.tfg.views.NuevoQrObjeto
import com.serrb.tfg.views.QrDescargar
import com.serrb.tfg.views.QrGenerado
import com.serrb.tfg.views.Subespacio
import com.serrb.tfg.views.listarObjetos

@Composable
fun AppNavigation(){
    val viewModel: QRViewModel = viewModel()
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = AppScreens.Menu.route)
    {
        composable(AppScreens.NuevoQr.route) { NuevoQr(navigationController) }
        composable(
            route = AppScreens.NuevoQrObjeto.route + "/{nombreQR}/{idObjetoPasar}",
            arguments = listOf(
                navArgument("nombreQR") { type = NavType.StringType },
                navArgument("idObjetoPasar") { type = NavType.LongType },
            )
        ) {
            val nombreQR = it.arguments?.getString("nombreQR")
            val idObjetoPasar = it.arguments?.getLong("idObjetoPasar")
             NuevoQrObjeto(navController = navigationController, nombreQR = nombreQR ,idObjetoPasar = idObjetoPasar)
        }
        composable(AppScreens.Menu.route) { MenuInicio(navigationController) }
        composable(AppScreens.MenuQr.route) { MenuQr(navigationController) }
        composable(AppScreens.QrGenerado.route + "{respuestaId2}" ,
            arguments = listOf(navArgument(name = "respuestaId2")
            {type = NavType.LongType})) {
            QrGenerado(navigationController, it.arguments?.getLong("respuestaId2"),)
        }
        composable(AppScreens.QrDescargar.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            QrDescargar(navigationController, it.arguments?.getLong("id"),)
        }
        composable(AppScreens.MenuInventario.route) { MenuInventario(navigationController) }
        composable(AppScreens.ListaQr.route) { ListaQrs(navigationController) }
        composable(AppScreens.ListaObjetos.route) { listarObjetos(navigationController) }
        composable(AppScreens.EspacioPadre.route) { EspacioPadre(navigationController) }
        composable(AppScreens.NuevoEspacio.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            NuevoEspacio(navigationController, it.arguments?.getLong("id"))
        }
        composable(AppScreens.NuevoEspacioPadre.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            NuevoEspacioPadre(navigationController, it.arguments?.getLong("id"))
        }
        composable(AppScreens.NuevoObjeto.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            NuevoObjeto(navigationController, it.arguments?.getLong("id"))
        }
        composable(AppScreens.ActualizarEspacio.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            ActualizarEspacio(navigationController, it.arguments?.getLong("id"))
        }
        composable(AppScreens.ActualizarQr.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            ActualizarQr(navigationController, it.arguments?.getLong("id"))
        }
        composable(
            route = AppScreens.ActualizarObjeto.route + "/{id}/{idCategoria}/{idEspacioPadre}",
            arguments = listOf(
                navArgument("id") { type = NavType.LongType },
                navArgument("idCategoria") { type = NavType.LongType },
                navArgument("idEspacioPadre") { type = androidx.navigation.NavType.LongType }
            )
        ) {
            val id = it.arguments?.getLong("id")
            val idCategoria = it.arguments?.getLong("idCategoria")
            val idEspacioPadre = it.arguments?.getLong("idEspacioPadre")
            ActualizarObjeto(navController = navigationController, id = id, idCategoria = idCategoria,idEspacioPadre = idEspacioPadre)
        }
        composable(AppScreens.Subespacio.route + "{id}" ,
            arguments = listOf(navArgument(name = "id")
            {type = NavType.LongType})) {
            Subespacio(navigationController, it.arguments?.getLong("id"))
        }
    }
    }
