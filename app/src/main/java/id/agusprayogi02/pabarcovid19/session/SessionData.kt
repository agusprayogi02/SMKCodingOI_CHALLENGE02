package id.agusprayogi02.pabarcovid19.session

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.firebase.auth.FirebaseUser

object SessionData {
    private var prefs: SharedPreferences? = null

    fun session(cntx: Context?) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx)
    }

    operator fun set(key: String?, value: String?) {
        prefs!!.edit().putString(key, value).apply()
    }

    operator fun get(key: String?): String? {
        return prefs!!.getString(key, "indonesia")
    }
}