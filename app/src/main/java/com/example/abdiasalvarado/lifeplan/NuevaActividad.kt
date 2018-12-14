package com.example.abdiasalvarado.lifeplan

import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.abdiasalvarado.lifeplan.data.LifePlanDatabase
import com.example.abdiasalvarado.lifeplan.data.Tabla_Actividades
import kotlinx.android.synthetic.main.activity_nueva_actividad.*


class NuevaActividad : AppCompatActivity() {


    private var lifeplanDatabase: LifePlanDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_actividad)

        fabHora.setOnClickListener {
            val timePicker = TimePicker()
            timePicker.show(supportFragmentManager, "SELECTOR HORA")
        }


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            listOf("Ejercicio", "Alimentación", "Académico", "Ocio", "Deportes", "Social", "Otro"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sprCategoria.adapter = adapter

        lifeplanDatabase = LifePlanDatabase.getInstance(this)

        val etiqueta = intent.getStringExtra("etiqueta")
        val hora = intent.getStringExtra("hora")
        val categoria = intent.getStringExtra("categoria")

        if (etiqueta == null || etiqueta == "") {
            btnAgregar.setOnClickListener {
                val actividad = Tabla_Actividades(etEtiqueta.text.toString(), etHora.text.toString() ,sprCategoria.selectedItem.toString())
                lifeplanDatabase?.getTablaActividadesDao()?.insertarActividad(actividad)

//                var hora = "8"
//                var minutos = "10"
//
//                establecerAlarma(etEtiqueta.text.toString(), hora, minutos)
                finish()
            }
        } else {
            val id = intent.getIntExtra("id", 0)
            etEtiqueta.setText(etiqueta)
            etHora.setText(hora)
            btnAgregar.setOnClickListener {
                val actividad = Tabla_Actividades(etEtiqueta.text.toString(), etHora.text.toString(), sprCategoria.selectedItem.toString())
                actividad.id = id
                lifeplanDatabase?.getTablaActividadesDao()?.actualizarActividad(actividad)
//                var hora = "8"
//                var minutos = "10"
//
//                establecerAlarma(etEtiqueta.text.toString(), hora, minutos)
                finish()
            }
        }
    }

    fun establecerAlarma(etiqueta: String, hora: String, minutos: String)
    {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
            .putExtra(AlarmClock.EXTRA_MESSAGE, etiqueta)
            .putExtra(AlarmClock.EXTRA_HOUR, hora)
            .putExtra(AlarmClock.EXTRA_MINUTES, minutos)

        if(intent.resolveActivity(packageManager)!= null){
            Toast.makeText(this, "Alarma a $hora:$minutos", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }
    }




}
