package ru.nikita.weatherappdiplom.utils

class DateConverter {

    fun convertDate(dateFromServer: String): String {

        //    dateFromServer = "2024-02-27"
        return dateFromServer[8].toString() + dateFromServer[9]
            .toString() + "." + dateFromServer[5].toString() + dateFromServer[6].toString()
    }

    fun convertDateWithHours(dateFromServer: String): String {

        // dateWithHourFromServer = "2024-02-28 23:00"
        return dateFromServer[8].toString() + dateFromServer[9]
            .toString() + "." + dateFromServer[5].toString() + dateFromServer[6]
            .toString() + " " + dateFromServer[11].toString() + dateFromServer[12]
            .toString() + dateFromServer[13].toString() + dateFromServer[14]
            .toString() + dateFromServer[15].toString()
    }
}