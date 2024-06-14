package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.model.QR.CategoriaEspacio
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.EspaciosViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarEspacio(navController: NavHostController, id: Long?) {
    val EspacioViewModel: EspaciosViewModel = viewModel()
    val respuesta by EspacioViewModel.respuesta.observeAsState()

    var showDialog2 by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var isComLoading by remember { mutableStateOf(true) }

    var nombre by remember { mutableStateOf("") }
    var idPadre: Long? = 0;

    //CATEGORIA
    val categorias by EspacioViewModel.listaCategorias.observeAsState(emptyList())
    var selectedCategoria by remember { mutableStateOf<CategoriaEspacio?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }
    var idCategoria: Long = 0;

    //Busca los datos del Cliente en la Base de Datos
    LaunchedEffect(Unit) {
        try {
            println("el id que me has pasado para actualizar espacio es " + id)
            EspacioViewModel.getEspacioPorId(id)
            EspacioViewModel.getCategoriaEspacios()
        } catch (e: Exception) {
            // Manejar errores
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        } finally {
            isComLoading = false
        }
    }
    if (isComLoading) {
        CircularProgressIndicator() // Muestra el indicador de carga
    } else {
        val Espacio by EspacioViewModel.espacioById.observeAsState()

        println(Espacio)
        if (Espacio != null) {
            // Asignamos los datos del QR a las variables de estado del formulario
            Espacio?.let { Espacio ->
                nombre = Espacio.nombre ?: ""
                idPadre = (Espacio.idEspacioPadre ?: "") as Long?
            }
        }
    }
    Card(
        modifier = Modifier
            .background(color = Color(0xFF130521))
            .fillMaxSize().padding(18.dp, 230.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White)
        ) {
            Text(
                text = "Actualizar Espacio",
                color = Color.Black,
                fontSize = 30.sp,
                fontFamily = raleway,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
            )
            Text(
                text = "Por favor, indique el nombre del subespacio",
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.fillMaxWidth().padding(bottom = 23.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            // NOMBRE QR
            var nombre2 by remember { mutableStateOf(nombre) }
            OutlinedTextField(
                value = nombre2,
                onValueChange = { nombre2 = it },
                label = { Text("Nombre") },
                modifier = Modifier
                    .fillMaxWidth().padding(10.dp, 0.dp)
                    .background(color = Color.White)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                singleLine = false,
                enabled = true,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = { isMenuExpanded = !isMenuExpanded },
                modifier = Modifier.fillMaxWidth().padding(10.dp,10.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEE5C5C)
                )
            )  {
                Text(
                    text = selectedCategoria?.nombre ?: "Seleccione una categoría",
                    modifier = Modifier.padding(6.dp),
                    fontFamily= raleway,
                    fontWeight= FontWeight.Bold,
                )
            }
            println("LAX CATEOGRIAS 2 SON " + categorias)
            DropdownMenu(
                expanded = isMenuExpanded && categorias.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(360.dp),
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
            }
            if(selectedCategoria!=null){
                idCategoria= selectedCategoria!!.id!!
            }else{
                idCategoria= 9
            }
            Button(
                onClick = { //onClick = { showDialog = true },
                    EspacioViewModel.actualizarEspacio(
                        Espacio(
                            id = id,
                            nombre = nombre2,
                            idPadre, idCategoria
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(40.dp, 10.dp),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF130521)
                ),
                content = {
                    Text(
                        text = "ACTUALIZAR ESPACIO",
                        fontSize = 16.sp,
                        fontFamily = raleway,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
            mensaje = respuesta?.mensaje ?: "";
            if (mensaje.isNotEmpty()) {
                StartDialogAct2(navController, mensaje, EspacioViewModel) { showDialog2 = false }

            }
        }
    }
}


@Composable
fun StartDialogAct2(navController: NavController, mensaje: String, viewModel: EspaciosViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    Dialog(onDismissRequest = { onDismiss() }) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("${mensaje}") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White),
                    onClick = {
                        navController.popBackStack()
                        onDismiss()
                    }
                ) {
                    Text("Volver")
                }
            },
            dismissButton = {
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview61() {
    val navController = rememberNavController()
    ActualizarEspacio(navController = navController, id = 4)
}