package id.agusprayogi02.pabarcovid19.ui.check

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.util.tampilToast
import kotlinx.android.synthetic.main.activity_check_up.*

class CheckUpActivity : AppCompatActivity() {

    private var nilai = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_up)
        check_batal.setOnClickListener {
            finish()
        }
        soal_1.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.soal_1a -> {
                    tampilToast(this, "1")
                }
                R.id.soal_1b -> {
                    tampilToast(this, "0")
                }
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
                +1
            }
        }

    }
}