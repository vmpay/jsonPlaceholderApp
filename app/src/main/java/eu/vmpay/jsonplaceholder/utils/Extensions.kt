package eu.vmpay.jsonplaceholder.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.postDistinct(newValue: T?) {
    if (this.value != newValue) this.postValue(newValue)
}

fun Context.sendEmailTo(email: String) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SENDTO
        putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
        type = "text/plain"
        data = Uri.parse("mailto:$email")
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    startActivity(shareIntent)
}