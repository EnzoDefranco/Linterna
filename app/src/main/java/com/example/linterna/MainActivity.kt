package com.example.linterna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi

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
            vibrar(this,500)
        }


        // Configurar el botón y su evento de clic
        apagar.setOnClickListener {
            toggleFlashlight()
            MostrarBoton()
            vibrar(this,500)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun vibrar(context: Context, duracion: Long) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Comprobar si la vibración es compatible
        if (vibrator.hasVibrator()) {
            // Obtener el patrón de vibración
            val pattern = VibrationEffect.createOneShot(duracion, VibrationEffect.DEFAULT_AMPLITUDE)

            // Vibrar con el patrón definido
            vibrator.vibrate(pattern)
        }
    }
}
