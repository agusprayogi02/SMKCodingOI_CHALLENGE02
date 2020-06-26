package id.agusprayogi02.pabarcovid19.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.ui.auth.LoginActivity
import id.agusprayogi02.pabarcovid19.util.dismissLoading
import id.agusprayogi02.pabarcovid19.util.showLoading
import id.agusprayogi02.pabarcovid19.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.fragment_profil.*

class ProfilFragment : Fragment() {

    private val mRef = FirebaseDatabase.getInstance()
    private val viewModel by viewModels<UsersViewModel>()
    lateinit var users: ArrayList<UsersModel>

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
            startActivity(Intent(context, AboutMeActivity::class.java))
        }

        btn_change.setOnClickListener {
            startActivity(Intent(context, UpdateProfileActivity::class.java))
        }
        viewModel.init(requireContext())
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser!!.uid.isNotEmpty()) {
            showLoading(requireContext(), swipe_profil)
            val user = auth.currentUser
            val uid = user?.uid ?: SessionData["UserData"]
            getData(uid)
            viewModel.allUsers.observe(viewLifecycleOwner, Observer { usr ->
                usr.let {
                    for (h in it) {
                        if (h.uid == uid) {
                            name_profil.text = h.name
                            no_phone.text = h.phone
                            if (!h.foto.equals("Not", true)) {
                                Glide.with(requireContext()).load(h.foto).into(img_profil)
                            }
                            user_id.text = h.uid
                            email_profile.text = h.email
                            age_profile.text = h.umur + " Tahun"
                            gender_profil.text = h.Jk
                            address_profile.text = h.alamat
                        }
                    }
                }
            })
        }

        sign_out.setOnClickListener {
            auth.signOut()
            if (auth.currentUser == null) {
                val i = Intent(context, LoginActivity::class.java)
                requireActivity().finish()
                startActivity(i)
            }
        }
    }

    private fun getData(uid: String?) {
        mRef.getReference("Users").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                users = ArrayList()
                if (p0.exists()) {
                    for (h in p0.children) {
                        val user: UsersModel? = h.getValue(UsersModel::class.java)
                        users.add(user!!)
                    }
                    viewModel.insertAll(users)
                }
                dismissLoading(swipe_profil)
            }

        })
    }
}
