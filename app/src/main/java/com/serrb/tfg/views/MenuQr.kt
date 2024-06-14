package com.serrb.tfg.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.serrb.tfg.R
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.ScannerQR

    @Composable
    fun MenuQr(navController: NavHostController){
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
                    Boton2Nuevo(navController, "ClienteGuardar", "NUEVO QR",0xFFD9D9D9)
                    Spacer(modifier = Modifier.height(5.dp))
                    Boton2Escanear(navController, "ClienteGuardar", "ESCANEAR QR",0xFFD9D9D9)
                    Spacer(modifier = Modifier.height(5.dp))
                    Boton2Listar(navController, "ClienteGuardar", "MIS QR'S",0xFFD9D9D9)
                }
            }
        }
    }
    @Composable
    fun Boton2Nuevo(navController: NavHostController, ruta:String, texto:String, color: Long){
        Button(
            onClick = {navController.navigate("NuevoQr")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color),
                contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier= Modifier
                .fillMaxWidth()
                .padding(12.dp,6.dp),
            contentPadding = PaddingValues(0.dp,44.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = texto,
                    fontFamily = outfit,
                    letterSpacing = 0.05.em,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier=Modifier.width(140.dp).padding(end=5.dp)
                )
                Spacer(modifier = Modifier.width(20.dp)) // Agrega un espacio entre el icono y el texto
                Icon(
                    painter = painterResource(id = R.drawable.baseline_qr_code_24),
                    contentDescription = "Icono",
                    modifier = Modifier.size(104.dp) // Ajusta el tamaño del icono según lo necesites
                )
            }
        }
    }
@Composable
fun Boton2Escanear(navController: NavHostController, ruta: String, texto: String, color: Long) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(context, ScannerQR::class.java)
            context.startActivity(intent)
        },
        colors = ButtonDefaults.buttonColors(
                containerColor = Color(color),
                contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier= Modifier
                .fillMaxWidth()
                .padding(12.dp,6.dp),
            contentPadding = PaddingValues(0.dp,40.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = "Icono",
                    modifier = Modifier.size(104.dp) // Ajusta el tamaño del icono según lo necesites
                )
                Spacer(modifier = Modifier.width(15.dp)) // Agrega un espacio entre el icono y el texto
                Text(
                    text = texto,
                    fontSize = 24.sp,
                    fontFamily = outfit,
                    letterSpacing = 0.em,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier=Modifier.width(180.dp)
                )
            }
        }
    }
    @Composable
    fun Boton2Listar(navController: NavHostController, ruta:String, texto:String, color: Long){
        Button(
            onClick = {navController.navigate("ListaQr")},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(color),
                contentColor = Color.Black),
            shape = RoundedCornerShape(12.dp),
            modifier= Modifier
                .fillMaxWidth()
                .padding(12.dp,6.dp),
            contentPadding = PaddingValues(0.dp,44.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = texto,
                    fontFamily = outfit,
                    letterSpacing = 0.05.em,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier=Modifier.width(124.dp)
                )
                Spacer(modifier = Modifier.width(28.dp)) // Agrega un espacio entre el icono y el texto

                Icon(
                    painter = painterResource(id = R.drawable.baseline_qr_code_2_24),
                    contentDescription = "Icono",
                    modifier = Modifier.size(104.dp) // Ajusta el tamaño del icono según lo necesites
                )
            }
        }

    }

@Preview(showBackground = true)
@Composable
fun MenuQr() {
    val navController = rememberNavController()
    MenuQr(navController = navController)
}

