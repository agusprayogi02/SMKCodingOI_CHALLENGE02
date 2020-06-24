package id.agusprayogi02.pabarcovid19.util

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.thecode.aestheticdialogs.AestheticDialog

fun tampilToast(context: Context, message: String){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun getRandomString(length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun connnetError(activity: Activity) {
    AestheticDialog.showConnectify(activity,"Tidak Dapat Memuat!!",AestheticDialog.ERROR)
}