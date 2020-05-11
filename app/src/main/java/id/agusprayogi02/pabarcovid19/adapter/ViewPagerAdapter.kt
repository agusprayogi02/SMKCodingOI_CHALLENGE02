package id.agusprayogi02.pabarcovid19.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import id.agusprayogi02.pabarcovid19.ui.check.CheckUpFragment
import id.agusprayogi02.pabarcovid19.ui.home.HomeFragment
import id.agusprayogi02.pabarcovid19.ui.news.NewsFragment
import id.agusprayogi02.pabarcovid19.ui.profile.ProfilFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val JML_Menu = 4

    override fun getItemCount(): Int {
        return JML_Menu
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return HomeFragment()
            }
            1 -> {
                return CheckUpFragment()
            }
            2 -> {
                return NewsFragment()
            }
            3 -> {
                return ProfilFragment()
            }
            else -> {
                return HomeFragment()
            }
        }
    }
}