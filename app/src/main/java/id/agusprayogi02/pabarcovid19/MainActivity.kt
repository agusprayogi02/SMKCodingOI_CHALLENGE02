package id.agusprayogi02.pabarcovid19

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import id.agusprayogi02.pabarcovid19.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val menuText = arrayOf("Home", "Periksa", "Berita", "Profil")
    val menuIcon = arrayOf(
        R.drawable.ic_home,
        R.drawable.ic_check_up,
        R.drawable.ic_news,
        R.drawable.ic_profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ViewPagerAdapter(this)
        view_pager.adapter = adapter
        TabLayoutMediator(
            TabLayout,
            view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = menuText[position]
                tab.icon = ResourcesCompat.getDrawable(resources, menuIcon[position], null)
            }).attach()
    }
}
