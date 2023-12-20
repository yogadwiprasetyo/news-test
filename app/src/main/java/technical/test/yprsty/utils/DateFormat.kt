package technical.test.yprsty.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun String.convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.getDefault())

    return try {
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        inputDate // Default format
    }
}