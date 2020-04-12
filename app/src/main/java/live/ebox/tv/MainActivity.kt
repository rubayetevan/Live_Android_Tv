package live.ebox.tv

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val width = getScreenWidth(this@MainActivity)
        val db = Firebase.firestore
        db.collection("channels").orderBy("name")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data["name"]}")
                }
                channelRV?.layoutManager = GridLayoutManager(this, ((width / 120.0).roundToInt()))
                channelRV?.adapter = ChannelAdapter(result, this@MainActivity)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }


    fun getScreenWidth(context: Context): Float {

        val px = Resources.getSystem().getDisplayMetrics().widthPixels
        return px / context.getResources().displayMetrics.density
    }
}
