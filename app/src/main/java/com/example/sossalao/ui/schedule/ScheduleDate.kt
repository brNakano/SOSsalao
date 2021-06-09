package com.example.sossalao.ui.schedule

import java.time.LocalDateTime

object ScheduleDate {
    fun dayNum(date: String?): String {
        val dateTime = LocalDateTime.parse(date)
        if(dateTime.dayOfMonth.toString().length < "00".length){
            return "0${dateTime.dayOfMonth}"
        }

        return dateTime.dayOfMonth.toString()
    }

    fun dayWeek(date: String?): String {
        val dateTime = LocalDateTime.parse(date)

        return toPortugueseWeek(dateTime.dayOfWeek.toString())
    }
    fun month(date: String?): String {
        val dateTime = LocalDateTime.parse(date)

        return toPortugueseMonth(dateTime.month.toString())
    }

    fun monthValue(date: String?): String {
        val dateTime = LocalDateTime.parse(date)
        if(dateTime.monthValue.toString().length < "00".length){
            return "0${dateTime.monthValue}"
        }

        return dateTime.monthValue.toString()
    }

    fun year(date: String?): Int {
        val dateTime = LocalDateTime.parse(date)

        return dateTime.year
    }

    fun hour(date: String?): Int {
        val dateTime = LocalDateTime.parse(date)

        return dateTime.hour
    }

    fun minute(date: String?): Int {
        val dateTime = LocalDateTime.parse(date)

        return dateTime.minute
    }

    fun toPortugueseMonth(month: String?): String {
        if(month == "JANUARY"){
            return "Janeiro"
        }
        if(month == "FEBRUARY"){
            return "Fevereiro"
        }
        if(month == "MARCH"){
            return "Março"
        }
        if(month == "APRIL"){
            return "Abril"
        }
        if(month == "MAY"){
            return "Maio"
        }
        if(month == "JUNE"){
            return "Junho"
        }
        if(month == "JULY"){
            return "Julho"
        }
        if(month == "AUGUST"){
            return "Agosto"
        }
        if(month == "SEPTEMBER"){
            return "Setembro"
        }
        if(month == "OCTOBER"){
            return "Outubro"
        }
        if(month == "NOVEMBER"){
            return "Novembro"
        }
        if(month == "DECEMBER"){
            return "Dezembro"
        }
        return ""
    }

    fun toPortugueseWeek(week: String?): String {
        if(week == "SUNDAY"){
            return "Dom"
        }
        if(week == "MONDAY"){
            return "Seg"
        }
        if(week == "TUESDAY"){
            return "Ter"
        }
        if(week == "WEDNESDAY"){
            return "Qua"
        }
        if(week == "THURSDAY"){
            return "Qui"
        }
        if(week == "FRIDAY"){
            return "Sex"
        }
        if(week == "SATURDAY"){
            return "Sáb"
        }
        return ""
    }
}