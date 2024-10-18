package game.utils.time

fun now(): Long = System.currentTimeMillis()

fun timeunit(): Long {
    return 3
}

fun millisToTimeFormatted(time: Long): String {
    val minutes = (time / 1000) / 60
    val seconds = (time / 1000) % 60

    val minutesFormatted = if (minutes < 10) {
        "0$minutes"
    } else {
        minutes.toString()
    }

    val secondsFormatted = if (seconds < 10) {
        "0$seconds"
    } else {
        seconds.toString()
    }

    return "$minutesFormatted:$secondsFormatted"
}
