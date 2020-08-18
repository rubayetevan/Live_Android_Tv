package live.ebox.tv.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import live.ebox.tv.base.MovieCategory
import live.ebox.tv.ui.fragment.MovieFragment
import live.ebox.tv.ui.fragment.TvChannelFragment


class ViewPagerAdapter(fm: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TvChannelFragment()
            }
            1 -> {
                MovieFragment(MovieCategory.HINDI)
            }
            2 -> {
                MovieFragment(MovieCategory.ENGLISH)
            }
            3 -> {
                MovieFragment(MovieCategory.BANGLA)
            }
            4 -> {
                MovieFragment(MovieCategory.ANIMATED)
            }
            5 -> {
                MovieFragment(MovieCategory.TAMIL)
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}