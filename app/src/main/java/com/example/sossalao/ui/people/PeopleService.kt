package com.example.sossalao.ui.people

import android.content.Context
import android.util.Log
import com.example.sossalao.Prefs
import com.example.sossalao.repository.HttpHelper
import com.example.sossalao.repository.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PeopleService {

    val host = "http://3.93.42.204:32770"
    val TAG = "PeopleService"
    val token = Prefs.getString("API_TOKEN").toString()

    fun getPeople (context: Context): List<People> {
        val url = "$host/api/people"
        val json = HttpHelper.get(url, token, "API")
        return parserJson(json)

    }

    fun getPeopleById(context: Context, id: Int): People {
        val url = "$host/api/people/$id"
        val json = HttpHelper.get(url, token, "API")

        return parserJson(json)
    }


    fun save(people: People): Response {
        val json = HttpHelper.post("$host/api/people", people.toJsonPost(), token, "API")
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun delete(people: People): Response {
        Log.d(TAG, people.idPeople.toString())
        val url = "$host/api/people/${people.idPeople}"
        val json = HttpHelper.delete(url, token)
        Log.d(TAG, json)
        return parserJson(json)
    }

    fun update(people: People): Response {
        val url = "$host/api/people/${people.idPeople}"
        val json = HttpHelper.update(url, people.toJson(), token)

        Log.d(TAG, json)
        return  parserJson(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}