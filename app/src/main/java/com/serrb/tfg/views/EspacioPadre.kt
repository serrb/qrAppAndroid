package com.serrb.tfg.views

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.viewmodel.EspaciosViewModel
import com.serrb.tfg.viewmodel.QRViewModel

@Composable
fun EspacioPadre(navController: NavHostController, viewModel: EspaciosViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val listaEspacios by viewModel.listaEspacios.observeAsState(emptyList())


    LaunchedEffect(Unit) {
        try {
            viewModel.getEspaciosPadre(0)
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
    ) {Box( // Imagen superior
        modifier = Modifier
            .fillMaxWidth().weight(0.75f)
            .background(color = Color(0xFF130521))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top=25.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "MIS CASAS",
                fontFamily = outfit,
                letterSpacing = 0.05.em,
                color = Color.White,
                fontSize =25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier=Modifier.padding(start=20.dp,top=5.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.house),
                contentDescription = "hola",
                Modifier
                    .width(90.dp).size(35.dp)
            )
        }
        // Agregar la imagen de fondo
    }
        Box(
            modifier = Modifier
                .background(color = Color(0xFF130521))
                .weight(5f)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(top = 14.dp, end = 4.dp, start = 4.dp, bottom = 8.dp)
            ) {
                items(listaEspacios.size) { index ->
                    val Espacio = listaEspacios[index]
                    CardItems(navController, viewModel, Espacio)
                }
                item {
                    Button(
                        onClick = {   navController.navigate(AppScreens.NuevoEspacioPadre.route + "${0}")},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEE5C5C),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(5.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(50.dp, 20.dp)
                    ) {
                        Text(text= "Añadir Casa")
                    }
                }
            }
        }

    }
}


@Composable
fun CardItems(navController: NavController, viewModel: ViewModel, Espacio: Espacio?){

    var id : Long = 0
    var nombre by remember { mutableStateOf("") }
    var idEspacioPadre: Long = 0

    if (Espacio != null) {
        id = Espacio.id!!
        nombre = Espacio.nombre
        idEspacioPadre = Espacio.idEspacioPadre!!
    }

    Card(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .height(250.dp).fillMaxWidth().padding(50.dp,10.dp)
            .clickable { navController.navigate(AppScreens.Subespacio.route+ "${id}") }
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        ) {
        Row (
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0xffE6724D))
                .background(Color(0xFFFFFFFF))
        ){
                Text(
                    text = "${nombre}",
                    Modifier
                        .fillMaxWidth(0.45f).padding(horizontal = 10.dp, vertical = 4.dp),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.casawhite),
                    contentDescription = "hola",
                    Modifier.fillMaxWidth(1f).size(180.dp).padding(top=20.dp,end=15.dp)
                        .padding(end=1.dp,bottom=1.dp)

                )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MenuInicioPreview7() {
    val navController = rememberNavController()
    EspacioPadre(navController = navController)
}


