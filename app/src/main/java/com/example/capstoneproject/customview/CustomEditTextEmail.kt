package com.example.capstoneproject.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import com.example.capstoneproject.R
import com.google.android.material.textfield.TextInputEditText

class CustomEditTextEmail @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {
    private var emailValid = false
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
        if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches()) {
            emailValid = false
            setError(context.getString(R.string.email_error), null)
        }else {
            emailValid = true
            error = null
        }
        validationCallback?.invoke(emailValid)
    }

    fun isValidCallback(callback: (Boolean) -> Unit) {
        validationCallback = callback
    }
}