package com.example.sossalao

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sossalao.HttpHelper.JSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

object InventoryService {

    val host = "http://3.93.42.204:32770"
    val TAG = "WS_SOSsalao"
    val token = Prefs.getString("API_TOKEN").toString()

    fun getInventory (context: Context): List<Inventory> {
            val url = "$host/api/stock/product"
            val json = HttpHelper.get(url, token, "API")
            return parserJson(json)

    }


    fun save(product: Inventory): Response {
        val data = JSONObject(product.toString())
        val json = HttpHelper.post("$host/api/stock/product", data.toString(), token, "API")
        Log.d(TAG, data.toString())
        return parserJson(json)
    }

    fun delete(product: Inventory): Response {
        Log.d(TAG, product.idProduct.toString())
        val url = "$host/api/stock/product/${product.idProduct}"
        val json = HttpHelper.delete(url, token)
        Log.d(TAG, json)
        return parserJson(json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}