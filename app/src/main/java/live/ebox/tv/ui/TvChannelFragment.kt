package live.ebox.tv.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_tv_channel.*
import live.ebox.tv.R
import live.ebox.tv.base.Util
import live.ebox.tv.ui.adapter.ChannelAdapter
import live.ebox.tv.ui.viewModel.HomeViewModel
import kotlin.math.roundToInt

class TvChannelFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var channelAdapter: ChannelAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_channel, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpChannelRV(activity as Context)
    }

    private fun setUpChannelRV(context: Context) {
        progressBar?.visibility = View.VISIBLE
        channelRV?.visibility = View.GONE
        channelRV?.layoutManager =
            GridLayoutManager(context, ((Util.getScreenWidth(context) / 120.0).roundToInt()))
        channelAdapter = ChannelAdapter(context)
        channelRV?.adapter = channelAdapter
        homeViewModel.getTvChannelList().observe(viewLifecycleOwner, tvChannelListObserver)
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