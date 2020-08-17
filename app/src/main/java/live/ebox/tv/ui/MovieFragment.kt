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
import kotlinx.android.synthetic.main.fragment_movie.*
import live.ebox.tv.R
import live.ebox.tv.base.Util
import live.ebox.tv.ui.adapter.MovieAdapter
import live.ebox.tv.ui.viewModel.HomeViewModel
import kotlin.math.roundToInt

class MovieFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpChannelRV(activity as Context)
    }

    private fun setUpChannelRV(context: Context) {
        progressBar?.visibility = View.VISIBLE
        movieRV?.visibility = View.GONE
        movieRV?.layoutManager =
            GridLayoutManager(context, ((Util.getScreenWidth(context) / 110.0).roundToInt()))
        movieAdapter = MovieAdapter(context)
        movieRV?.adapter = movieAdapter
        homeViewModel.getMovieList().observe(viewLifecycleOwner, movieListObserver)
    }

    private val movieListObserver = Observer<QuerySnapshot> {
        Log.d("movieListObserver", "Called")
        if (it != null && !it.isEmpty && this::movieAdapter.isInitialized) {
            movieAdapter.setData(it)
            movieRV?.adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
            movieRV?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.VISIBLE
            movieRV?.visibility = View.GONE
        }
    }

}