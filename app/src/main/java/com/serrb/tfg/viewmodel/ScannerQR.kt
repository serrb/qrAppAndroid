package com.serrb.tfg.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.integration.android.IntentIntegrator

class ScannerQR : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScannerQRContent()
        }
    }

    @Composable
    fun ScannerQRContent() {
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxSize().background(color = Color(0xFF130521)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { initScanner(context) },
                shape = RoundedCornerShape(120.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63),
                    contentColor = Color(0xFF000000)
                ),
                modifier = Modifier
                    .height(220.dp)
                    .width(220.dp)
                    .height(170.dp)
            ) {
                Text(
                    text = "ESCANEAR QR",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.6f)
                )
            }
        }
    }

    // Iniciar la funcionalidad de escaneo
    fun initScanner(context: Context) {
        println("init scanner funcionandooooooooooooooooo")
        val integrator = IntentIntegrator(context as ComponentActivity)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // establecemos que solo queremos escanear QR's
        integrator.setPrompt("Bienvenido")
        integrator.setTorchEnabled(false) //opcion de activar el flash
        integrator.setBeepEnabled(true) //aviso de que lo escanea correctamente
        integrator.initiateScan()
    }

    // Manejar el resultado una vez escaneado el QR
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("onactivity funcionandooooooooooooooooo")
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Error al escanear", Toast.LENGTH_SHORT).show()
                println("no funciona")
            } else {
                handleQRCodeResult(result.contents)
                println("funciona")
            }
        }
    }

    // Función para manejar el resultado del código QR
    private fun handleQRCodeResult(result: String) {
        if (result.startsWith("http://") || result.startsWith("https://")) {
            // Si el resultado es una URL, abrir en el navegador web
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
            startActivity(intent)
        } else {
            // Si el resultado no es una URL, mostrar un mensaje o realizar alguna otra acción
            Toast.makeText(this, "El valor escaneado no es una URL, El valor escaneado es $result", Toast.LENGTH_SHORT).show()
        }
    }
}