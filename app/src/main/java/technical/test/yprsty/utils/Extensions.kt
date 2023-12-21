package technical.test.yprsty.utils

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import technical.test.yprsty.R
import java.text.SimpleDateFormat
import java.util.Locale

fun String.convertDateFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        this // Default format
    }
}

fun ImageView.loadImage(src: String?, radiusCorner: Float = 24.0f) {
    load(src) {
        crossfade(true)
        placeholder(R.drawable.round_access_time_24)
        error(R.drawable.round_error_24)
        transformations(RoundedCornersTransformation(radiusCorner))
    }
}
