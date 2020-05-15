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
            name_profil.text = auth.currentUser!!.displayName
            email_profile.text = auth.currentUser!!.email
            no_phone.text = auth.currentUser!!.phoneNumber
            user_id.text = auth.currentUser!!.uid
            Glide.with(context!!).load(auth.currentUser!!.photoUrl).into(img_profil)
            auth.currentUser!!.providerData?.let {

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
