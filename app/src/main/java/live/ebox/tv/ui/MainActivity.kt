package live.ebox.tv.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*
import live.ebox.tv.R
import live.ebox.tv.base.Util
import live.ebox.tv.ui.adapter.ChannelAdapter
import live.ebox.tv.ui.viewModel.HomeViewModel
import kotlin.math.roundToInt

class MainActivity : FragmentActivity() {
    private val TAG: String = "MainActivity"

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var channelAdapter: ChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpChannelRV()
    }

    private fun setUpChannelRV() {
        progressBar?.visibility = View.VISIBLE
        channelRV?.visibility = View.GONE
        channelRV?.layoutManager =
            GridLayoutManager(this, ((Util.getScreenWidth(this@MainActivity) / 120.0).roundToInt()))
        channelAdapter = ChannelAdapter(this@MainActivity)
        channelRV?.adapter = channelAdapter
        homeViewModel.getTvChannelList().observe(this, tvChannelListObserver)
    }

    private val tvChannelListObserver = Observer<QuerySnapshot> {
        if (it != null && !it.isEmpty && this::channelAdapter.isInitialized) {
            Log.d("tvChannelListObserver", "Called")
            channelAdapter.setData(it)
            channelRV?.adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
            channelRV?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.VISIBLE
            channelRV?.visibility = View.GONE
        }
    }

}
