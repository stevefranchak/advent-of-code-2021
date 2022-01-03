@file:JvmName("GeneralUtils")
@file:JvmMultifileClass

package advent.of.code

import advent.of.code.days.IDay

fun executeDayByStar(day: Int, star: Int, input: List<String>): Any {
    val dayClass = try {
        Class.forName("advent.of.code.days.Day$day")
    } catch (exc: ClassNotFoundException) {
        throw InvalidDayException(day, exc)
    }
    val dayInstance = dayClass.getDeclaredConstructor().newInstance() as IDay
    return when (star) {
        1 -> dayInstance.executeStar1(input)
        2 -> dayInstance.executeStar2(input)
        else -> throw InvalidStarException(star)
    }
}

class InvalidDayException(providedDay: Int, cause: Throwable) : Exception("Invalid day $providedDay provided.", cause)

class InvalidStarException(providedStar: Int) : Exception("Invalid star $providedStar provided.")
