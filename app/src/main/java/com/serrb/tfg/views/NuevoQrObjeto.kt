package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.viewmodel.ObjetosViewModel
import com.serrb.tfg.viewmodel.QRViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoQrObjeto(navController: NavHostController,
                  viewModel: QRViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),nombreQR:String?,idObjetoPasar:Long?,)
{
    val respuestaId by viewModel.respuestaId.observeAsState()
    var respuestaId2:Long?=0;

    println(" A VEEEEEEER " + idObjetoPasar)

    // DEFINIMOS CAMPOS DEL QR
    var propietario by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var emailCliente by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var recompensa by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var id2:Long?=0;
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
    var errorNombreQr by remember { mutableStateOf("") }
    var errorPropietario by remember { mutableStateOf("") }
    var errorDescripcion by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf("") }
    var errorTelefono by remember { mutableStateOf("") }
    var errorDireccion by remember { mutableStateOf("") }
    var errorRecompensa by remember { mutableStateOf("") }

    //COMPOSABLES

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
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = "hola",
                    Modifier
                        .width(90.dp).size(20.dp)
                )
                Text(
                    text = "GENERAR QR",
                    fontFamily = outfit,
                    letterSpacing = 0.05.em,
                    color = Color.White,
                    fontSize =20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier=Modifier.padding(top=5.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_qr_code_scanner_24),
                    contentDescription = "hola",
                    Modifier
                        .width(90.dp).size(20.dp).padding(end=5.dp)
                )
            }
            // Agregar la imagen de fondo
        }
        Box(modifier=Modifier.weight(5f).background(Color.White)){


        LazyColumn(
            modifier = Modifier
                .background(color = Color.White)
                .padding(end = 20.dp, start = 20.dp, top = 10.dp, bottom = 10.dp)
        ) {
            // Mostrar la imagen obtenida en un Image
            val bitmapValue = imageBitmap?.asImageBitmap()
            if (bitmapValue != null) {
                item {
                    Image(
                        bitmap = bitmapValue,
                        contentDescription = "Código QR",
                        modifier = Modifier
                            .size(200.dp)
                            .height(200.dp)
                    )
                }
            }

            // CABECERA
            item{
                Text(
                    text="Por favor, indique los datos a codificar en el QR",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            //CAMPO NOMBRE DEL QR
            item{
                OutlinedTextField(
                    value = nombreQR!!,
                    onValueChange = { nombreQR },
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        Text("Nombre del objeto*",color = Color.Black)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .focusRequester(focusRequester)
                        .padding(0.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),
                        enabled = false
                )
                //ERROR*
                Text(
                    text = errorNombreQr,
                    fontSize = 12.sp,
                    color = Color.Red
                ) }

            //CAMPO PROPIETARIO
            item{ OutlinedTextField(
                value = propietario,
                onValueChange = { propietario = it },
                shape = RoundedCornerShape(8.dp),
                label = {
                    Text("Propietario*",color = Color.Black)
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
                //ERROR PROPIETARIO
                Text(
                    text = errorPropietario,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CAMPO DESCRIPCION
            item{
                OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                    shape = RoundedCornerShape(8.dp),
                label = {
                    Text("Descripcion*",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(color = Color.White)
                    .focusRequester(focusRequester3),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
                //ERROR*
                Text(
                    text = errorDescripcion,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //EMAIL
            item{
                OutlinedTextField(
                value = emailCliente,
                onValueChange = { emailCliente = it }, shape = RoundedCornerShape(8.dp),
                label = {
                    Text("Email de contacto*",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester4),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
                )
                //ERROR EMAIL
                Text(
                    text = errorEmail,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CAMPO TELEFONO
            item{
                OutlinedTextField(
                value = telefono, shape = RoundedCornerShape(8.dp),
                onValueChange = { telefono = it },
                label = {
                    Text("Telefono de contacto (opcional)",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester5),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
                )
                //ERROR TELEFONO
                Text(
                    text = errorTelefono,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CAMPO DIRECCION
            item{
                OutlinedTextField(
                value = direccion,  shape = RoundedCornerShape(8.dp),
                onValueChange = { direccion = it },
                label = {
                    Text("Direccion (opcional)",color = Color.Black,)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .focusRequester(focusRequester6),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
                )
                //ERROR DIRECCION
                Text(
                    text = errorDireccion,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            //CAMPO RECOMPENSA
            item{
                OutlinedTextField(
                value = recompensa,  shape = RoundedCornerShape(8.dp),
                onValueChange = { recompensa = it },
                label = {
                    Text("Recompensa (Opcional)",color = Color.Black,)
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
                    text =errorRecompensa,
                    fontSize = 12.sp,
                    color = Color.Red
                )
            }

            val recompensaDouble = recompensa.toDoubleOrNull() ?: 0.0

            // TOMAMOS LOS DATOS DE LOS CAMPOS Y GENERAMOS EL OBJETO
            val QR = QR(nombreQR!!,propietario,descripcion,emailCliente,telefono,direccion,recompensaDouble,idObjetoPasar,null)

            //DIALOGO DE CONFIRMACIÓN, SE GENERARÁ QR.
            item {
                var showDialog by remember { mutableStateOf(false) }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Confirmación") },
                        text = { Text(text = "¿Desea guardar este QR?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    // Llamar a la función generateQR del ViewModel cuando se hace clic en el botón
                                    viewModel.viewModelScope.launch {
                                        try {
                                            //1.guardamos qr
                                            println("holi holi holi holi")
                                            viewModel.guardarQR(QR);
                                            println("el id que estamos viendo es " + respuestaId)
                                            respuestaId2 = respuestaId
                                            println("y id2 es " + respuestaId2)
                                            //val bitmap = viewModel.generateQR(id2)
                                            //imageBitmap = bitmap
                                            navController.navigate(AppScreens.QrGenerado.route+ "${respuestaId2}")
                                        } catch (e: Exception) {
                                            // SI NO HAY CONEXION AL MICROSERVICIO LO GENERA LOCALMENTE...**REVISAR
                                            Log.e("Inicio", "Error al generar el código QR", e)
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

                // VALIDAMOS DATOS AL PULSAR EN EL BOTON

                var datosValidos: Boolean = false
                datosValidos = ValidaDatosCliente(nombreQR,propietario,descripcion,emailCliente,telefono,direccion,recompensaDouble)

                //AL PULSAR EN EL BOTON, SI LOS DATOS SON VALIDOS, MUESTRA DIALOG

                Button(
                    onClick = {
                        if (datosValidos) {
                            showDialog = true
                            errorNombreQr= ""
                            errorPropietario = ""
                            errorDescripcion =""
                            errorEmail = ""
                            errorTelefono = ""
                            errorDireccion = ""
                            errorRecompensa = ""

                        }else{
                            // si no son validos, muestra errores
                            errorNombreQr= viewModel.nombreErrMsg.value
                            errorPropietario = viewModel.propietarioErrMsg.value
                            errorDescripcion =viewModel.descripcionErrMsg.value
                            errorEmail = viewModel.emailErrMsg.value
                            errorTelefono = viewModel.telefonoErrMsg.value
                            errorDireccion = viewModel.direccionErrMsg.value
                            errorRecompensa = viewModel.recompensaErrMsg.value
                        }
                    }, // Mostrar el diálogo al hacer clic en el botón
                    modifier = Modifier
                        .fillMaxWidth(),
                         shape = CutCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFE6969),
                        contentColor = Color.Black,
                        disabledContainerColor = Color(0xFF81807F),

                        ),
                    content = {
                        Text(text = "GUARDAR QR", fontSize = 16.sp)
                    }
                )
            }
        }
    }
}}

@Composable
fun ValidaDatosClienteObj (nombreQR:String,propietario:String,descripcion:String,emailCliente:String,telefono:String,direccion:String,recompensaDouble:Double): Boolean {
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
        if(nombreQR.length < 20 && nombreQR.matches("[a-zA-Z0-9\\s]+".toRegex())) {
            nombreIsValido = true
        }else{
            viewModel.nombreErrMsg.value = "Nombre menor a 20 caracteres alfanuméricos"
        }
    }  else {
        viewModel.nombreErrMsg.value = "El campo nombre no puede estar vacío"
    }

    //Validamos que propietario debe ser menor a 20 caracteres alfanuméricos
    if (propietario.isNotEmpty()){
        if(nombreQR.length < 20 && nombreQR.matches("[a-zA-Z0-9\\s]+".toRegex())) {
            propietarioIsValido = true
        } else{
            viewModel.propietarioErrMsg.value = "Propietario menor a 30 caracteres alfanuméricos"
        }
    } else {
        viewModel.propietarioErrMsg.value = "El campo propietario no puede estar vacío"
    }

    //Validamos que descripcion debe ser menor a 20 caracteres alfanuméricos
    if (descripcion.isNotEmpty()){
        if( descripcion.length < 250 && descripcion.matches("[a-zA-Z0-9\\s]+".toRegex())){
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
    if (telefono.length==0 || telefono.length == 9 && telefono.matches("[0-9]+".toRegex())) {
        telfIsValido = true
    } else {
        viewModel.telefonoErrMsg.value = "9 números"
    }

    //Validamos que dirección debe ser menor a 50 caracteres alfanuméricos
    if (direccion.length==0||direccion.length < 50 && direccion.matches("[a-zA-Z0-9\\s]+".toRegex())) {
        direccionIsValido = true
    } else {
        viewModel.direccionErrMsg.value = "Dirección menor a 50 caracteres alfanuméricos"
    }

    val recompensaStr = recompensaDouble.toString()
    val index = recompensaStr.indexOf('.') //verificamos si hay punto
    val digitsAfterDecimal = if (index == -1) {
        0
    } else {
        recompensaStr.length - index - 1
    }

    if (digitsAfterDecimal <= 5) {
        recompensaIsValido = true
    } else {
        viewModel.recompensaErrMsg.value = "La recompensa debe tener un máximo de 5 dígitos"
    }

    // SI TODOS LOS CAMPOS SON CORRECTOS, DEVUELVE TRUE.
    if(nombreIsValido && propietarioIsValido && descripcionIsValido && emailIsValido && telfIsValido && direccionIsValido && recompensaIsValido){
        return true
    }else{
        return false
    }

}
