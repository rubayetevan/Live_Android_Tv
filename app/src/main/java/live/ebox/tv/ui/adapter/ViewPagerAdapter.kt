package live.ebox.tv.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import live.ebox.tv.ui.MovieFragment
import live.ebox.tv.ui.TvChannelFragment


class ViewPagerAdapter(fm: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                TvChannelFragment()
            }
            1 -> {
                MovieFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}