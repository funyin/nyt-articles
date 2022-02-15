package com.initbase.nytarticles.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter



fun String.toReadableDate(): String {
    return LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(
        DateTimeFormatter.ofPattern("MMM. dd, yyyy")
    )
}