package live.ebox.tv.ui

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


        tabLayout?.addTab(tabLayout.newTab().setText("TV"))
        tabLayout?.addTab(tabLayout.newTab().setText("Movie"))

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
