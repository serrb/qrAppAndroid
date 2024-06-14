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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.EspaciosViewModel
import com.serrb.tfg.viewmodel.ObjetosViewModel
import com.serrb.tfg.viewmodel.QRViewModel

@Composable
fun Subespacio(navController: NavHostController,id:Long?) {
    val viewModel: EspaciosViewModel = viewModel()
    val listaEspacios by viewModel.listaEspacios.observeAsState(emptyList())

    val viewModel2: ObjetosViewModel = viewModel()
    val listaObjetos by viewModel2.listaObjetos.observeAsState(emptyList())

    val objeto by viewModel.espacioById.observeAsState()


    println("comprobando que funcone es el " + id)

    LaunchedEffect(Unit) {
        try {
            viewModel.getEspaciosPadre(id)
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        }
    }

    LaunchedEffect(Unit) {
        try {
            viewModel2.getObjetosByIdPadre(id)
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar el Objeto: ", e)
        }
    }
    LaunchedEffect(Unit) {
        try {
            viewModel.getEspacioPorId(id)
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        }
    }

    val idCategoria = objeto?.idCategoria
    val imageResId = when (idCategoria) {  //asignamos foto en funcion de la categoria del espacio
        0L->R.drawable.casawhite
        1L -> R.drawable.sofa
        2L -> R.drawable.cocina
        3L -> R.drawable.dormitorio
        4L -> R.drawable.tasca
        5L -> R.drawable.almacenamiento
        6L -> R.drawable.almacenamiento
        7L -> R.drawable.ofic
        8L -> R.drawable.almacenamiento
        9L -> R.drawable.almacenamiento
        10L->R.drawable.casawhite
        null ->R.drawable.almacenamiento
        else -> R.drawable.casawhite// si no hay categoria muestra por defecto
    }

    Card(
        modifier = Modifier
            .background(color = Color(0xFF000000))
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(2.75f)
                .background(
                    Color(0xFFEE5C5C)
                ),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        shape = RoundedCornerShape(bottomStart = 64.dp),
                        color =Color(0xFFFFFFFF).copy(alpha = 1f),
                    )

            ) {
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(1f),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier=Modifier.weight(1f).fillMaxWidth().padding(top=7.dp,end=10.dp),
                            text = "${objeto?.nombre}".toUpperCase(),
                            color = Color.Black,
                            fontSize = 41.sp,
                            overflow = TextOverflow.Clip,
                            textAlign = TextAlign.Right,
                            fontFamily = outfit,
                            letterSpacing = 0.05.em,
                            fontWeight = FontWeight.ExtraBold
                        )
                        IconButton(modifier= Modifier
                            .weight(0.15f).padding(bottom=18.dp),
                            onClick = {
                                navController.navigate(AppScreens.ActualizarEspacio.route + "${id}")
                            }
                        ) {
                            Icon(modifier= Modifier
                                .size(30.dp)
                                .background(Color(0x9CFFFFFF))
                                .align(Alignment.Bottom),
                                imageVector = Icons.Filled.Edit, contentDescription = "Añadir")
                        }

                        var showDialog by remember { mutableStateOf(false) }
                        IconButton(
                            onClick = { showDialog = true },
                            modifier = Modifier.weight(0.15f).align(Alignment.CenterVertically).width(20.dp).padding(bottom=15.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Start Dialog")
                        }
                        if (showDialog) {
                            StartDialog4(navController,id = id!!, viewModel =viewModel()) {
                                showDialog = false
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth().padding(end=10.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Espacio Image",
                            modifier = Modifier
                                .weight(2.8f)
                                .fillMaxHeight()
                                .padding(start=140.dp,bottom = 10.dp)
                        )
                    }

                } // Agregar la imagen de fondo
            }
        }
        Column(  modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEE5C5C))
            .weight(5f)) {
            Row(modifier=Modifier.fillMaxWidth()){
                IconButton(
                    onClick = {
                        navController.navigate(AppScreens.NuevoEspacio.route + "${id}")
                    },
                    modifier = Modifier
                    .weight(0.25f)
                    .padding(top = 12.dp)
                    .clip(RoundedCornerShape(2.dp))
                ){
                    Icon(modifier= Modifier
                        .padding(start=170.dp)
                        .background(color = Color(0xFFDFDFDF), shape = RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically),
                        imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }

                Text(
                    text = "SUBESPACIOS",
                    modifier=Modifier.padding(top=20.dp,end=15.dp).weight(0.2f),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = raleway,
                    textAlign = TextAlign.End,
                    fontWeight=FontWeight.Bold
                )

            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 1.dp, end = 4.dp, start = 4.dp)
            ) {
                items(listaEspacios.size) { index ->
                    val Espacio = listaEspacios.reversed()[index]
                    CardItems2(
                        navController,
                        viewModel,
                        Espacio,
                    )
                }
            }
            Row(){
                IconButton(modifier= Modifier
                    .weight(1f).padding(top=5.dp),
                    onClick = {
                        navController.navigate(AppScreens.NuevoObjeto.route + "${id}")
                    }
                ) {
                    Icon(modifier= Modifier
                        .padding(start=170.dp)
                        .background(color = Color(0xFFDFDFDF), shape = RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically),
                        imageVector = Icons.Filled.Add, contentDescription = "Añadir")
                }
                Text(
                    text = "MIS OBJETOS",
                    modifier=Modifier.fillMaxWidth(0.44f).padding(top=10.dp,end=15.dp),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontFamily = raleway,
                    textAlign = TextAlign.End,
                    fontWeight=FontWeight.Bold
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(2f)
                    .padding(top = 0.dp, end = 4.dp, start = 4.dp, bottom = 8.dp)
            ) {

                items(listaObjetos.size) { index ->
                    val Objeto = listaObjetos.reversed()[index]
                    CardObjetos(
                        navController,
                        viewModel,
                        Objeto,
                    )
                }
            }
        }

        }
}


