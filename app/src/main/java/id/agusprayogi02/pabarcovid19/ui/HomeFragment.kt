package id.agusprayogi02.pabarcovid19.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.agusprayogi02.pabarcovid19.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImage()
    }

    private fun initImage() {
        val width = DisplayMetrics().widthPixels
        val h = (width / 1.9).toInt()
        Glide.with(this).load("https://covid19.mathdro.id/api/og")
            .apply(RequestOptions().override(width, h))
            .into(image_global_covid)
    }
}
