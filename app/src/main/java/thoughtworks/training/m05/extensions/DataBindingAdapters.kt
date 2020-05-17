package thoughtworks.training.m05.extensions

import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.BindingAdapter

@BindingAdapter("app:hideIfEmpty")
fun hideIfEmpty(view: View, value: String) {
    view.visibility = if (value.isEmpty()) View.GONE else View.VISIBLE
}


@BindingAdapter("app:checkoutResult")
fun setText(textView: TextView, checkoutResult: String) {
    textView.text = checkoutResult
}