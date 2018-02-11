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

@file:Suppress("unused")

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
inline fun SharedPreferences.edit(unit: SharedPreferences.Editor.() -> Unit) =
    edit().apply(unit).apply()

/**
 *
 * @see SharedPreferences.commit
 */
@SuppressLint("ApplySharedPref")
inline fun SharedPreferences.commit(unit: SharedPreferences.Editor.() -> Unit) =
    edit().apply(unit).commit()

/**
 * [SharedPreferences].[get] Kotlin Operator
 *
 * usages :
 *
 *  val pref : Int = sharedPreferences[[key]] or
 *
 *  val pref = sharedPreferences[[key], [defaultValue]] or
 *
 *  val pref = sharedPreferences.get<Int>([key])
 *
 * if [defaultValue] is not specified or null the following values will be used,
 * depending of the preference type
 *
 * String -> null
 * Integer -> -1
 * Boolean -> false
 * Float -> -1f
 * Long -> -1
 * Set<String> -> null
 *
 * @throw [UnsupportedOperationException] if the preference type is not supported
 * @throw [ClassCastException] if the specified [Set] isn't a [Set] of [String]
 * */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null):
        T? = when (T::class) {
            String::class -> getString(key, defaultValue as? String) as? T
            Integer::class -> getInt(key, defaultValue as? Int ?: -1) as T
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
            Set::class -> {
                @Suppress("UNCHECKED_CAST")
                getStringSet(key, defaultValue as? Set<String>) as? T
            }
            else -> throw UnsupportedOperationException(T::class.java.simpleName +
                    " is not supported as preference type")
        }

/**
 * [SharedPreferences].[put] Kotlin Operator
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @throw [UnsupportedOperationException] if the preference type is not supported
 * @throw [ClassCastException] if the specified [Set] isn't a [Set] of [String]
 * */
inline operator fun <reified T : Any> SharedPreferences.set(key: String, value: T) =
    when (T::class) {
            String::class -> edit { putString(key, value as String) }
            Integer::class -> edit { putInt(key, value as Int) }
            Boolean::class -> edit { putBoolean(key, value as Boolean) }
            Float::class -> edit { putFloat(key, value as Float) }
            Long::class -> edit { putLong(key, value as Long) }
            Set::class -> {
                @Suppress("UNCHECKED_CAST")
                edit { putStringSet(key, value as Set<String>) }
            }
            else -> throw UnsupportedOperationException(T::class.java.simpleName +
                    " is not supported as preference type")
    }