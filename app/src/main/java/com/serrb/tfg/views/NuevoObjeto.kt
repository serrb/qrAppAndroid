package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.CategoriaObjeto
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.viewmodel.ObjetosViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoObjeto(navController: NavHostController,idEspacio:Long?){

    val viewModel: ObjetosViewModel = viewModel()
    //val respuestaId by viewModel.respuestaId.observeAsState()
    val respuesta by viewModel.respuesta.observeAsState()
    var showDialog3 by remember { mutableStateOf(false) }

    // DEFINIMOS CAMPOS DEL QR
    var nombreObjeto by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf<String?>(null) }
    var color by remember { mutableStateOf("") }
    var idCategoria:Long=0;
    var marca by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    //CATEGORIA
    val categorias by viewModel.listaCategorias.observeAsState(emptyList())
    var selectedCategoria by remember { mutableStateOf<CategoriaObjeto?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    //DEFINIMOS SET FOCUS
    val focusRequester = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }
    val focusRequester4 = remember { FocusRequester() }
    val focusRequester5 = remember { FocusRequester() }
    val focusRequester6 = remember { FocusRequester() }
    val focusRequester7 = remember { FocusRequester() }

    var mensaje: String = ""

    //DEFINIMOS VARIABLES PARA ERRORES
    var errorNombreObjeto by remember { mutableStateOf("") }
    var errorColor by remember { mutableStateOf("") }
    var errorIdCategoria by remember { mutableStateOf("") }
    var errorMarca by remember { mutableStateOf("") }
    var errorCantidad by remember { mutableStateOf("") }
    var errorPrecio by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var errorIdEspacio by remember { mutableStateOf("") }



    LaunchedEffect(Unit) {
        try {
            println("el id que me has pasado para actualizar espacio es " + idEspacio)
            viewModel.getCategoriaObjetos()
            isMenuExpanded = false
        } catch (e: Exception) {
            // Manejar errores
            Log.d(ContentValues.TAG, "Error al cargar el Espacio: ", e)
        }    }

    //COMPOSABLES
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
    ) {
        Box( // Imagen superior
            modifier = Modifier
                .fillMaxWidth().weight(0.40f)
                .background(color = Color(0xFF130521))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(top=10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_category_24),
                    contentDescription = "hola",
                    Modifier
                        .width(90.dp).size(25.dp)
                )
                Text(
                    text = "NUEVO OBJETO",
                    fontFamily = outfit,
                    letterSpacing = 0.05.em,
                    color = Color.White,
                    fontSize =20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier=Modifier.padding(top=5.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_category_24),
                    contentDescription = "hola",
                    Modifier
                        .width(90.dp).size(25.dp).padding(end=5.dp)
                )
            }
            // Agregar la imagen de fondo
        }

        LazyColumn(
            modifier = Modifier
                .background(color = Color.White).weight(5f)
                .padding(end=20.dp,start=20.dp, top=10.dp,bottom=10.dp)
        ) {
            // CABECERA
            item{
                Text(
                    text="Por favor, rellene los campos del objeto",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.fillMaxWidth().padding(bottom=8.dp)
                )
            }

            //CAMPO NOMBRE DEL QR
            item{
                OutlinedTextField(
                    value = nombreObjeto,
                    onValueChange = { nombreObjeto = it },
                    label = {
                        Text("Nombre del objeto*",color = Color.Black)
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .focusRequester(focusRequester).padding(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next)
                )
                //ERROR*
                Text(
                    text = errorNombreObjeto,
                    fontSize = 12.sp,
                    color = Color.Red
                ) }

            /*CAMPO foto
            item{ OutlinedTextField(
                value = foto,
                onValueChange = { foto = it },
                label = {
                    Text("Foto",color = Color.Black)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester2),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
                //ERROR FOTO
                Text(
                    text = errorPropietario,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }*/

            //CAMPO COLOR
            item{
                OutlinedTextField(
                value = color, shape = RoundedCornerShape(8.dp),
                onValueChange = { color = it },
                label = {
                    Text("Color",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester3),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
                //ERROR*
                Text(
                    text = errorColor,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CATEGORIA
            item {
                Button(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    modifier = Modifier.fillMaxWidth().padding(bottom=5.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCCCCCC)
                    )
                )  {
                    Text(
                        text = selectedCategoria?.nombre ?: "Seleccione una categoría",
                        color=Color.Black,
                        modifier = Modifier.padding(6.dp)
                    )
                }
                println("LAX CATEOGRIAS 2 SON " + categorias)
                DropdownMenu(
                    expanded = isMenuExpanded && categorias.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth().height(180.dp),
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

            //CAMPO MARCA
            item{
                OutlinedTextField(
                value = marca,
                onValueChange = { marca = it },shape = RoundedCornerShape(8.dp),
                label = {
                    Text("Marca",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester5),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
                )
                //ERROR TELEFONO
                Text(
                    text = errorMarca,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CAMPO CANTIDAD
            item{
                OutlinedTextField(
                value = cantidad,shape = RoundedCornerShape(8.dp),
                onValueChange = { cantidad = it },
                label = {
                    Text("Cantidad",color =Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester7),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ))
                //ERROR RECOMPENSA
                Text(
                    text = errorMarca,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            val cantidadInt:Int = cantidad.toIntOrNull()?:0
            println(cantidad)
            println(cantidadInt)

            //CAMPO PRECIO
            item{
                OutlinedTextField(
                value = precio,shape = RoundedCornerShape(8.dp),
                onValueChange = { precio = it },
                label = {
                    Text("Precio",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester7),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
                )
                //ERROR RECOMPENSA
                Text(
                    text =errorPrecio,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            val precioDouble:Double = precio.toDoubleOrNull() ?: 0.0

            println(precioDouble)
            //CAMPO DESCRIPCION
            item{
                OutlinedTextField(
                    value = descripcion,shape = RoundedCornerShape(8.dp),
                    onValueChange = { descripcion = it },
                    label = {
                        Text("Descripcion",color = Color.Black,)
                    },
                    modifier = Modifier
                        .fillMaxWidth().height(120.dp)
                        .background(color = Color.White)
                        .focusRequester(focusRequester5),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                //ERROR DESCRIPCION
                Text(
                    text = errorDescripcion,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }
            //CAMPO IDESPACIO

            // TOMAMOS LOS DATOS DE LOS CAMPOS Y GENERAMOS EL OBJETO
            val objeto = Objeto(nombreObjeto,null,color,idCategoria,marca,cantidadInt,precioDouble,descripcion,idEspacio!!)

            println("el objeto es "+ objeto)

            //DIALOGO DE CONFIRMACIÓN, SE GENERARÁ ESPACIO.
            item {
                var showDialog by remember { mutableStateOf(false) }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Confirmación") },
                        text = { Text(text = "¿Desea guardar este Objeto?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    // Llamar a la función generateQR del ViewModel cuando se hace clic en el botón
                                    viewModel.viewModelScope.launch {
                                        try {
                                            println("veamos a ver si funciona" + objeto)
                                            val response = viewModel.guardarObjeto(objeto)
                                        } catch (e: Exception) {
                                            Log.e("Inicio", "Error al generar el Objeto", e)
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
                    StartDialogAct3(navController, mensaje, viewModel) { showDialog3 = false }

                }

                // VALIDAMOS DATOS AL PULSAR EN EL BOTON

                var datosValidos:Boolean = false
                datosValidos = ValidaDatosObjeto(nombreObjeto,color,marca,cantidadInt,precioDouble,descripcion)
                println(datosValidos)
                //AL PULSAR EN EL BOTON, SI LOS DATOS SON VALIDOS, MUESTRA DIALOG

                Button(
                    onClick = {
                        if (datosValidos) {
                            showDialog = true
                            errorNombreObjeto= ""
                            errorColor = ""
                            errorDescripcion =""
                            errorMarca = ""
                            errorCantidad = ""
                            errorPrecio = ""
                        }else{
                            // si no son validos, muestra errores
                            errorNombreObjeto= viewModel.nombreObjetoMsg.value
                            errorColor= viewModel.colorErrMsg.value
                            errorMarca =viewModel.marcaErrMsg.value
                            errorCantidad = viewModel.cantidadErrMsg.value
                            errorPrecio = viewModel.precioErrMsg.value
                            errorDescripcion= viewModel.descripcionErrMsg.value
                        }
                    }, // Mostrar el diálogo al hacer clic en el botón
                    modifier = Modifier.fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = CutCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFE6969),
                        contentColor = Color.Black,
                        disabledContainerColor = Color(0xFF81807F),

                        ),
                    content = {
                        Text(text = "GUARDAR OBJETO", fontSize = 16.sp)
                    }
                )
            }
        }
    }
}

@Composable
fun ValidaDatosObjeto (nombreObjeto:String,color:String,marca:String,cantidadInt: Int,precioDouble:Double,descripcion:String): Boolean {
    val viewModel: ObjetosViewModel = viewModel()

    var nombreIsValido: Boolean = false
    viewModel.nombreObjetoMsg.value = ""

    var colorIsValido: Boolean = false
    viewModel.colorErrMsg.value = ""

    var marcaIsValido: Boolean = false
    viewModel.marcaErrMsg.value = ""

    var cantidadIsValido: Boolean = false
    viewModel.cantidadErrMsg.value = ""

    var precioIsValido: Boolean = false
    viewModel.precioErrMsg.value = ""

    var descripcionIsValido: Boolean = false
    viewModel.descripcionErrMsg.value = ""

    //VALIDACIONES
    //Validamos que nombre debe ser menor a 20 caracteres alfanuméricos
    if (nombreObjeto.isNotEmpty()){
        if(nombreObjeto.length < 20 && nombreObjeto.matches("[a-zA-Z0-9\\s]+".toRegex())) {
            nombreIsValido = true
        }else{
            viewModel.nombreObjetoMsg.value = "Nombre menor a 20 caracteres alfanuméricos"
        }
    }  else {
        viewModel.nombreObjetoMsg.value = "El campo nombre no puede estar vacío"
    }
    println("nombre" + nombreIsValido)

    //Validamos que propietario debe ser menor a 20 caracteres alfanuméricos
    if (color.isNotEmpty()){
        if(color.length < 15 && color.matches("[a-zA-Z0-9\\s]+".toRegex())) {
            colorIsValido = true
        } else{
            viewModel.colorErrMsg.value = "Color menor a 15 caracteres alfanuméricos"
        }
    } else {
        viewModel.colorErrMsg.value = "El campo color no puede estar vacío"
    }

    println("color" + colorIsValido)

    //VALIDAMOS MARCA

    //Validamos que propietario debe ser menor a 20 caracteres alfanuméricos
        if(color.length < 20 && color.matches("[a-zA-Z0-9\\s]+".toRegex())) {
            marcaIsValido = true
        } else{
            viewModel.colorErrMsg.value = "Marca menor a 20 caracteres alfanuméricos"
        }

    //VALIDAMOS CANTIDAD
    if (cantidadInt!=null){
        if(cantidadInt< 1000) {
            cantidadIsValido = true
        } else{
            viewModel.colorErrMsg.value = "Cantidad debe ser inferior a 1000"
        }
    } else {
        viewModel.colorErrMsg.value = "El campo cantidad no puede estar vacío"
    }
    println("cantidad" + cantidadIsValido)

    //VALIDAMOS PRECIO
    val precioStr = precioDouble.toString()
    val index = precioStr.indexOf('.') //verificamos si hay punto
    val digitsAfterDecimal = if (index == -1) {
        0
    } else {
        precioStr.length - index - 1
    }
    if (digitsAfterDecimal <= 5) {
        precioIsValido = true
    } else {
        viewModel.precioErrMsg.value = "La recompensa debe tener un máximo de 5 dígitos"
    }
    println("precio" + precioIsValido)



    //Validamos que descripcion debe ser menor a 250 caracteres alfanuméricos
    if (descripcion.isNotEmpty()){
        if( descripcion.length <250){
            descripcionIsValido = true
        } else {
            viewModel.descripcionErrMsg.value = "Apellido menor a 30 caracteres alfanuméricos"
        }
    }else {
        viewModel.descripcionErrMsg.value = "El campo descripcion no puede estar vacío"
    }
    println("descripcion" + descripcionIsValido)

    // SI TODOS LOS CAMPOS SON CORRECTOS, DEVUELVE TRUE.
    if(nombreIsValido && colorIsValido && cantidadIsValido && precioIsValido && descripcionIsValido && marcaIsValido){
        return true
    }else{
        return false
    }

}
@Composable
fun StartDialogAct3(navController: NavController, mensaje: String, viewModel: ObjetosViewModel, onDismiss: () -> Unit) {
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
fun NuevoObjeto2() {
    val navController = rememberNavController()
    NuevoObjeto(navController = navController,2)
}
