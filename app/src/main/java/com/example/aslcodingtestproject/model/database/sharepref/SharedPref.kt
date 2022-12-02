package com.example.aslcodingtestproject.model.database.sharepref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONException
import java.io.Serializable
import java.lang.reflect.Type


/**
 * Created by Kenneth Tse on 10/08/2021.
 */

@SuppressLint("CommitPrefEdits")
class SharedPref(private val mContext: Context) {

//    private val dbName = if(BuildConfig.IS_UAT) {
//        mContext.getString(R.string.shared_database_name) + mContext.getString(R.string.db_name_postfix)
//    }else{
//        mContext.getString(R.string.shared_database_name)
//    }

    private var gson: Gson? = null
    private var mTransaction = false

    var spec = KeyGenParameterSpec.Builder(
        MasterKey.DEFAULT_MASTER_KEY_ALIAS,
        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
        .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        .build()

    private val masterKey: MasterKey =
        MasterKey.Builder(mContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyGenParameterSpec(spec).build()

    private val preferences: SharedPreferences =
        EncryptedSharedPreferences.create(
            mContext,
            DB_Name,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

    private val editor: SharedPreferences.Editor = preferences.edit()


    fun getGson(): Gson {
        if (null == gson) {
            gson = GsonBuilder().disableHtmlEscaping().create()
        }
        return gson as Gson
    }

    fun beginTransaction() {
        mTransaction = true
    }

    fun commit() {
        editor.apply()
        mTransaction = false
    }

    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: String): String? {
        return preferences.getString(key, defaultValue)
    }

    fun setValue(key: String, value: Int) {
        editor.putInt(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Int): Int {
        val preferences = mContext.getSharedPreferences(DB_Name, Context.MODE_PRIVATE)
        return preferences.getInt(key, defaultValue)
    }

    fun setValue(key: String, value: Long) {
        editor.putLong(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }

    fun setValue(key: String, value: Float) {
        editor.putFloat(key, value)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Float): Float {
        return preferences.getFloat(key, defaultValue)
    }

    fun setValue(key: String, value: Boolean?) {
        editor.putBoolean(key, value == true)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun getValue(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun setSerializableValue(key: String, value: Serializable?) {
//        Timber.i("setSerializableValue(), key: ${key}, value:${value}")

        val valueString: String? = if (value != null) {
            try {
                getGson().toJson(value)
            } catch (e: java.lang.Exception) {
                null
            }
        } else null

//        Logger.i("setSerializableValue(), key: ${key}, json String: $valueString")


        editor.putString(key, valueString)
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun <T> getSerializableValue(
        key: String,
        defaultValue: Serializable?,
        decodeClass: Class<T>,
    ): T? {

        val defaultValueString: String? = if (defaultValue != null) {
            try {
                getGson().toJson(defaultValue)
            } catch (e: java.lang.Exception) {
                null
            }
        } else null

        val valueInDB = preferences.getString(key, defaultValueString)
        return try {
            getGson().fromJson(valueInDB, decodeClass)
        } catch (e: java.lang.Exception) {
            null
        }
    }

    fun getStringListValue(key: String, desc: Boolean?): ArrayList<String> {
        var itemList = ArrayList<String>()
        val json = preferences.getString(key, null)

        if (json.isNullOrEmpty()) return itemList

//        Timber.i("preferences.getString:$json")

        try {

            val groupListType: Type =
                object : TypeToken<ArrayList<String>?>() {}.type
            itemList = getGson().fromJson(json, groupListType)

            if (desc == true) {
                itemList.reverse()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return itemList
    }


    fun <T> setListValue(key: String, value: List<T>) {
        editor.putString(key, JSONArray(value).toString())
        if (!mTransaction) {
            editor.apply()
        }
    }

    fun setObjectValue(key: String, objectToSave: Any): Boolean {

        val jsonString: String = getGson().toJson(objectToSave)

        editor.putString(key, jsonString)
        editor.apply()

        return true
    }

    fun removeValue(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun <T> getObjectValue(key: String, c: Class<T>): T? {

        val jsonString = preferences.getString(key, null)
        //Log.i(TAG, StringUtils.isNotNullOrEmpty(jsonString) ? jsonString : "message null");
        val objectDecoded: T?
        try {
            objectDecoded = getGson().fromJson(jsonString, c)
        } catch (e: NullPointerException) {
            return null
        }

        return c.cast(objectDecoded)
    }

    /**
     * A bug in android 7.0, when using HashMap<T, U> as method return type
     * TypeToken<HashMap<String, String>>() {}.type
     * cannot get T, U as String, String, to prevent this, don't use dynamic type for TypeToken
     */
    fun getStringMapValue(key: String): HashMap<String, String>? {

        val jsonString = preferences.getString(key, null)
        //Log.i(TAG, StringUtils.isNotNullOrEmpty(jsonString) ? jsonString : "message null");
        var objDecode: HashMap<String, String>? = null
        try {
            val type: Type = object : TypeToken<HashMap<String, String>>() {}.type
            objDecode = getGson().fromJson(jsonString, type)
        } catch (e: Exception) {
        }

        return objDecode
    }


    companion object {

        private const val DB_Name = "FFTS"

        fun getInstance(context: Context): SharedPref {
            return SharedPref(context)
        }
    }

}