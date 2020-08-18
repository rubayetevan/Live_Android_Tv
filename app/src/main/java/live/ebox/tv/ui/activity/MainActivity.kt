package live.ebox.tv.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import live.ebox.tv.R
import live.ebox.tv.ui.adapter.ViewPagerAdapter

class MainActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tabLayout?.addTab(tabLayout.newTab().setText("Live TV"))
        tabLayout?.addTab(tabLayout.newTab().setText("Hindi Movie"))
        tabLayout?.addTab(tabLayout.newTab().setText("English Movie"))
        tabLayout?.addTab(tabLayout.newTab().setText("Bangla Movie"))
        tabLayout?.addTab(tabLayout.newTab().setText("Animated Movie"))
        tabLayout?.addTab(tabLayout.newTab().setText("Tamil Movie"))

        tabLayout?.tabGravity = TabLayout.GRAVITY_FILL

        viewPager?.adapter = ViewPagerAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}
