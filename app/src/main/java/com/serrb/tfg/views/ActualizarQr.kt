package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import coil.compose.rememberImagePainter
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.navigation.AppNavigation
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.viewmodel.QRViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarQr(navController: NavHostController, id: Long?) {
    val QRViewModel: QRViewModel = viewModel()
    val respuesta by QRViewModel.respuesta.observeAsState()

    var showDialog2 by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    var isComLoading by remember { mutableStateOf(true) }

    var nombreQR by remember { mutableStateOf("") }
    var propietario by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var emailCliente by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var recompensa: Double = 0.0
    var idObjeto: Long? = 0
    var image by remember { mutableStateOf("") }

    //DEFINIMOS VARIABLES PARA ERRORES
    var errorNombreQr by remember { mutableStateOf("") }
    var errorPropietario by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf("") }
    var errorTelefono by remember { mutableStateOf("") }
    var errorDireccion by remember { mutableStateOf("") }
    var errorRecompensa by remember { mutableStateOf("") }

    //Busca los datos del Cliente en la Base de Datos
    LaunchedEffect(Unit) {
        try {
            println("el id que me has pasado es " + id)
            QRViewModel.buscarQrById(id)
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
        val QR by QRViewModel.qrByid.observeAsState()

        if (QR != null) {
            // Asignamos los datos del QR a las variables de estado del formulario
            QR?.let { QR ->
                nombreQR = QR.nombreQR ?: ""
                propietario = QR.propietario ?: ""
                descripcion = QR.descripcion ?: ""
                telefono = QR.telefono ?: ""
                emailCliente = QR.emailCliente ?: ""
                direccion = QR.direccion ?: ""
                recompensa = QR.recompensa
                image = QR.qrImagePath ?: ""
                idObjeto = QR.idObjeto
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
                        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                        contentDescription = "hola",
                        Modifier
                            .width(90.dp)
                            .size(20.dp)
                            )
                    Text(
                        text = "ACTUALIZAR QR",
                        fontFamily = outfit,
                        letterSpacing = 0.05.em,
                        color = Color.White,
                        fontSize =20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier=Modifier.padding(top=5.dp).clickable {
                            navController.navigate(AppScreens.MenuQr.route)
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                        contentDescription = "hola",
                        Modifier
                            .width(90.dp)
                            .size(20.dp)
                            .padding(end = 5.dp)
                    )
                }
                // Agregar la imagen de fondo
            }
            Box(modifier= Modifier
                .weight(5f)
                .background(Color.White)){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {

                    val painter: Painter = rememberImagePainter(image)
                    // Muestra la imagen dentro de un elemento Image
                    Image(
                        painter = painter,
                        contentDescription = null, // Opcional: proporciona una descripción de contenido
                        modifier = Modifier
                            .weight(1.2f)
                            .size(300.dp)
                            .clickable { navController.navigate(AppScreens.QrDescargar.route + "${id}") }
                    )
                    //ERRORES
                    var errores = errorNombreQr.plus("" + errorPropietario +"" + errorDescripcion + "" + errorEmail +"" + errorTelefono + "" + errorDireccion + "" + errorRecompensa)
                    Text( text = errores,fontSize = 12.sp,color = Color.Red,modifier=Modifier.height(20.dp))


                    /* Campos de texto
                    OutlinedTextField(
                        value = id.toString(),
                        onValueChange = {id },
                        label = { Text("ID") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        singleLine = false,
                        enabled = false,
                    )*/
                    // NOMBRE QR
                    var nombre2 by remember { mutableStateOf(nombreQR) }
                    OutlinedTextField(
                        value = nombre2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = {nombre2=it},
                        label = { Text("NOMBRE") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = false,
                        enabled =true,
                    )
                    //PROPIETARIO
                    var propietario2 by remember { mutableStateOf(propietario) }
                    OutlinedTextField(
                        value = propietario2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = { propietario2 = it },
                        label = { Text("Propietario") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    //DESCRIPCION
                    var descripcion2 by remember { mutableStateOf(descripcion) }
                    OutlinedTextField(
                        value = descripcion2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = { descripcion2 = it },
                        label = { Text("descripcion") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // Campo de texto para el Email
                    var telefono2 by remember { mutableStateOf(telefono) }
                    OutlinedTextField(
                        value = telefono2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = { telefono2 = it },
                        label = { Text("Telefono") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // Campo de texto para el Teléfono
                    var email2 by remember { mutableStateOf(emailCliente) }
                    OutlinedTextField(
                        value = email2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = { email2 = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // Campo de texto para el Dirección
                    var direccion2 by remember { mutableStateOf(direccion) }
                    OutlinedTextField(
                        value = direccion2,  shape = RoundedCornerShape(8.dp),
                        onValueChange = { direccion2 = it },
                        label = { Text("Dirección") },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // Campo de texto para Ciudad

                    var recompensa2 by remember { mutableStateOf(recompensa) }
                    OutlinedTextField(
                        value = recompensa2.toString(), // Convertir el Double a String
                        onValueChange = { recompensa2 = it.toDoubleOrNull() ?: 0.0 }, // Convertimos el String de nuevo a Double
                        label = { Text("Recompensa") },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    )



                    // Campo de texto para Código Postal
                    var datosValidos: Boolean = false
                    datosValidos = ValidaDatosCliente(nombre2,propietario2,descripcion2,email2,telefono2,direccion2,recompensa2)
                    val QrUpdate = QR(id,nombre2,propietario2,descripcion2,email2,telefono2,direccion2,recompensa2,idObjeto,image)
                    Button(
                        onClick = {
                            if (datosValidos) {
                                errorNombreQr= ""
                                errorPropietario = ""
                                errorDescripcion =""
                                errorEmail = ""
                                errorTelefono = ""
                                errorDireccion = ""
                                errorRecompensa = ""
                                QRViewModel.actualizarQR(QrUpdate)
                            }else{
                                // si no son validos, muestra errores
                                errorNombreQr= QRViewModel.nombreErrMsg.value
                                errorPropietario = QRViewModel.propietarioErrMsg.value
                                errorDescripcion =QRViewModel.descripcionErrMsg.value
                                errorEmail = QRViewModel.emailErrMsg.value
                                errorTelefono = QRViewModel.telefonoErrMsg.value
                                errorDireccion = QRViewModel.direccionErrMsg.value
                                errorRecompensa = QRViewModel.recompensaErrMsg.value
                            }//onClick = { showDialog = true },
                        },
                        modifier = Modifier.padding(16.dp),
                        shape = CutCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE91E63),
                            contentColor = Color.White
                        ),
                        content = {
                            Text(text = "ACTUALIZAR QR", fontSize = 16.sp)
                        }
                    )
                    mensaje = respuesta?.mensaje ?: "";
                    if (mensaje.isNotEmpty()) {
                        StartDialogAct(navController, mensaje, QRViewModel) { showDialog2 = false }

                    }
                }
            }
            }
        }
    }
    }


@Composable
fun StartDialogAct(navController: NavController, mensaje: String, viewModel: QRViewModel,onDismiss: () -> Unit) {
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
                        navController.navigate(AppScreens.ListaQr.route)
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

@Composable
fun ValidaDatosQR (nombreQR:String,propietario:String,descripcion:String,emailCliente:String,telefono:String,direccion:String,recompensaDouble:Double): Boolean {
    val viewModel: QRViewModel = viewModel()

    var nombreIsValido: Boolean = false
    viewModel.nombreErrMsg.value = ""

    var propietarioIsValido: Boolean = false
    viewModel.propietarioErrMsg.value = ""

    var descripcionIsValido: Boolean = false
    viewModel.descripcionErrMsg.value = ""

    var emailIsValido: Boolean = false
    viewModel.emailErrMsg.value = ""

    var telfIsValido: Boolean = false
    viewModel.telefonoErrMsg.value = ""

    var direccionIsValido: Boolean = false
    viewModel.direccionErrMsg.value = ""

    var recompensaIsValido: Boolean = false
    viewModel.recompensaErrMsg.value = ""

    println(nombreQR)
    //VALIDACIONES
    //Validamos que nombre debe ser menor a 20 caracteres alfanuméricos
    if (nombreQR.isNotEmpty()){
        if(nombreQR.length < 20 && nombreQR.matches("[a-zA-Z0-9]+".toRegex())) {
            nombreIsValido = true
        }else{
            viewModel.nombreErrMsg.value = "Nombre menor a 20 caracteres alfanuméricos"
        }
    }  else {
        viewModel.nombreErrMsg.value = "El campo nombre no puede estar vacío"
    }

    //Validamos que apellido debe ser menor a 20 caracteres alfanuméricos
    if (propietario.isNotEmpty()){
        if(propietario.length < 30 && propietario.matches("[a-zA-Z0-9]+".toRegex())){
            propietarioIsValido = true
        } else{
            viewModel.propietarioErrMsg.value = "Apellido menor a 30 caracteres alfanuméricos"
        }
    } else {
        viewModel.propietarioErrMsg.value = "El campo propietario no puede estar vacío"
    }

    //Validamos que apellido debe ser menor a 20 caracteres alfanuméricos
    if (descripcion.isNotEmpty()){
        if( descripcion.length < 200 && descripcion.matches("[a-zA-Z0-9]+".toRegex())){
            descripcionIsValido = true
        } else {
            viewModel.descripcionErrMsg.value = "Apellido menor a 30 caracteres alfanuméricos"
        }
    }else {
        viewModel.descripcionErrMsg.value = "El campo descripcion no puede estar vacío"
    }

    //Validamos que email tenga el formato correcto
    if (descripcion.isNotEmpty()){
        if(Patterns.EMAIL_ADDRESS.matcher(emailCliente).matches()){
            emailIsValido = true
        }else {
            viewModel.emailErrMsg.value = "Formato del email no válido"
        }
    } else {
        viewModel.emailErrMsg.value = "El campo email no puede estar vacío"
    }

    //Validamos que el teléfono sea igual a 9 caracteres numéricos
    if (telefono.length == 9 && telefono.matches("[0-9]+".toRegex())) {
        telfIsValido = true
    } else {
        viewModel.telefonoErrMsg.value = "El teléfono debe tener 9 números"
    }

    //Validamos que dirección debe ser menor a 50 caracteres alfanuméricos
    if (direccion.length < 50 && direccion.matches("[a-zA-Z0-9]+".toRegex())) {
        direccionIsValido = true
    } else {
        viewModel.direccionErrMsg.value = "Dirección menor a 50 caracteres alfanuméricos"
    }

    val recompensaStr = recompensaDouble.toString()

    if (recompensaStr.length < 5 ) {
        recompensaIsValido = true
    } else {
        viewModel.recompensaErrMsg.value = "La recompensa debe ser un número de 5 dígitos"
    }

    // SI TODOS LOS CAMPOS SON CORRECTOS, DEVUELVE TRUE.
    if(nombreIsValido && propietarioIsValido && descripcionIsValido && emailIsValido && telfIsValido && direccionIsValido && recompensaIsValido){
        return true
    }else{
        return false
    }

}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview64() {
    val navController = rememberNavController()
    ActualizarQr(navController = navController,7)
}


