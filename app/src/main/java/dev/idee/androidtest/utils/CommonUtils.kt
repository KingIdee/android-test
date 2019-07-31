package dev.idee.androidtest.utils

import android.widget.EditText
import dev.idee.androidtest.R

fun validateEditText(editText: EditText): Boolean {
    if (editText.text.isEmpty()) {
        editText.error = editText.context.getString(R.string.edit_text_error)
        return false
    }
    editText.error = null
    return true
}