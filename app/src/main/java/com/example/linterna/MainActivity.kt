package com.example.linterna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager

import android.view.View

import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    private lateinit var cameraManager: CameraManager
    private lateinit var cameraId: String
    private var isFlashOn: Boolean = false
    private lateinit var apagar: ImageView
    private lateinit var prendido: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar las vistas después de inflar el diseño de la actividad
        apagar = findViewById<ImageView>(R.id.apagar)
        prendido = findViewById<ImageView>(R.id.encender)

        // Obtener el servicio del administrador de la cámara
        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try {
            cameraId = cameraManager.cameraIdList[0] // Obtener el ID de la cámara trasera
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }

        MostrarBoton()



        prendido.setOnClickListener {
            toggleFlashlight()
            MostrarBoton()
        }


        // Configurar el botón y su evento de clic
        apagar.setOnClickListener {
            toggleFlashlight()
            MostrarBoton()
        }
    }

    fun MostrarBoton() {
        if (isFlashOn) {
            prendido.visibility = View.VISIBLE
            apagar.visibility=View.GONE
        }
        else {
            prendido.visibility = View.GONE
            apagar.visibility=View.VISIBLE
        }
    }

    // Método para alternar el estado del flash del dispositivo.
    fun toggleFlashlight() {
        try {
            cameraManager.setTorchMode(cameraId, !isFlashOn) // Cambia el estado del flash
            isFlashOn = !isFlashOn
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }
}
