package com.serrb.tfg.views

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R

@Composable
fun MenuInventario(navController: NavHostController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black),
    ) {
        Box( // Imagen superior
            modifier = Modifier
                .fillMaxWidth().weight(1f)
                .background(color = Color(0xFF130521))
        ) {
            Row(modifier= Modifier.fillMaxWidth()){
                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "hola",
                    Modifier.size(90.dp)
                        .padding(top=30.dp,start=20.dp,end=10.dp)
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
                    Modifier.fillMaxWidth().size(75.dp).padding(top=50.dp,start=115.dp)

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
                    .background(color = Color.White ,shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .padding(top=14.dp,end=4.dp,start=4.dp,bottom=8.dp)


            ) {
                BotonInventario1(navController, "EspacioPadre", "Mis Espacios",0xFFFE6969)
                BotonObjetos(navController, "ListaObjetos", "Mis Objetos",0xFFFE6969)
            }
        }
    }
}
@Composable
fun BotonInventario1(navController: NavHostController, ruta:String, texto:String, color: Long){
    Button(
        onClick = {navController.navigate(ruta)},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color.Black),
        shape = RoundedCornerShape(12.dp),
        modifier= Modifier
            .fillMaxWidth().height(300.dp).padding(22.dp,26.dp).shadow( // Agregar sombra
                elevation = 20.dp, // Altura de la sombra
                shape = RoundedCornerShape(12.dp), // Forma del botón
                clip = true)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.logoblanco),
                contentDescription = "Icono",
                modifier = Modifier.size(140.dp).padding(start=2.dp,bottom=12.dp) // Ajusta el tamaño del icono según lo necesites
            )
            Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el icono y el texto
            Text(
                text = texto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier= Modifier.width(180.dp).padding(start=5.dp)
            )
        }
    }
}
@Composable
fun BotonObjetos(navController: NavHostController, ruta:String, texto:String, color: Long){
    Button(
        onClick = {navController.navigate(ruta)},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(color),
            contentColor = Color.Black),
        shape = RoundedCornerShape(12.dp),
        modifier= Modifier
            .fillMaxWidth().height(300.dp).padding(22.dp,26.dp).shadow( // Agregar sombra
                elevation = 20.dp, // Altura de la sombra
                shape = RoundedCornerShape(12.dp), // Forma del botón
                clip = true)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = texto,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier= Modifier.width(150.dp).padding(start=25.dp)
            )
            Spacer(modifier = Modifier.width(8.dp)) // Agrega un espacio entre el icono y el texto
            Icon(
                painter = painterResource(id = R.drawable.objetos),
                contentDescription = "Icono",
                modifier = Modifier.size(170.dp).padding(top=20.dp,bottom=12.dp) // Ajusta el tamaño del icono según lo necesites
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MenuInicioPreview16() {
    val navController = rememberNavController()
    MenuInventario(navController = navController)
}