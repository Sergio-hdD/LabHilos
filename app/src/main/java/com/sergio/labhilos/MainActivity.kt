package com.sergio.labhilos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var tvSonando : TextView
    lateinit var tvDescargando : TextView
    lateinit var btnSonar : Button
    lateinit var btnDescargar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVariables()
        var numeroCancion = 1
        var numeroDescarga = 1

        btnSonar.setOnClickListener {
            //Thread.sleep(10000) //que duerma (espere) 10000 milisegundos, Si hago esto en intentó hacer más de una cosa a la vez, cómo dar muchos clicks daría error
            tvSonando.setText("Suena canción número $numeroCancion")
            numeroCancion++
        }

        btnDescargar.setOnClickListener {
            simularDescargaCorrutina(numeroDescarga)
            numeroDescarga++
        }

    }

    private fun inicializarVariables() {
        tvSonando = findViewById(R.id.tvSonando)
        tvDescargando = findViewById(R.id.tvDescargando)
        btnSonar = findViewById(R.id.btnSonar)
        btnDescargar = findViewById(R.id.btnDescargar)
    }

    fun simularDescarga(numeroDescarga : Int){ //Simulamos que por cada vez que presionamos el botón "iniciar descarga" inicia una descarga (en otro hilo) que tarda 5 minutos
        //creo un nuevo hilo, pasandole una funcion lambda
        val hilo = Thread(Runnable {
            Thread.sleep(5000) //que espere 5 segundos
            runOnUiThread(Runnable {//Llamo al hilo principal para poder mostrar lo que se ejecuta en él hilo secundaria ya que sino no se mostraría
                tvDescargando.setText("Descarga de canción número $numeroDescarga finalizada")
            })
        })
        hilo.start() //Inicio el hilo
    }

    fun simularDescargaCorrutina(numeroDescarga: Int){
        //Uso "lifecycleScope" para que la corrutina muera al morir el activity
        lifecycleScope.launch{
            tvDescargando.setText("Inicio de descarga de canción número $numeroDescarga") //Apenas inicie la corrutina comenzaría la descarga
            withContext(Dispatchers.IO) {
                Thread.sleep(5000) //que espere 5 segundos
            }
            tvDescargando.setText("Descarga de canción número $numeroDescarga finalizada")
        }
        //NOTA: Lo que hace la corrutina en realidad es acceder más fácilmente al hilo
    }

}