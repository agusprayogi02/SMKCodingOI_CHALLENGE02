package id.agusprayogi02.pabarcovid19.ui.profile

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_update_profile.*

class UpdateProfileActivity : AppCompatActivity() {

    val viewModel by viewModels<UsersViewModel>()

    //    var photo = findViewById<ImageView>(R.id.image_change)
//    var name = findViewById<EditText>(R.id.full_name_profile)
//    var email = findViewById<EditText>(R.id.email_profiles)
//    var phone = findViewById<EditText>(R.id.phone_profile)
//    var umur = findViewById<EditText>(R.id.umur_profile)
//    var gender = findViewById<Spinner>(R.id.jk_profile)
//    var alamat = findViewById<EditText>(R.id.alamat_profile)
    lateinit var mref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        viewModel.init(baseContext)
        mref = FirebaseDatabase.getInstance().reference
        val uid = SessionData["UserData"]
        setSpinner()
        clicked()
        viewModel.allUsers.observeForever { list ->
            list.let { data ->
                for (dt in data) {
                    if (dt.uid == uid) {
                        if (!dt.foto.equals("Not", true)) {
                            Glide.with(baseContext).load(dt.foto).into(image_change)
                        }
                        full_name_profile.setText(dt.name)
                        email_profiles.setText(dt.email)
                        phone_profile.setText(dt.phone)
                        umur_profile.setText(dt.umur)
                        if (dt.Jk.equals("Laki-Laki", true)) {
                            jk_profile.setSelection(1)
                        } else if (dt.Jk.equals("Perempuan", true)) {
                            jk_profile.setSelection(2)
                        } else if (dt.Jk.equals("Lainnya", true)) {
                            jk_profile.setSelection(3)
                        } else {
                            jk_profile.setSelection(0)
                        }
                        alamat_profile.setText(dt.alamat)
                    }
                }
            }
        }
    }

    private fun clicked() {
        batal_profile.setOnClickListener {
            finish()
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kl,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jk_profile.adapter = adapter
    }
}