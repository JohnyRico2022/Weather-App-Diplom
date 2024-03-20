package ru.nikita.weatherappdiplom.dialogManager

import android.app.AlertDialog
import android.content.Context
import ru.nikita.weatherappdiplom.R

class LocationDialog() {

    fun settingsLocation(context: Context, listener: DialogClickListener) {
        AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.attention)
            .setMessage(R.string.gps_question)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onClick()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}