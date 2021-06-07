package com.example.sossalao.repository

import android.util.Log
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

object HttpHelper {

    private val TAG = "HTTP_SOS"
    private val LOG_ON = true
    val JSON = MediaType.parse("application/json; charset=utf-8")

    var client = OkHttpClient()

    // GET
    fun get(url:String, token: String, type: String): String {
        Log.d(TAG, "HttpHelper.get: $url")
        var headerValue = "Bearer $token"
        if (type == "API"){
            headerValue = "Bearer $token"
        } else if (type == "FB"){
            headerValue = "key=$token"
        }
        val request = Request.Builder()
            .url(url)
            .header("Authorization", headerValue)
            .addHeader("Content-Type", "application/json")
            .get()
            .build()
        return getJson(request)
    }

    // POST
    fun post(url: String, json: String, token: String,type: String): String {
        var headerValue = "Bearer $token"
        if (type == "API"){
            headerValue = "Bearer $token"
        } else if (type == "FB"){
            headerValue = "key=$token"
        }
        Log.d(TAG, "HttpHelper.post: $url > $json")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .header("Authorization", headerValue)
            .addHeader("Content-Type", "application/json")
            .build()
        return getJson(request)
    }

    // DELETE
    fun delete(url: String, token: String): String {
        Log.d(TAG, "HttpHelper.delete: $url")
        val request = Request.Builder()
            .url(url)
            .delete()
            .header("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()
        return getJson(request)
    }

    fun update(url: String, json: String, token: String): String {
        Log.d(TAG, "HttpHelper.update: $url")
        val body = RequestBody.create(JSON, json)
        val request = Request.Builder()
            .url(url)
            .put(body)
            .header("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()
        return getJson(request)
    }



    private fun getJson(request: Request?): String {
        val response = client.newCall(request).execute()
        val body = response.body()
        if (body != null) {
            val json = body.string()
            Log.d(TAG, "  << : $json")
            return json
        }
        throw IOException("Erro na requisição")
    }
}