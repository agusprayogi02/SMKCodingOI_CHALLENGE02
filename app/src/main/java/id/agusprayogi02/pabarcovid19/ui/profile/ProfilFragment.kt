package id.agusprayogi02.pabarcovid19.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.ui.auth.LoginActivity
import id.agusprayogi02.pabarcovid19.util.tampilToast
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
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser!!.uid.isNotEmpty()) {
            tampilToast(context!!, SessionData["UserData"]!!)
            auth.currentUser?.let {
                for (profile in it.providerData){
                    name_profil.text = profile.displayName
                    Glide.with(context!!).load(profile.photoUrl).into(img_profil)
                    email_profile.text = profile.email
                    no_phone.text = profile.phoneNumber
                    user_id.text = profile.uid
                }
            }
        }

        sign_out.setOnClickListener {
            auth.signOut()
            if (auth.currentUser == null) {
                val i = Intent(context, LoginActivity::class.java)
                startActivity(i)
            }
        }
    }
}
