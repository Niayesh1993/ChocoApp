package com.example.choco.utils

import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import com.example.choco.BuildConfig
import com.google.android.material.textfield.TextInputLayout
import java.lang.NumberFormatException
import java.lang.StringBuilder
import java.util.*
import java.util.regex.Pattern

object InputHelper {
    const val SPACE = "\u202F\u202F"
    private fun isWhiteSpaces(s: String?): Boolean {
        return s != null && s.matches("\\s+".toRegex())
    }

    fun isEmpty(list: List<*>?): Boolean {
        return (list == null
                || list.isEmpty())
    }

    fun isEmpty(text: String?): Boolean {
        return (text == null || TextUtils.isEmpty(text)
                || isWhiteSpaces(text)
                || text.equals("null", ignoreCase = true))
    }

    fun isEmpty(text: Any?): Boolean {
        return text == null || isEmpty(text.toString())
    }

    fun isEmpty(text: EditText?): Boolean {
        return text == null || isEmpty(text.text.toString().trim { it <= ' ' })
    }

    fun toString(input: Int): String {
        return Integer.toString(input)
    }

    fun toString(input: Double): String {
        return java.lang.Double.toString(input)
    }

    fun toString(input: Long): String {
        return java.lang.Long.toString(input)
    }

    fun toString(editText: TextView): String {
        return editText.text.toString()
    }

    fun toString(textInputLayout: TextInputLayout): String {
        return if (textInputLayout.editText != null) toString(textInputLayout.editText) else ""
    }

    fun toString(`object`: Any?): String {
        return if (!isEmpty(`object`)) `object`.toString() else ""
    }



    fun equals(s1: String, s2: String): Boolean {
        return (isEmpty(s1) && isEmpty(s2)
                || !isEmpty(s1) && s1 == s2)
    }


    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    //--------------------------------------------------------------------------------------------||
    //------------------------------------ InputTypes --------------------------------------------||
    //--------------------------------------------------------------------------------------------||
    const val date = "date"
    const val datetime = "datetime"
    const val none = "none"
    const val number = "number"
    const val numberDecimal = "numberDecimal"
    const val numberPassword = "numberPassword"
    const val numberSigned = "numberSigned"
    const val phone = "phone"
    const val text = "text"
    const val textAutoComplete = "textAutoComplete"
    const val textAutoCorrect = "textAutoCorrect"
    const val textCapCharacters = "textCapCharacters"
    const val textCapSentences = "textCapSentences"
    const val textCapWords = "textCapWords"
    const val textEmailAddress = "textEmailAddress"
    const val textEmailSubject = "textEmailSubject"
    const val textFilter = "textFilter"
    const val textLongMessage = "textLongMessage"
    const val textMultiLine = "textMultiLine"
    const val textNoSuggestions = "textNoSuggestions"
    const val textPassword = "textPassword"
    const val textPersonName = "textPersonName"
    const val textPhonetic = "textPhonetic"
    const val textPostalAddress = "textPostalAddress"
    const val textShortMessage = "textShortMessage"
    const val textUri = "textUri"
    const val textVisiblePassword = "textVisiblePassword"
    const val textWebEditText = "textWebEditText"
    const val textWebEmailAddress = "textWebEmailAddress"
    const val textWebPassword = "textWebPassword"
    const val time = "time"
    private var inputTypes: MutableMap<String, Int>? = null

    init {
        inputTypes = HashMap()
        (inputTypes as HashMap<String, Int>)[date] = InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_DATE
        (inputTypes as HashMap<String, Int>)[datetime] =
            InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_NORMAL
        (inputTypes as HashMap<String, Int>)[none] = InputType.TYPE_NULL
        (inputTypes as HashMap<String, Int>)[number] = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
        (inputTypes as HashMap<String, Int>)[numberDecimal] =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        (inputTypes as HashMap<String, Int>)[numberPassword] =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        (inputTypes as HashMap<String, Int>)[numberSigned] = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
        (inputTypes as HashMap<String, Int>)[phone] = InputType.TYPE_CLASS_PHONE
        (inputTypes as HashMap<String, Int>)[text] = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
        (inputTypes as HashMap<String, Int>)[textAutoComplete] = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
        (inputTypes as HashMap<String, Int>)[textAutoCorrect] = InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        (inputTypes as HashMap<String, Int>)[textCapCharacters] = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
        (inputTypes as HashMap<String, Int>)[textCapSentences] = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        (inputTypes as HashMap<String, Int>)[textCapWords] = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        (inputTypes as HashMap<String, Int>)[textEmailAddress] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        (inputTypes as HashMap<String, Int>)[textEmailSubject] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
        (inputTypes as HashMap<String, Int>)[textFilter] = InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE
        (inputTypes as HashMap<String, Int>)[textLongMessage] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE
        (inputTypes as HashMap<String, Int>)[textMultiLine] = InputType.TYPE_TEXT_FLAG_MULTI_LINE
        (inputTypes as HashMap<String, Int>)[textNoSuggestions] = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        (inputTypes as HashMap<String, Int>)[textPassword] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        (inputTypes as HashMap<String, Int>)[textPersonName] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        (inputTypes as HashMap<String, Int>)[textPhonetic] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PHONETIC
        (inputTypes as HashMap<String, Int>)[textPostalAddress] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS
        (inputTypes as HashMap<String, Int>)[textShortMessage] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE
        (inputTypes as HashMap<String, Int>)[textUri] = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI
        (inputTypes as HashMap<String, Int>)[textVisiblePassword] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        (inputTypes as HashMap<String, Int>)[textWebEditText] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT
        (inputTypes as HashMap<String, Int>)[textWebEmailAddress] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
        (inputTypes as HashMap<String, Int>)[textWebPassword] =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        (inputTypes as HashMap<String, Int>)[time] = InputType.TYPE_CLASS_DATETIME or InputType.TYPE_DATETIME_VARIATION_TIME
    }
}
