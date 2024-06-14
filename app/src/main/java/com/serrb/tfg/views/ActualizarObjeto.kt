package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.CategoriaObjeto
import com.serrb.tfg.model.QR.Espacio
import com.serrb.tfg.model.QR.Objeto
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.viewmodel.EspaciosViewModel
import com.serrb.tfg.viewmodel.ObjetosViewModel
import com.serrb.tfg.viewmodel.QRViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarObjeto(navController: NavHostController, id: Long?, idCategoria:Long?,idEspacioPadre:Long?) {
    val viewModel: ObjetosViewModel = viewModel()
    val espaciosViewModel: EspaciosViewModel = viewModel()
    val qrViewModel : QRViewModel = viewModel()

    val respuesta by viewModel.respuesta.observeAsState()

    var showDialog2 by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    var isComLoading by remember { mutableStateOf(true) }

    // DEFINIMOS CAMPOS DEL OBJETO
    var objetoId:Long=0;
    var nombreObjeto by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf<String?>(null) }
    var color by remember { mutableStateOf("") }
    var idCategoria2:Long=0;
    var marca by remember { mutableStateOf("") }
    var cantidad:Int=0;
    var precio: Double =0.0;
    var descripcion by remember { mutableStateOf("") }
    var idEspacio2 by remember { mutableStateOf<Long?>(null) }

    //CATEGORIA
    val categorias by viewModel.listaCategorias.observeAsState(emptyList())
    val categoria by viewModel.objetoById2.observeAsState()
    var selectedCategoria by remember { mutableStateOf<CategoriaObjeto?>(null) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    // Obtener y aplanar los espacios
    val listaEspacios by espaciosViewModel.listaEspacios.observeAsState(emptyList())
    println("************** " + listaEspacios)
    val espacio by espaciosViewModel.espacioById.observeAsState()
    var selectedEspacio by remember { mutableStateOf<Espacio?>(null) }
    var isMenuExpanded2 by remember { mutableStateOf(false) }
    println("***********" + listaEspacios)

    //QRs lista

    val listaQRs by qrViewModel.listaQRs.observeAsState()
    //DEFINIMOS SET FOCUS
    val focusRequester = remember { FocusRequester() }

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
            println("el id del objeto que me has pasado es " + id)
            viewModel.getObjetoByIdObjeto(id)
            viewModel.getCategoriaObjetos()
            viewModel.getCategoriaById(idCategoria)
            espaciosViewModel.getEspacios()
            espaciosViewModel.getEspacioPorId(idEspacioPadre)
            qrViewModel.getQRs()
        } catch (e: Exception) {
            // Manejar errores
            Log.d(ContentValues.TAG, "Error al cargar el QR: ", e)
        } finally {
            isComLoading = false
        }
    }


    if (isComLoading) {
        CircularProgressIndicator() // Muestra el indicador de carga
    } else {
        val Objeto by viewModel.objetoById.observeAsState()

        if (Objeto != null) {
            // Asignamos los datos del QR a las variables de estado del formulario
            Objeto?.let { Objeto ->
                objetoId = Objeto.id!!
                nombreObjeto = Objeto.nombre ?:""
                color = Objeto.color ?: ""
                idCategoria2 = (Objeto.idCategoria ?:"") as Long
                marca = Objeto.marca ?: ""
                cantidad = Objeto.cantidad
                precio = Objeto.precio
                descripcion= Objeto.descripcion
                idEspacio2 = Objeto.idEspacio
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
            ) {
                Box( // Imagen superior
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.40f)
                        .background(color = Color(0xFF130521))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_category_24),
                            contentDescription = "hola",
                            Modifier
                                .width(90.dp)
                                .size(25.dp)
                        )
                        Text(
                            text = "ACTUALIZAR OBJETO",
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
                                .width(90.dp)
                                .size(25.dp)
                                .padding(end = 5.dp)
                        )
                    }
                    // Agregar la imagen de fondo
                }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(5f)
                    .background(Color.White)
                    .padding(16.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.objetos),
                    contentDescription = "Icono",
                    modifier = Modifier.size(70.dp) // Ajusta el tamaño del icono según lo necesites
                )
                // NOMBRE OBJETO
                var nombre2 by remember { mutableStateOf(nombreObjeto) }
                OutlinedTextField(
                    value = nombre2,shape = RoundedCornerShape(8.dp),
                    onValueChange = {nombre2=it},
                    label = { Text("Nombre Objeto") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = false,
                    enabled =true,
                )
                Text(
                    text = errorNombreObjeto,
                    fontSize = 12.sp,
                    color = Color.Red
                )
                //COLOR
                var color2 by remember { mutableStateOf(color) }
                OutlinedTextField(
                    value = color2,shape = RoundedCornerShape(8.dp),
                    onValueChange = { color2 = it },
                    label = { Text("Color") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = errorColor,
                    fontSize = 12.sp,
                    color = Color.Red
                )
                //CATEGORIA
                //selectedCategoria = categoria
                Button(
                    onClick = { isMenuExpanded = !isMenuExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCCCCCC)
                    )
                )  {
                    Text(
                        text = selectedCategoria?.nombre ?: categoria?.nombre?:"",
                        color=Color.Black,
                        modifier = Modifier.padding(6.dp)
                    )
                }
                println("LAX CATEOGRIAS 2 SON " + categorias)
                DropdownMenu(
                    expanded = isMenuExpanded && categorias.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
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
                    idCategoria2= selectedCategoria!!.id!!
                }else{
                    idCategoria2= idCategoria2
                }

                // MARCA
                var marca2 by remember { mutableStateOf(marca) }
                OutlinedTextField(
                    value = marca2,shape = RoundedCornerShape(8.dp),
                    onValueChange = { marca2 = it },
                    label = { Text("Marca") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = errorMarca,
                    fontSize = 12.sp,
                    color = Color.Red
                )
                // Campo de texto para la cantidad
                var cantidad2 by remember { mutableStateOf(cantidad) }
                OutlinedTextField(
                    value = cantidad2.toString(), // Convertir el Int a String
                    onValueChange = { cantidad2 = it.toIntOrNull() ?: 0 }, // Convertimos el String de nuevo a Double
                    label = { Text("Cantidad") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text =errorCantidad,
                    fontSize = 12.sp,
                    color = Color.Red
                )
                Button(
                    onClick = { isMenuExpanded2 = !isMenuExpanded2 },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Text(
                        text = selectedEspacio?.nombre ?: espacio?.nombre?:"",
                        color = Color.Black,
                        modifier = Modifier.padding(6.dp)
                    )
                }

                // Menú desplegable
                DropdownMenu(
                    expanded = isMenuExpanded2 && listaEspacios.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    onDismissRequest = { isMenuExpanded2 = false }
                ) {
                    listaEspacios.forEach { espacio ->
                        val nombreCompletoEspacio = espaciosViewModel.getNombreCompletoEspacio(espacio.id)
                        DropdownMenuItem(
                            onClick = {
                                selectedEspacio = espacio
                                isMenuExpanded2 = false
                            },
                            text = {
                                Text(text = nombreCompletoEspacio)
                            },
                            interactionSource = remember { MutableInteractionSource() } // Objeto MutableInteractionSource
                        )
                    }
                }

                if(selectedEspacio!=null){
                    idEspacio2= selectedEspacio!!.id!!
                }else{
                    idEspacio2= idEspacioPadre
                }

                //precio
                var precio2 by remember { mutableStateOf(precio) }
                OutlinedTextField(
                    value = precio2.toString(), // Convertir el Double a String
                    onValueChange = { precio2 = it.toDoubleOrNull() ?: 0.0 }, // Convertimos el String de nuevo a Double
                    label = { Text("Precio") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text =errorPrecio,
                    fontSize = 12.sp,
                    color = Color.Red
                )
                // MARCA
                var descripcion2 by remember { mutableStateOf(descripcion) }
                OutlinedTextField(
                    value = descripcion2,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { descripcion2 = it },
                    label = { Text("descripcion") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
                Text(
                    text = errorDescripcion,
                    fontSize = 12.sp,
                    color = Color.Red
                )

                // Campo de texto para Código Postal
                var datosValidos:Boolean = false
                datosValidos = ValidaDatosObjeto2(nombre2,color2,marca2,cantidad2,precio2,descripcion2)
                val objetoAct = Objeto(objetoId,nombre2,null,color2,idCategoria2,marca2,cantidad2,precio2,descripcion2,idEspacio2!!)

                Button(
                    onClick = {
                        if (datosValidos) {
                            errorNombreObjeto= ""
                            errorColor = ""
                            errorDescripcion =""
                            errorMarca = ""
                            errorCantidad = ""
                            errorPrecio = ""
                            viewModel.actualizarObjeto(objetoAct)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = CutCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFE6969),
                        contentColor = Color.Black,
                        disabledContainerColor = Color(0xFF81807F),

                        ),
                    content = {
                        Text(text = "ACTUALIZAR OBJETO", fontSize = 16.sp)
                    }
                )
                mensaje = respuesta?.mensaje ?: "";
                if (mensaje.isNotEmpty()) {
                    StartDialogAct4(navController, mensaje, ObjetosViewModel()) { showDialog2 = false }

                }
                println("****eL OBJETO ID ES " + objetoId)
                val idObjetoPasar=objetoId
                println(idObjetoPasar)

                //buscamos qr asociado
                val qrAsociado = listaQRs?.find { it.idObjeto == idObjetoPasar }
                val tieneQRAsociado = qrAsociado != null

                Button(
                    onClick = {
                        val id = qrAsociado?.id
                        if (tieneQRAsociado) { // si tiene qr asociado podemos vero
                            // Navegar a la pantalla de mostrar QR
                            navController.navigate(AppScreens.ActualizarQr.route +  "${id}")
                        } else {
                            // si no tiene QR asociado lo podemos generar
                            navController.navigate(AppScreens.NuevoQrObjeto.route + "/${nombreObjeto}/${idObjetoPasar}")
                        }
                    },
                            // Mostrar el diálogo al hacer clic en el botón
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally),
                shape = CutCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF130521),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF81807F),

                    ),
                content = {
                    Text(text = if (tieneQRAsociado) "MOSTRAR QR" else "GENERAR QR",
                        fontSize = 16.sp)
                }
                )
            }

        }
    }}
}

@Composable
fun ValidaDatosObjeto2 (nombreObjeto:String,color:String,marca:String,cantidadInt: Int,precioDouble:Double,descripcion:String): Boolean {
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
fun flattenSpaces(spaces: List<Espacio>, parentId: Long? = null, depth: Int = 0): List<Pair<Espacio, Int>> {
    val result = mutableListOf<Pair<Espacio, Int>>()
    spaces.filter { it.idEspacioPadre == parentId }.forEach { space ->
        result.add(Pair(space, depth))
        result.addAll(flattenSpaces(spaces, space.id, depth + 1))
    }
    return result
}

@Composable
fun StartDialogAct4(navController: NavController, mensaje: String, viewModel: ObjetosViewModel, onDismiss: () -> Unit) {
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
fun MenuInicioPreview12() {
    val navController = rememberNavController()
    ActualizarObjeto(navController = navController,5,5,4)
}