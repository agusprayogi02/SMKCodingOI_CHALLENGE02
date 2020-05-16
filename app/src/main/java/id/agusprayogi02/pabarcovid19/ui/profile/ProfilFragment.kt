package id.agusprayogi02.pabarcovid19.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.item.Users
import id.agusprayogi02.pabarcovid19.ui.auth.LoginActivity
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance()

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

        about_me.setOnClickListener {
            startActivity(Intent(context,AboutMeActivity::class.java))
        }
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser!!.uid.isNotEmpty()) {
            showLoading(context!!, swipe_profil)
            val user = auth.currentUser
            val uid = user!!.uid

            if (user.displayName!!.isNotEmpty()) {
                name_profil.text = user.displayName
                Glide.with(context!!).load(user.photoUrl).into(img_profil)
                user_id.text = user.uid
                email_profile.text = user.email
                no_phone.text = user.phoneNumber
                dismissLoading(swipe_profil)
            } else {
                mRef.getReference("Users").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()) {
                            for (h in p0.children) {
                                val users = h.getValue(Users::class.java)
                                if (users!!.uid == uid) {
                                    name_profil.text = users.name
                                    no_phone.text = users.phone
                                    if (!users.foto.equals("Not", true)) {
                                        Glide.with(context!!).load(users.foto).into(img_profil)
                                    }
                                    user_id.text = users.uid
                                    email_profile.text = users.email
                                    age_profile.text = users.umur + " Tahun"
                                    gender_profil.text = users.Jk
                                    address_profile.text = users.alamat
                                }
                            }
                        }
                        dismissLoading(swipe_profil)
                    }

                })
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
