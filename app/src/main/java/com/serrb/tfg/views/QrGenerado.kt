package com.serrb.tfg.views

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.serrb.tfg.R
import com.serrb.tfg.model.QR.QR
import com.serrb.tfg.navigation.AppScreens
import com.serrb.tfg.ui.theme.outfit
import com.serrb.tfg.ui.theme.raleway
import com.serrb.tfg.viewmodel.QRViewModel
import java.io.File
import java.io.FileOutputStream

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrGenerado(navController: NavHostController, id: Long?) {

    val QRViewModel: QRViewModel = viewModel()
    var image by remember { mutableStateOf("") }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isComLoading by remember { mutableStateOf(true) }

    val context = LocalContext.current // Obteniendo el contexto

    LaunchedEffect(Unit) {
        try {
            println("el id que me has pasado es " + id)
            bitmap = QRViewModel.generateQR(id)
        } catch (e: Exception) {
            // Manejar errores
            Log.d(ContentValues.TAG, "Error al cargar el QR: ", e)
            bitmap = QRViewModel.generarQRLocalmente(id)
        } finally {
            isComLoading = false
        }
    }
        Box(
            modifier = Modifier
                .background(color = Color(0xFF130521)).padding(20.dp,75.dp),
        ){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White).padding(20.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("QR GENERADO CORRECTAMENTE",
                fontFamily = raleway,
                letterSpacing = 0.05.em,
                fontWeight = FontWeight.Medium,fontSize = 30.sp, textAlign = TextAlign.Center)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(400.dp)

            ) {
                // Fondo del marco personalizado
                Image(
                    painter = painterResource(id = R.drawable.marco), // Reemplaza con el ID de tu imagen PNG
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(2f).padding(top=0.dp)
                )
                // Contenido (imagen QR)
                bitmap?.let { bitmap ->
                    val painter: Painter = rememberImagePainter(bitmap)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier=Modifier.fillMaxSize(0.55f).padding(bottom=50.dp)
                    )
                }
            }

            Button(
                onClick = {
                    // Verificar si el bitmap no es nulo
                    bitmap?.let { bitmap ->
                        // Utiliza la función para guardar la imagen en la galería
                        saveBitmapToGallery(bitmap, context)
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally).padding(0.dp,15.dp).height(60.dp),
                shape = CutCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFE6969),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFF81807F),
                    )
            ) {
                Text("Descargar QR a galería")
            }
            Button(
                onClick = {
                    // Verificar si el bitmap no es nulo
                    navController.navigate(AppScreens.ListaQr.route)
                },
                modifier = Modifier.fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally).padding(0.dp,10.dp).height(60.dp),
                shape = CutCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF130521),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF130521),
                    )
            ) {
                Text(" Ver mi listado de QR's ")
            }
        }
    }}

private fun saveBitmapToGallery(bitmap: Bitmap, context: Context) {
    // Obtener el directorio de almacenamiento externo público
    val saveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

    // Crear un nombre de archivo único
    val fileName = "QR_${System.currentTimeMillis()}.jpeg"

    // Crear el archivo de salida
    val file = File(saveDir, fileName)

    // Obtener un OutputStream para escribir en el archivo
    val outputStream = FileOutputStream(file)

    // Comprimir el bitmap en formato JPEG y escribirlo en el OutputStream
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

    // Cerrar el OutputStream
    outputStream.close()

    // Escanear el archivo para que aparezca en la galería de medios
    MediaScannerConnection.scanFile(
        context,
        arrayOf(file.absolutePath),
        arrayOf("image/jpeg"),
        null
    )

    // Mostrar un mensaje de éxito al usuario
    Toast.makeText(context, "¡QR guardado en la galería!", Toast.LENGTH_SHORT).show()
}

private fun addFrameToBitmap(qrBitmap: Bitmap, frameBitmap: Bitmap): Bitmap {
    val resultBitmap = Bitmap.createBitmap(frameBitmap.width, frameBitmap.height, frameBitmap.config)
    val canvas = Canvas(resultBitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    canvas.drawBitmap(frameBitmap, 0f, 0f, paint)
    canvas.drawBitmap(qrBitmap, (frameBitmap.width - qrBitmap.width) / 2f, (frameBitmap.height - qrBitmap.height) / 2f, paint)
    return resultBitmap
}

@Preview(showBackground = true)
@Composable
fun MenuInicioPreview10() {
    val navController = rememberNavController()
    QrGenerado(navController = navController,44)
}