@Composable
fun CardItems2(navController: NavController, viewModel: ViewModel, Espacio: Espacio?){

    var id : Long = 0
    var nombre by remember { mutableStateOf("") }
    var idEspacioPadre: Long = 0
    var idCategoria :Long=0

    if (Espacio != null) {
        id = Espacio.id!!
        nombre = Espacio.nombre
        idEspacioPadre = Espacio.idEspacioPadre!!
        idCategoria = Espacio.idCategoria!!
    }

    Card(
        modifier = Modifier
            .height(80.dp)
            .width(150.dp)
            .clickable { navController.navigate(AppScreens.Subespacio.route + "${id}") }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0xffE6724D))
                .background(color=Color(0xFF130521)),
        ) {
            Column(modifier = Modifier.width(230.dp).background(color=Color(0xFF130521)),) {
                Text(
                    text = "${nombre}".toUpperCase(),
                    Modifier
                        .padding(horizontal =10.dp, vertical = 15.dp).align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize =15.sp,
                        fontFamily = raleway,
                        fontWeight = FontWeight.Bold,
                        color= Color.White
                    )
                )
            }

        }
    } }
@Composable
fun CardObjetos(navController: NavController, viewModel: ViewModel, Objeto: Objeto?) {


    var id: Long = 0
    var nombre by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var idEspacioPadre: Long = 0
    var idCategoria :Long=0
    var cantidad:Int =0

    if (Objeto != null) {
        id = Objeto.id!!
        nombre = Objeto.nombre
        color = Objeto.color
        marca = Objeto.marca
        cantidad = Objeto.cantidad
        descripcion = Objeto.descripcion
        idEspacioPadre = Objeto.idEspacio
        idCategoria = Objeto.idCategoria
    }

    Card(
        modifier = Modifier
            .height(80.dp)
            .clickable {
                navController.navigate(AppScreens.ActualizarObjeto.route + "/${id}/${idCategoria}/${idEspacioPadre}")
            }
            .width(400.dp)
            //.clickable { navController.navigate(AppScreens.Subespacio.route + "${id}") }
            .padding(vertical = 8.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),

        ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                //.background(Color(0xffE6724D))
                .background(Color(0xFFededed))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.objetos),
                contentDescription = "Icono",
                modifier = Modifier.size(70.dp) // Ajusta el tamaño del icono según lo necesites
            )
            Column(modifier=Modifier.weight(0.7f).padding(start=10.dp)
            ) {
                    Text(
                        text = "${nombre}".toUpperCase(),
                        Modifier
                            .fillMaxWidth().weight(1f).padding(top=5.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = raleway,
                            letterSpacing = 0.05.em,
                        )
                    )
                Row(modifier=Modifier.weight(0.8f)){
                    Text(
                        text = "${marca}",
                        Modifier
                            .fillMaxWidth().weight(0.9f),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text = "Cant:" +"${cantidad}",
                        Modifier
                            .fillMaxWidth().weight(0.7f),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                }
            var showDialog by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.weight(0.25f).padding(top=7.dp)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Start Dialog")
            }
            if (showDialog) {
                StartDialog(navController,id = id,idEspacioPadre =idEspacioPadre, viewModel = ObjetosViewModel()) {
                    showDialog = false
                }
            }
            }
        }
    }


@Composable
fun StartDialog(navController: NavController,id: Long, idEspacioPadre:Long,viewModel:ObjetosViewModel,onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("¿Está seguro que desea eliminar esta QR?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White),
                    onClick = {
                        viewModel.deleteObjeto(id)
                        navController.navigate(AppScreens.Subespacio.route+ "${idEspacioPadre}")
                        onDismiss()
                    }
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White),
                    onClick = {
                        // User cancelled the dialog
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun StartDialog4(navController: NavController,id: Long,viewModel:EspaciosViewModel,onDismiss: () -> Unit) {

    Dialog(onDismissRequest = { onDismiss() }) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("¿Está seguro que desea eliminar este espacio?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White),
                    onClick = {
                        viewModel.deleteEspacio(id)
                        navController.popBackStack()
                        viewModel.getEspacios()
                        onDismiss()
                    }
                ) {
                    Text("Borrar")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White),
                    onClick = {
                        // User cancelled the dialog
                        onDismiss()
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview6() {
    val navController = rememberNavController()
    Subespacio(navController = navController,null)
}
