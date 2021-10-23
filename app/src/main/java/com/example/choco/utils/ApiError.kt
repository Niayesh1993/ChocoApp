package com.example.choco.utils

data class ApiError(var widget: String? = null,
                    var title: String? = null,
                    var text: String? = null,
                    var code: Int? = null) {

    object Widget {
        const val DIALOG_BOX = "dialogBox"
        const val TOAST = "toast"
        const val SNACK_BAR = "snackBar"
    }
}