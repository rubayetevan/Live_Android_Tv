package live.ebox.tv.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository private constructor(context: Context) {
    private val TAG: String = "Repository"

    companion object {
        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(context).also {
                    INSTANCE = it
                }
            }
    }

    private val db = Firebase.firestore


    fun getTVChannelList(): MutableLiveData<QuerySnapshot> {
        val data = MutableLiveData<QuerySnapshot>()

        db.collection("channels").orderBy("name")
            .addSnapshotListener { snapshot: QuerySnapshot?, e: FirebaseFirestoreException? ->
                if (e != null) {
                    Log.w(TAG, "TVChannelList Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    data.postValue(snapshot)
                } else {
                    Log.d(TAG, "TVChannelList data: null")
                }
            }

        return data
    }


}