package com.serrb.tfg.views

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.EspaciosViewModel
import com.serrb.tfg.viewmodel.ObjetosViewModel
import com.serrb.tfg.viewmodel.QRViewModel

//aqui hacemos la pantalla principal de 3 botones

@Composable
fun MenuInicio(navController: NavHostController){

    val objetosViewModel: ObjetosViewModel = viewModel()
    val listaObjetos by objetosViewModel.listaObjetos.observeAsState(emptyList())

    val espaciosViewModel :EspaciosViewModel = viewModel()
    val listaEspacios by espaciosViewModel.listaEspacios.observeAsState(emptyList())

    val QRViewModel : QRViewModel = viewModel()
    val listaQRs by QRViewModel.listaQRs.observeAsState(emptyList())

    //Iniciamos metodos
    LaunchedEffect(Unit) {
        try {
            objetosViewModel.getObjetos()
            espaciosViewModel.getEspacios()
            QRViewModel.getQRs()
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar los Objetos: ", e)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black),
    ) {
        Box( // Imagen superior
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(color = Color(0xFF130521))
        ) {
            Row(modifier=Modifier.fillMaxWidth()){
            Image(
                painter = painterResource(id = R.drawable.perfil),
                contentDescription = "hola",
                Modifier
                    .size(90.dp)
                    .padding(top = 30.dp, start = 20.dp, end = 10.dp)
                    .border(width = 1.dp, color = Color(0x6FFFFFFF), shape = CircleShape)


            )

            Column(
                modifier = Modifier.padding(top=40.dp,start=2.dp)
            ){
                Text(
                    text = "Welcome Back!",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.SansSerif)
                Text(
                    text = "Sergio Ramos",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                )
            }
                Image(
                    painter = painterResource(id = R.drawable.baseline_notifications_24),
                    contentDescription = "hola",
                    Modifier
                        .fillMaxWidth()
                        .size(75.dp)
                        .padding(top = 50.dp, start = 115.dp)

                )

            }
            // Agregar la imagen de fondo
        }
        Box( // Botones
            modifier = Modifier
                .background(color = Color(0xFF130521))
                .weight(5f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(top = 14.dp, end = 4.dp, start = 4.dp, bottom = 25.dp)


            ) {
                val numEspacios = listaEspacios.size
                val numObjetos = listaObjetos.size
                val numQrs = listaQRs.size
                Button( // Vamos a listar los objetos, los espacios, y los QRs
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF130521),
                        contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier= Modifier
                        .fillMaxWidth(1f)
                        .height(100.dp)
                        .padding(12.dp, 6.dp)
                ) {
                    Row(
                        modifier=Modifier.fillMaxWidth(1f).height(120.dp).padding(1.dp),
                    ) {

                        Column(modifier=Modifier.fillMaxWidth().height(100.dp).weight(0.9f).drawBehind {
                            drawLine(
                                color = Color(0x92D9D9D9),
                                start = Offset(size.width, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }.padding(end=10.dp)
                            ,  verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            Text("ESPACIOS",color =Color.White,
                                fontSize = 12.sp,
                                letterSpacing = 0.05.em,
                                fontWeight = FontWeight.ExtraBold,
                                modifier=Modifier.height(20.dp).padding(top=5.dp,bottom=0.dp))
                            Text(text = "$numEspacios",color=Color.White, fontSize = 25.sp,
                                fontFamily = raleway,
                                modifier=Modifier.height(100.dp))
                        }
                        Column(modifier=Modifier.height(100.dp).weight(1.1f).drawBehind {
                            drawLine(
                                color = Color(0x92D9D9D9),
                                start = Offset(size.width, 0f),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        },  verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            Text("OBJETOS",color =Color.White,
                                fontSize = 12.sp,
                                letterSpacing = 0.05.em,
                                fontWeight = FontWeight.ExtraBold,
                                modifier=Modifier.height(20.dp).padding(top=5.dp,bottom=0.dp))
                            Text(text = "$numObjetos",color=Color.White, fontSize = 25.sp,
                                fontFamily = raleway,
                                modifier=Modifier.height(100.dp))
                        }
                        Column(modifier=Modifier.height(100.dp).weight(0.9f).padding(start=10.dp) ,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ){
                            Text("QR'S",color =Color.White,
                                fontSize = 12.sp,
                                letterSpacing = 0.05.em,
                                fontWeight = FontWeight.ExtraBold,
                                modifier=Modifier.height(20.dp).padding(top=5.dp,bottom=0.dp))
                            Text(text = "$numQrs",color=Color.White, fontSize = 25.sp,
                                fontFamily = raleway,
                                modifier=Modifier.height(100.dp))
                        }


                    }
                }

                BotonInventario(navController, "MenuInventario", "MI INVENTARIO",0xFFFE6969)
                BotonQr(navController, "MenuQr", "MIS QR'S",0xFFD9D9D9)
            }
        }
    }
}

@Composable
fun BotonInventario(navController:NavHostController, ruta:String, texto:String, color: Long){
    Button(
        onClick = {navController.navigate(ruta)},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color.Black),
        shape = RoundedCornerShape(12.dp),
        modifier= Modifier
            .fillMaxWidth()
            .padding(12.dp, 6.dp),
        contentPadding = PaddingValues(0.dp,60.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.logoblanco),
                contentDescription = "Icono",
                modifier = Modifier
                    .size(140.dp)
                    .padding(start = 2.dp, bottom = 12.dp) // Ajusta el tamaño del icono según lo necesites
            )
            Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el icono y el texto
            Text(
                text = texto,
                fontFamily=outfit,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier= Modifier
                    .width(180.dp)
                    .padding(start = 5.dp)
            )
        }
    }}
@Composable
fun BotonQr(navController:NavHostController, ruta:String, texto:String, color: Long){
    Button(
        onClick = {navController.navigate(ruta)},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color.Black),
        shape = RoundedCornerShape(12.dp),
        modifier= Modifier
            .fillMaxWidth()
            .padding(12.dp, 6.dp),
        contentPadding = PaddingValues(0.dp,60.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = texto,
                fontSize = 20.sp,
                fontFamily=outfit,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier= Modifier
                    .width(180.dp)
                    .padding(start = 5.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el icono y el texto
            Icon(
                painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                contentDescription = "Icono",
                modifier = Modifier
                    .size(130.dp)
                    .padding(end = 30.dp, top = 5.dp) // Ajusta el tamaño del icono según lo necesites
            )
        }
    }}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview() {
    val navController = rememberNavController()
        MenuInicio(navController = navController)
}