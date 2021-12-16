package com.eegets

import com.eegets.GsonUtls.getGson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by wangkai on 2021/02/20 16:17

 * Desc 设置GSON的非严格模式setLenient()，严格模式下特殊字符或空格都会导致Gson解析异常
 */
object GsonUtls {
    fun getGson(): Gson {
        return GsonBuilder()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .setLenient()
                .create()
    }
}

/**
 * 实体类转化为json
 */
fun Any.beanToJson(): String? {
    return getGson().toJson(this)
}

/**
 * json转化为Bean
 */
inline fun <reified T> String.jsonToBean(): T? {
    return getGson().fromJson<T>(this, object : TypeToken<T>() {}.type)
}

/**
 * json转为List
 */
inline fun <reified T> String.json2List(): List<T>? {
    try {
        return getGson().fromJson(this, object : TypeToken<List<T>?>() {}.type) ?: return ArrayList()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ArrayList()
}