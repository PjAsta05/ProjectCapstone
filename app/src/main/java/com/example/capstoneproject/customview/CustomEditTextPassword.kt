package com.example.capstoneproject.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.example.capstoneproject.R
import com.google.android.material.textfield.TextInputEditText

class CustomEditTextPassword @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {
    private var passwordValid = false
    private var validationCallback: ((Boolean) -> Unit)? = null

    init {
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }

        })
    }

    private fun validateInput() {
        if ((text?.length ?: 0) < 8) {
            passwordValid = false
            setError(context.getString(R.string.password_error), null)
        } else {
            passwordValid = true
            error = null
        }
        validationCallback?.invoke(passwordValid)
    }

    fun isValidCallback(callback: (Boolean) -> Unit) {
        validationCallback = callback
    }
}