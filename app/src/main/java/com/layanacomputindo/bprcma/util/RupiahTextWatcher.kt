package com.layanacomputindo.bprcma.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

class RupiahTextWatcher(internal var editText: EditText) : TextWatcher {
    internal lateinit var text: String

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        text = charSequence.toString().replace("[$,.]".toRegex(), "")
        text = text.replace("Rp ", "")
        if (!text.equals("Rp ", ignoreCase = true) && !text.equals("", ignoreCase = true)) {
            if (java.lang.Double.valueOf(text) < 100000000000) {
                editText.removeTextChangedListener(this)
                val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
                val rupiah = rupiahFormat.format(java.lang.Double.valueOf(text))
                editText.setText("$rupiah")
                editText.setSelection(rupiah.length)
                editText.addTextChangedListener(this)
            } else {
                text = "9999999999"
                val rupiahFormat = NumberFormat.getInstance(Locale.GERMANY)
                val rupiah = rupiahFormat.format(java.lang.Double.valueOf(text))
                editText.setText("$rupiah")
            }

        } else {
            editText.removeTextChangedListener(this)
            editText.setText("")
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(editable: Editable) {}
}