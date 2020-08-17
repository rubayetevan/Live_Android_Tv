package live.ebox.tv.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.QuerySnapshot
import live.ebox.tv.repository.Repository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository.getInstance(application.applicationContext)

    fun getTvChannelList(): MutableLiveData<QuerySnapshot> = repository.getTVChannelList()

}