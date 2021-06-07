package com.example.sossalao.ui.schedule

import android.content.Context
import android.util.Log
import androidx.room.Dao
import com.example.sossalao.repository.HttpHelper
import com.example.sossalao.Prefs
import com.example.sossalao.repository.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject


object ScheduleService {

    val host = "http://3.93.42.204:32770"
    val TAG = "ScheduleService"
    val token = Prefs.getString("API_TOKEN").toString()

    fun getSchedule (context: Context): List<Schedule> {
        val url = "$host/api/scheduling"
        val json = HttpHelper.get(url, token, "API")
        return parserJson(json)

    }

    fun getScheduleById(context: Context, id: Int): Schedule {
        val url = "$host/api/scheduling/$id"
        val json = HttpHelper.get(url, token, "API")

        return parserJson(json)
    }


    fun save(schedule: Schedule): Response {
        val json = HttpHelper.post("$host/api/scheduling", schedule.toJsonPost(), token, "API")
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun delete(schedule: Schedule): Response {
        Log.d(TAG, schedule.idScheduling.toString())
        val url = "$host/api/scheduling/${schedule.idScheduling}"
        val json = HttpHelper.delete(url, token)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun update(schedule: Schedule):Response{
        val url = "$host/api/scheduling/${schedule.idScheduling}"
        val json = HttpHelper.update("$host/api/scheduling", schedule.toJson(), token)

        Log.d(TAG, json)
        return  parserJson(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}