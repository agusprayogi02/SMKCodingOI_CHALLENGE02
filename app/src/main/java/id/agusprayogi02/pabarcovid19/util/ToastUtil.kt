package id.agusprayogi02.pabarcovid19.util

import android.content.Context
import android.widget.Toast

fun tampilToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}