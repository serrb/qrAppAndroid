package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.asImageBitmap
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.model.QR.CategoriaEspacio
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.EspaciosViewModel
import com.serrb.tfg.viewmodel.QRViewModel

import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoEspacio(navController: NavHostController,id:Long?){
    val viewModel: EspaciosViewModel = viewModel()

    // DEFINIMOS CAMPOS DEL QR
    var nombre by remember { mutableStateOf("") }
    var idCategoria:Long = 0;
    //DEFINIMOS SET FOCUS
    val focusRequester = remember { FocusRequester() }
    var showDialog2 by remember { mutableStateOf(false) }

    var mensaje: String = ""
    val respuesta by viewModel.respuesta.observeAsState()

    //DEFINIMOS VARIABLES PARA ERRORES
    var errorNombre by remember { mutableStateOf("") }

    //CATEGORIA
    val categorias by viewModel.listaCategorias.observeAsState(emptyList())
    var selectedCategoria by remember { mutableStateOf<CategoriaEspacio?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    println("LAX CATEOGRIAS SON " + categorias)

    LaunchedEffect(Unit) {
        try {
            println("el id que me has pasado para actualizar espacio es " + id)
            viewModel.getCategoriaEspacios()
            isMenuExpanded = false
        } catch (e: Exception) {
            // Manejar errores
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        }    }

    //COMPOSABLES
    Card(
        modifier = Modifier
            .background(color = Color(0xFF130521))
            .fillMaxSize().padding(18.dp,200.dp)
    ){
        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(15.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Nuevo Espacio",
                    color = Color.Black,
                    fontSize = 30.sp,
                    fontFamily= raleway,
                    fontWeight= FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                )
            }
            // CABECERA
            item{
                Text(
                    text="Por favor, indique el nombre del subespacio",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth().padding(bottom=23.dp)
                )
            }

            //CAMPO NOMBRE DEL QR
            item{
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre= it },
                    label = {
                        Text("Nombre",color = Color.Black)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next)
                )
                //ERROR*
                Text(
                    text = errorNombre,
                    fontSize = 12.sp,
                    color = Color.Red
                ) }

            item {
                Button(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    modifier = Modifier.fillMaxWidth().padding(0.dp,20.dp),
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
            }


            if(selectedCategoria!=null){
                idCategoria= selectedCategoria!!.id!!
            }else{
                idCategoria= 9
            }


            // TOMAMOS LOS DATOS DE LOS CAMPOS Y GENERAMOS EL OBJETO
            val espacio = Espacio(null,nombre,id!!,idCategoria)

            //DIALOGO DE CONFIRMACIÓN, SE GENERARÁ ESPACIO.
            item {
                var showDialog by remember { mutableStateOf(false) }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Confirmación") },
                        text = { Text(text = "¿Desea guardar este Espacio?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    // Llamar a la función generateQR del ViewModel cuando se hace clic en el botón
                                    viewModel.viewModelScope.launch {
                                        try {
                                            println("veamos a ver si funciona" + espacio)

                                           val response = viewModel.guardarEspacio(espacio)

                                        } catch (e: Exception) {
                                            // SI NO HAY CONEXION AL MICROSERVICIO LO GENERA LOCALMENTE...**REVISAR
                                            Log.e("Inicio", "Error al generar el Espacio", e)
                                        }
                                    }
                                }
                            ) {
                                Text(text = "Guardar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false }
                            ) {
                                Text(text = "Cancelar")
                            }
                        }
                    )
                }
                mensaje = respuesta?.mensaje ?: "";
                if (mensaje.isNotEmpty()) {
                    StartDialogAct3(navController, mensaje, viewModel) { showDialog2 = false }

                }

                // VALIDAMOS DATOS AL PULSAR EN EL BOTON

                var datosValidos: Boolean = false
                datosValidos = ValidaDatosEspacio(espacio.nombre)

                //AL PULSAR EN EL BOTON, SI LOS DATOS SON VALIDOS, MUESTRA DIALOG

                Button(
                    onClick = {
                        if (datosValidos) {
                            showDialog = true
                            errorNombre= ""
                        }else{
                            // si no son validos, muestra errores
                            errorNombre= viewModel.nombreErrMsg.value

                        }
                    }, // Mostrar el diálogo al hacer clic en el botón
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(16.dp),
                    shape = CutCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF81807F),

                        ),
                    content = {
                        Text(text = "GUARDAR ESPACIO", fontSize = 16.sp,
                            fontFamily= raleway,
                            fontWeight= FontWeight.Bold)
                    }
                )
            }
        }
    }
}

@Composable
fun ValidaDatosEspacio (nombre:String): Boolean {
    val viewModel: EspaciosViewModel = viewModel()

    var nombreIsValido: Boolean = false
    viewModel.nombreErrMsg.value = ""

    println(nombre)
    //VALIDACIONES
    //Validamos que nombre debe ser menor a 20 caracteres alfanuméricos
    if (nombre.isNotEmpty()){
        if(nombre.length < 20 && nombre.matches(Regex("^[a-zA-Z0-9\\s]+$")))  {
            nombreIsValido = true
            viewModel.nombreErrMsg.value = "VALIDO"
        }else{
            viewModel.nombreErrMsg.value = "Nombre menor a 20 caracteres alfanuméricos"
        }
    }  else {
        viewModel.nombreErrMsg.value = "El campo nombre no puede estar vacío"
    }

    // SI TODOS LOS CAMPOS SON CORRECTOS, DEVUELVE TRUE.
    if(nombreIsValido==true){
        return true
        println("validado")

    }else{
        return false
        println("no valido")
    }


}

@Composable
fun StartDialogAct3(navController: NavController, mensaje: String, viewModel: EspaciosViewModel, onDismiss: () -> Unit) {
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
                    Text("Volver al Listado")
                }
            },
            dismissButton = {
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NuevoQr5() {
    val navController = rememberNavController()
    NuevoEspacio(navController = navController,3)
}

