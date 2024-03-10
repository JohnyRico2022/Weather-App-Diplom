package ru.nikita.weatherappdiplom.dialogManager

import android.app.AlertDialog
import android.content.Context
import ru.nikita.weatherappdiplom.R

class InfoDialog {

    fun mainCardInfoDialog(context: Context) {

        AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.important_information)
            .setMessage(R.string.info_main_card)
            .setPositiveButton(R.string.i_understand) { _, _ -> }
            .show()

    }

    fun registrationInfoDialog(context: Context) {

        AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.important_information)
            .setMessage(R.string.text_in_dialog)
            .setPositiveButton(R.string.i_understand) { _, _ -> }
            .show()

    }
}