package live.ebox.tv.base

import android.content.Context
import android.content.res.Resources

object Util {
    fun getScreenWidth(context: Context): Float {
        return Resources.getSystem().displayMetrics.widthPixels / context.resources.displayMetrics.density
    }
}