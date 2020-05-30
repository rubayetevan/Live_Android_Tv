package live.ebox.tv

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar?.visibility = View.VISIBLE
        channelRV?.visibility = View.GONE
        val width = getScreenWidth(this@MainActivity)
        val db = Firebase.firestore
        db.collection("channels").orderBy("name")
            .get()
            .addOnSuccessListener { result ->
                channelRV?.layoutManager = GridLayoutManager(this, ((width / 120.0).roundToInt()))
                channelRV?.adapter = ChannelAdapter(result, this@MainActivity)
                channelRV?.adapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE
                channelRV?.visibility = View.VISIBLE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    private fun getScreenWidth(context: Context): Float {
        return Resources.getSystem().displayMetrics.widthPixels / context.resources.displayMetrics.density
    }
}
