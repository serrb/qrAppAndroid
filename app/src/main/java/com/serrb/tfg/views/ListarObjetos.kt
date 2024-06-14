package com.serrb.tfg.views

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.CategoriaObjeto
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.ObjetosViewModel

@Composable
fun listarObjetos(navController: NavHostController) {

    val viewModel: ObjetosViewModel = viewModel()
    val listaObjetos by viewModel.listaObjetos.observeAsState(emptyList())
    val objetosBuscados by viewModel.listaObjetosIdCat.observeAsState(emptyList()) // Default to emptyList to avoid null
    val categorias by viewModel.listaCategorias.observeAsState(emptyList())
    var selectedCategoria by remember { mutableStateOf<CategoriaObjeto?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var filteredList by remember { mutableStateOf<List<Objeto>>(listaObjetos) }
    var searchText by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        try {
            viewModel.getObjetos()
            viewModel.getCategoriaObjetos()
            isMenuExpanded = false
        } catch (e: Exception) {
            // Handle errors
            Log.d(ContentValues.TAG, "Error al cargar los Objetos: ", e)
        }
    }

    LaunchedEffect(selectedCategoria, searchText, objetosBuscados, listaObjetos) {
        filteredList = listaObjetos.filter { objeto ->
            (selectedCategoria == null || objeto.idCategoria == selectedCategoria?.id) &&
                    (searchText.isBlank() || objeto.nombre.contains(searchText, ignoreCase = true))
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
                    text = "MIS OBJETOS",
                    fontFamily = outfit,
                    letterSpacing = 0.05.em,
                    color = Color.White,
                    fontSize =25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier=Modifier.padding(start=20.dp,top=5.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_category_24),
                    contentDescription = "hola",
                    Modifier
                        .width(90.dp).size(35.dp)
                )
            }
            // Agregar la imagen de fondo
        }

        Column(){
            Button(
                onClick = { isMenuExpanded = !isMenuExpanded },
                modifier = Modifier.fillMaxWidth().padding(0.dp,0.dp).height(60.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFE6969)
                )
            )  {
                Text(
                    text = selectedCategoria?.nombre ?: "Filtrar por categoría",
                    color=Color.White,
                    modifier = Modifier.padding(6.dp)
                )

            println("LAX CATEOGRIAS 2 SON " + categorias)
            DropdownMenu(
                expanded = isMenuExpanded && categorias.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(150.dp),
                onDismissRequest = { isMenuExpanded = false }
            ) {
                categorias.forEach { categoria ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategoria = categoria
                            isMenuExpanded = false
                        },
                        text = {
                            Text(text = categoria.nombre)
                        },
                        interactionSource = remember { MutableInteractionSource() } // Objeto MutableInteractionSource
                    )
                }
            }}
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Buscar por nombre") },
                modifier = Modifier
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(top=0.dp,end = 0.dp).background(Color.White)
            )
        }

        Column(  modifier = Modifier
            .fillMaxWidth()
            .weight(5f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                    )
                    .padding(top = 4.dp, end = 4.dp, start = 4.dp, bottom = 8.dp)
            ) {
                items(filteredList.size) { index ->
                    val Espacio = filteredList.reversed()[index]
                    CardItemsObjetos(
                        navController,
                        viewModel,
                        Espacio,
                    )
                }

            }
        }

    }
}


@Composable
fun CardItemsObjetos(navController: NavController, viewModel: ViewModel, Objeto: Objeto?) {

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
            .height(100.dp)
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
                modifier = Modifier.size(100.dp) // Ajusta el tamaño del icono según lo necesites
            )
            Column() {
                Row(){
                    Text(
                        text = "${nombre}".toUpperCase(),
                        Modifier
                            .fillMaxWidth().weight(1f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = raleway,
                            letterSpacing = 0.05.em,
                        )
                    )
                    Text(
                        text = "Cant:" +"${cantidad}",
                        Modifier
                            .fillMaxWidth().weight(0.4f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Row(){
                    Text(
                        text = "${marca}",
                        Modifier
                            .fillMaxWidth().weight(1f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                    Text(
                        text ="${color}",
                        Modifier
                            .fillMaxWidth().weight(0.4f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Row(){
                    Text(
                        text = "Descr:" + "${descripcion}",
                        Modifier
                            .fillMaxWidth().weight(1f)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Italic,
                        )
                    )

                }
            }
            /*
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
            }*/
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview14() {
    val navController = rememberNavController()
    listarObjetos(navController = navController)
}