package id.agusprayogi02.pabarcovid19.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.ui.auth.LoginActivity
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_out.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            if (auth.currentUser == null) {
                val i = Intent(context, LoginActivity::class.java)
                startActivity(i)
            }
        }
    }
}
