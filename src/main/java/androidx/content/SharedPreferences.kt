/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.content

import android.annotation.SuppressLint
import android.content.SharedPreferences

/**
 * Allows editing of this preference instance with a call to
 * [SharedPreferences.Editor.apply] to persist the changes.
 *
 * prefs.edit {
 *     putString("key", value)
 *     myInteger = getInt("key2", 0)
 *     [...]
 * }
 *
 * @see SharedPreferences.apply
 */
inline fun SharedPreferences.edit(unit: SharedPreferences.Editor.() -> Unit)
        = edit().apply(unit).apply()

/**
 *
 * @see SharedPreferences.commit
 */
@SuppressLint("ApplySharedPref")
inline fun SharedPreferences.commit(unit: SharedPreferences.Editor.() -> Unit)
        = edit().apply(unit).commit()

/**
 *
 * @see SharedPreferences.getInt
 */
operator fun SharedPreferences.get(key: String, defaultValue: Int): Int =
    getInt(key, defaultValue)

/**
 *
 * @see SharedPreferences.getFloat
 */
operator fun SharedPreferences.get(key: String, defaultValue: Float): Float =
    getFloat(key, defaultValue)

/**
 *
 * @see SharedPreferences.getLong
 */
operator fun SharedPreferences.get(key: String, defaultValue: Long): Long =
    getLong(key, defaultValue)

/**
 *
 * @see SharedPreferences.getBoolean
 */
operator fun SharedPreferences.get(key: String, defaultValue: Boolean): Boolean =
    getBoolean(key, defaultValue)

/**
 *
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null):
        T? = when (T::class) {
    String::class -> getString(key, defaultValue as? String) as? T
    Set::class -> {
        @Suppress("UNCHECKED_CAST")
        getStringSet(key, defaultValue as? Set<String>) as? T
    }
    Integer::class -> {
        if(contains(key))
            if(defaultValue != null) getInt(key, defaultValue as Int) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Float::class -> {
        if(contains(key))
            if(defaultValue != null) getFloat(key, defaultValue as Float) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Long::class -> {
        if(contains(key))
            if(defaultValue != null) getLong(key, defaultValue as Long) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Boolean::class -> {
        if(contains(key))
            if(defaultValue != null) getBoolean(key, defaultValue as Boolean) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    else -> throw UnsupportedOperationException(T::class.java.simpleName +
            " is not supported as preference type")
}

/**
 *
 * @see SharedPreferences.Editor.putString
 */
operator fun SharedPreferences.set(key: String, value: String?) =
    edit { putString(key, value) }

/**
 *
 * @see SharedPreferences.Editor.putBoolean
 */
operator fun SharedPreferences.set(key: String, value: Boolean) =
    edit { putBoolean(key, value) }

/**
 *
 * @see SharedPreferences.Editor.putInt
 */
operator fun SharedPreferences.set(key: String, value: Int) =
    edit { putInt(key, value) }

/**
 *
 * @see SharedPreferences.Editor.putFloat
 */
operator fun SharedPreferences.set(key: String, value: Float) =
    edit { putFloat(key, value) }

/**
 *
 * @see SharedPreferences.Editor.putLong
 */
operator fun SharedPreferences.set(key: String, value: Long) =
    edit { putLong(key, value) }

/**
 *
 * @see SharedPreferences.Editor.putStringSet
 */
operator fun SharedPreferences.set(key: String, value: Set<String>?) =
    edit { putStringSet(key, value) }
