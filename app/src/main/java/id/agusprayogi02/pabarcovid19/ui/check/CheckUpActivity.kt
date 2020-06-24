package id.agusprayogi02.pabarcovid19.ui.check

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.PeriksaModel
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.util.getRandomString
import id.agusprayogi02.pabarcovid19.util.tampilToast
import id.agusprayogi02.pabarcovid19.viewmodel.PeriksaViewModel
import kotlinx.android.synthetic.main.activity_check_up.*
import java.util.*

class CheckUpActivity : AppCompatActivity() {

    private var nilai = 0
    private var jawab1 = 0
    private var jawab2 = 0
    private var jawab3 = 0
    private var jawab4 = 0
    private var jawab5 = 0
    private var jawab6 = 0

    lateinit var ref: DatabaseReference
    private val viewModel by viewModels<PeriksaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_up)
        check_batal.setOnClickListener {
            finish()
        }

        viewModel.init(baseContext)
        ref = FirebaseDatabase.getInstance().reference

        nilai = 0
        soal_1.setOnCheckedChangeListener { group, checkedId ->
            jawab1 = 0
            jawab1 += when (checkedId) {
                R.id.soal_1a -> 2
                else -> 0
            }
        }

        soal_2.setOnCheckedChangeListener { group, checkedId ->
            jawab2 = 0
            jawab2 += when (checkedId) {
                R.id.soal_2a -> 2
                else -> 0
            }
        }

        soal_3.setOnCheckedChangeListener { group, checkedId ->
            jawab3 = 0
            jawab3 += when (checkedId) {
                R.id.soal_3a -> 1
                else -> 0
            }
        }

        soal_4.setOnCheckedChangeListener { group, checkedId ->
            jawab4 = 0
            jawab4 += when (checkedId) {
                R.id.soal_4a -> 5
                else -> 0
            }
        }

        soal_5.setOnCheckedChangeListener { group, checkedId ->
            jawab5 = 0
            jawab5 += when (checkedId) {
                R.id.soal_5a -> 5
                else -> 0
            }
        }

        soal_6.setOnCheckedChangeListener { group, checkedId ->
            jawab6 = 0
            jawab6 += when (checkedId) {
                R.id.soal_6a -> 5
                else -> 0
            }
        }

        check_kirim.setOnClickListener {
            val soal1 = soal_1.checkedRadioButtonId
            val soal2 = soal_2.checkedRadioButtonId
            val soal3 = soal_3.checkedRadioButtonId
            val soal4 = soal_4.checkedRadioButtonId
            val soal5 = soal_5.checkedRadioButtonId
            val soal6 = soal_6.checkedRadioButtonId
            if (soal1 == -1 || soal2 == -1 || soal3 == -1 || soal4 == -1 || soal5 == -1 || soal6 == -1) {
                tampilToast(this, "Harus diIsi Semua!!")
            } else {
                nilai = jawab1 + jawab2 + jawab3 + jawab4 + jawab5 + jawab6
                val jawab: String = if (nilai == 0) {
                    "Sehat"
                } else if (nilai == 1) {
                    "Sehat"
                } else if (nilai in 2..4) {
                    "Butuh diPeriksa"
                } else {
                    "Butuh diRawat"
                }
                val tgl = Date()
                val uid = SessionData["UserData"]
                val data =
                    PeriksaModel(getRandomString(9999), nilai.toString(), jawab, tgl.toString())
                ref.child(uid!!).child("History").push().setValue(data).addOnCompleteListener {
                    tampilToast(this, "Data Berhasil Disimpan")
                    if (it.isSuccessful) {
                        viewModel.addData(data)
                    }
                }
                finish()
            }
        }

    }
}