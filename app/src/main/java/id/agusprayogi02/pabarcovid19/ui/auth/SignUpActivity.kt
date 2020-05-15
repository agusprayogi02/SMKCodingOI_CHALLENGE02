package id.agusprayogi02.pabarcovid19.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.thecode.aestheticdialogs.AestheticDialog
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.item.Users
import id.agusprayogi02.pabarcovid19.util.CustomProgressBar
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val mRef = FirebaseDatabase.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val progressBar = CustomProgressBar()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        batal_signUp.setOnClickListener {
            finish()
        }
        btn_signUP.setOnClickListener {
            signUp()
        }
        setSpinner()
    }

    private fun signUp() {
        val error = "Harus di Isi !!"
        val email = email_signUP.text!!.toString()
        val pass = pass_signUP.text!!.toString()

        when {
            full_name_signUP.text!!.isEmpty() -> {
                full_name_signUP.error = error
            }
            email_signUP.text!!.isEmpty() -> {
                email_signUP.error = error
            }
            pass_signUP.length() < 6-> {
                pass_signUP.error = "Password miniman 6 Karakter"
            }
            phone_signUP.text!!.isEmpty() -> {
                phone_signUP.error = error
            }
            umur_signUP.text!!.isEmpty() -> {
                umur_signUP.error = error
            }
            jk_signUp.selectedItem.toString().equals("Pilih Jenis Kelamin", true) -> {
                tampilToast(this, "Harus Memilih Jenis Kelamin")
            }
            alamat_signUP.text!!.isEmpty() -> {
                alamat_signUP.error = error
            }
            else -> {
                progressBar.show(this, "Tunggu Sebentar ...")
                mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val uid = mAuth.currentUser!!.uid
                            tampilToast(this, "Berhasil Membuat Akun")
                            saveData(uid)
                        } else tampilToast(this, "error: " + it.result.toString())
                    }
                    .addOnFailureListener {
                        AestheticDialog.showToaster(
                            this,
                            "Error Make User",
                            it.message,
                            AestheticDialog.ERROR
                        )
                    }
            }
        }
    }

    private fun saveData(uid: String) {
        val db = mRef.getReference("Users/$uid")
        val name = full_name_signUP.text.toString()
        val phone = phone_signUP.text.toString()
        val user = Users(
            uid,
            name,
            email_signUP.text.toString(),
            pass_signUP.text.toString(),
            phone,
            umur_signUP.text.toString(),
            jk_signUp.selectedItem.toString(),
            alamat_signUP.text.toString(),
            "Not"
        )
        db.setValue(user).addOnCompleteListener {
            if (it.isSuccessful) {
                val i = Intent(this, LoginActivity::class.java)
                progressBar.dialog!!.dismiss()
                startActivity(i)
                finish()
            } else tampilToast(this, it.result.toString())
        }
            .addOnFailureListener {
                AestheticDialog.showToaster(this, "Error Upload Data", it.message, AestheticDialog.ERROR)
            }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenis_kl,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        jk_signUp.adapter = adapter
    }
}
