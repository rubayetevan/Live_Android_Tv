package live.ebox.tv.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*
import live.ebox.tv.R
import live.ebox.tv.ui.PlayerActivity


class ChannelAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private var items: QuerySnapshot? = null

    fun setData(dataList: QuerySnapshot?) {
        this.items = dataList
    }

    override fun getItemCount(): Int {
        return items?.size() ?: 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        try {
            holder.channelName?.text = items?.documents?.get(position)?.get("name") as String
            Picasso.get().load(items?.documents?.get(position)?.get("logo") as String)
                .into(holder.channelLogo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java).apply {
                putExtra("Url", items?.documents?.get(position)?.get("url") as String)
            }
            context.startActivity(intent)
        }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val channelLogo: ImageView? = view.logoIMGV
    val channelName: TextView? = view.channelNameTV
}