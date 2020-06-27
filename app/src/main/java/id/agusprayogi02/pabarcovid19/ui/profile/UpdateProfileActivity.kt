package id.agusprayogi02.pabarcovid19.ui.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.agusprayogi02.pabarcovid19.R
import id.agusprayogi02.pabarcovid19.database.entity.UsersModel
import id.agusprayogi02.pabarcovid19.session.SessionData
import id.agusprayogi02.pabarcovid19.util.getRandomString
import id.agusprayogi02.pabarcovid19.util.tampilToast
import id.agusprayogi02.pabarcovid19.viewmodel.UsersViewModel
import kotlinx.android.synthetic.main.activity_update_profile.*
import java.io.ByteArrayOutputStream
import java.io.IOException

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
    lateinit var storageReference: StorageReference
    private val PICK_IMAGE_REQUEST = 100
    private var filePath: Uri? = null
    lateinit var uid: String
    lateinit var pass: String
    lateinit var pp: String
    var urlDown: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        viewModel.init(baseContext)
        mref = FirebaseDatabase.getInstance().reference
        storageReference = FirebaseStorage.getInstance().reference

        uid = SessionData["UserData"].toString()
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
                        pass = dt.password
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
        val anim = AnimationUtils.loadAnimation(this, R.anim.shake)
        batal_profile.setOnClickListener {
            finish()
        }
        btn_change_image.setOnClickListener {
            it.animation = anim
            launchImage()
        }
        btn_profile.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        val err = "Harus di Isi!!"
        if (full_name_profile.text!!.isEmpty()) {
            full_name_profile.error = err
        } else if (phone_profile.text!!.isEmpty()) {
            phone_profile.error = err
        } else if (umur_profile.text!!.isEmpty()) {
            umur_profile.error = err
        } else if (jk_profile.selectedItem.toString().equals("Pilih Jenis Kelamin", true)) {
            tampilToast(this, "Pilih Jenis Kelamin")
        } else if (alamat_profile.text!!.isEmpty()) {
            alamat_profile.error = err
        } else if (filePath != null) {
            uploadImage()
        } else {
            pushData()
        }
    }

    private fun uploadImage() {
        val rand = getRandomString(12)
        val storRef = storageReference.child("Users/Fotos/$rand")
        image_change.isDrawingCacheEnabled = true
        image_change.buildDrawingCache()
        val bitmap = (image_change.drawable as BitmapDrawable).bitmap
        val resize = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * 0.3).toInt(),
            (bitmap.height * 0.3).toInt(), true
        )
        val baos = ByteArrayOutputStream()
        resize.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val dt = baos.toByteArray()

        storRef.putBytes(dt).addOnFailureListener {
            tampilToast(this, it.message.toString())
        }.addOnSuccessListener {
            tampilToast(this, "Berhasil Upload Foto!")
            it.metadata?.reference!!.downloadUrl.addOnSuccessListener { uri ->
                urlDown = uri.toString()
                pushData()
            }
        }

    }

    private fun pushData() {
        pp = when {
            urlDown.isNullOrEmpty() -> {
                "Not"
            }
            else -> urlDown!!
        }
        val user = UsersModel(
            uid,
            full_name_profile.text.toString(),
            email_profiles.text.toString(),
            pass,
            phone_profile.text.toString(),
            umur_profile.text.toString(),
            jk_profile.selectedItem.toString(),
            alamat_profile.text.toString(),
            pp
        )
        mref.child("Users").child(uid).setValue(user).addOnSuccessListener {
            viewModel.updateData(user)
            finish()
        }
    }

    private fun launchImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data!!.data != null) {
                filePath = data.data!!
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, filePath)
                    image_change.setImageBitmap(bitmap)
                } catch (err: IOException) {
                    err.printStackTrace()
                }
            }
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