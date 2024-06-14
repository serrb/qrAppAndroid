package com.serrb.tfg.views

import android.content.ContentValues
import android.media.Image
import androidx.compose.foundation.Image
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.CategoriaObjeto
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.QRViewModel
import kotlinx.coroutines.launch

@Composable
fun ListaQrs(
    navController: NavHostController,
    viewModel: QRViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
) {
    val listaQRs by viewModel.listaQRs.observeAsState(emptyList())
    var selectedQR by remember { mutableStateOf<CategoriaObjeto?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var filteredList by remember { mutableStateOf<List<QR>>(listaQRs) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(selectedQR, searchText, listaQRs) {
        filteredList = listaQRs.filter { qr ->
            searchText.isBlank() || qr.nombreQR.contains(searchText, ignoreCase = true)
        }
    }


    LaunchedEffect(Unit) {
        try {
            viewModel.getQRs()
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar el Cliente: ", e)
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
                text = "MIS QR'S",
                fontFamily = outfit,
                letterSpacing = 0.05.em,
                color = Color.White,
                fontSize =30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier=Modifier.padding(start=20.dp,top=5.dp)
                    .clickable {
                        navController.navigate(AppScreens.MenuQr.route)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                contentDescription = "hola",
                Modifier
                    .width(90.dp).size(40.dp)
            )
        }
        // Agregar la imagen de fondo
    }
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar QR por nombre...") },
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .padding(5.dp,0.dp).background(Color.White)
        )

        Box( // Botones
            modifier = Modifier
                .background(color = Color(0xFF130521))
                .weight(5f)
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    )
                    .padding(top = 0.dp, end = 4.dp, start = 4.dp, bottom = 8.dp)
            ) {
                items(filteredList.size) { index ->
                    val QR = filteredList.reversed()[index] // Utilizamos filteredList en lugar de listaQRs
                    CardItems(navController, viewModel, QR)
                }
            }
        }
    }
}


@Composable
fun CardItems(navController: NavController, viewModel: ViewModel, QR: QR?){

    var id : Long = 0
    var objeto by remember { mutableStateOf("") }
    var propietario by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }
    var recompensa by remember { mutableStateOf("") }
    var mensaje: String =""

    if (QR != null) {

        id = QR.id!!
        objeto = QR.nombreQR
        propietario = QR.propietario
        descripcion = QR.descripcion
        direccion = QR.direccion
        telefono =QR.telefono
        correo = QR.emailCliente
        imagen = QR.qrImagePath.toString()
    }

    Card(
        modifier = Modifier
            .height(100.dp)
            .clickable { navController.navigate(AppScreens.ActualizarQr.route+ "${id}") }
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(10.dp),

        ) {
        Row (
            modifier = Modifier
                .fillMaxHeight()
                //.background(Color(0xffE6724D))
                .background(Color(0xFFededed))
        ){
            val painter: Painter = rememberImagePainter(imagen)
            // Muestra la imagen dentro de un elemento Image
            Image(
                painter = painter,
                contentDescription = null, // Opcional: proporciona una descripción de contenido
                modifier = Modifier
                    .weight(0.3f)
                    .width(50.dp)
                    .size(90.dp)
                    .padding(2.dp,2.dp)
            )
                Column(modifier=Modifier.width(230.dp)){
                    Text(text = "${objeto}".toUpperCase(),
                        Modifier
                            .padding(horizontal = 10.dp,vertical=4.dp)
                            .align(alignment = Alignment.Start),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(text = "Propietario: ${propietario}",
                        Modifier
                            .padding(horizontal = 10.dp,vertical=4.dp)
                            .align(alignment = Alignment.Start),
                        style = TextStyle(
                            fontSize = 14.sp,
                        )

                    )
                    Text( text = "Descripcion:${descripcion.take(20)}...",//mostraremos solo 20 caracteres
                        Modifier
                            .padding(horizontal = 10.dp,vertical=4.dp)
                            .align(alignment = Alignment.Start),
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontStyle = FontStyle.Italic,
                        )
                    )
                }

            var showDialog by remember { mutableStateOf(false) }
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.weight(0.25f).align(Alignment.CenterVertically).width(20.dp)
            ) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Start Dialog")
            }
            if (showDialog) {
                StartDialog(navController,id = id, viewModel = QRViewModel()) {
                    showDialog = false
                }
            }
        }
    }
}


@Composable
fun StartDialog(navController: NavController,id: Long,viewModel:QRViewModel,onDismiss: () -> Unit) {

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
                            viewModel.deleteQr(id)
                        navController.navigate(AppScreens.ListaQr.route)
                        viewModel.getQRs()
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
fun MenuInicioPreview5() {
    val navController = rememberNavController()
    ListaQrs(navController = navController)
}


