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

import android.content.SharedPreferences
import java.util.Objects
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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
 * @see SharedPreferences.Editor.apply
 */
inline fun SharedPreferences.edit(unit: SharedPreferences.Editor.() -> Unit) =
    edit().apply(unit).apply()

/**
 * Operator to Retrieve a [Int] value from the preferences.
 *
 * usage : sharedPreferences[[key], [defaultValue]]
 *
 * @see SharedPreferences.getInt([key], [defaultValue])
 */
operator fun SharedPreferences.get(key: String, defaultValue: Int): Int =
    getInt(key, defaultValue)

/**
 * Operator to Retrieve a [Float] value from the preferences.
 *
 * usage : sharedPreferences[[key], [defaultValue]]
 *
 * @see SharedPreferences.getFloat([key], [defaultValue])
 */
operator fun SharedPreferences.get(key: String, defaultValue: Float): Float =
    getFloat(key, defaultValue)

/**
 * Operator to Retrieve a [Long] value from the preferences.
 *
 * usage : sharedPreferences[[key], [defaultValue]]
 *
 * @see SharedPreferences.getLong([key], [defaultValue])
 */
operator fun SharedPreferences.get(key: String, defaultValue: Long): Long =
    getLong(key, defaultValue)

/**
 * Operator to Retrieve a [Boolean] value from the preferences.
 *
 * usage : sharedPreferences[[key], [defaultValue]]
 *
 * @see SharedPreferences.getBoolean([key], [defaultValue])
 */
operator fun SharedPreferences.get(key: String, defaultValue: Boolean): Boolean =
    getBoolean(key, defaultValue)

/**
 * Operator to Set a [String] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putString([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: String?) =
    edit { putString(key, value) }

/**
 * Operator to Set a [Int] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putInt([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: Int) =
    edit { putInt(key, value) }

/**
 * Operator to Set a [Float] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putFloat([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: Float) =
    edit { putFloat(key, value) }

/**
 * Operator to Set a [Long] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putLong([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: Long) =
    edit { putLong(key, value) }

/**
 * Operator to Set a [Boolean] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putBoolean([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: Boolean) =
    edit { putBoolean(key, value) }

/**
 * Operator to Set a [Set<String>] value in the preferences editor and apply immediately
 *
 * usage : sharedPreferences[[key]] = [value]
 *
 * @see SharedPreferences.Editor.putStringSet([key],[value])
 */
operator fun SharedPreferences.set(key: String, value: Set<String>?) =
    edit { putStringSet(key, value) }

/**
 * Operator to Retrieve a value from the preferences.
 *
 * @param key The name of the preference to retrieve.
 * @null @param defaultValue Value to return if this preference does not exist.
 *
 * @return Returns the preference value if it exists, or defaultValue (Null if nothing specified).
 *
 * @throws ClassCastException if there is a preference with this name that is not the
 * reified type or the explicit type specified.
 *
 * @throws UnsupportedOperationException if the explicit type specified or
 * the reified type isn't a type supported by [SharedPreferences] like :
 * val value = preferences["key", UnsupportedType()] or
 * val value = preferences.get<UnsupportedType>["key"] or
 * val value: UnsupportedType = preferences["key"]
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null):
        T? = when (T::class) {
    String::class -> getString(key, defaultValue as? String) as? T
    Set::class -> {
        @Suppress("UNCHECKED_CAST")
        getStringSet(key, defaultValue as? Set<String>) as? T
    }
    Integer::class -> {
        if (contains(key))
            if (defaultValue != null) getInt(key, defaultValue as Int) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Float::class -> {
        if (contains(key))
            if (defaultValue != null) getFloat(key, defaultValue as Float) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Long::class -> {
        if (contains(key))
            if (defaultValue != null) getLong(key, defaultValue as Long) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Boolean::class -> {
        if (contains(key))
            if (defaultValue != null) getBoolean(key, defaultValue as Boolean) as T
            else all[key] as? T ?: throw ClassCastException()
        else defaultValue
    }
    Object::class -> {

        // Not sure which behavior to choose in that case,
        // This case happen if the type cannot be reified and is not explicitly set like
        // preferences["key"]
        //

        //throw UnsupportedOperationException("A default value or " +
        //      "an explicit parameter must be supplied")

        all[key] as? T
    }
    else -> throw UnsupportedOperationException(T::class.java.simpleName +
            " is not supported as preference type")
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> SharedPreferences.property(key: String, defaultValue: T? = null):
        ReadWriteProperty<Any?, T?> {
            return object : ReadWriteProperty<Any?, T?>{
                override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
                    return this@property[key, defaultValue]
                }

                override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
                    when (T::class){
                        String::class -> this@property[key] = value as String
                        Set::class -> this@property[key] = value as Set<String>
                        Int::class -> this@property[key] = value as Int
                        Float::class -> this@property[key] = value as Float
                        Long::class -> this@property[key] = value as Long
                        Boolean::class -> this@property[key] = value as Boolean
                    }
                }
            }
}

class SharedPreferencesStringProperty(
    val sharedPreferences: SharedPreferences,
    val key:
    String, val defaultValue: String?):
    ReadWriteProperty<Any?, String?> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return sharedPreferences[key, defaultValue]
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        sharedPreferences[key] = value
    }
}


class SharedPreferencesIntProperty(
    val sharedPreferences: SharedPreferences,
    val key:
    String, val defaultValue: Int):
    ReadWriteProperty<Any?, Int> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return sharedPreferences[key, defaultValue]
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        sharedPreferences[key] = value
    }
}

class SharedPreferencesFloatProperty(
    val sharedPreferences: SharedPreferences,
    val key:
    String, val defaultValue: Float):
    ReadWriteProperty<Any?, Float> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
        return sharedPreferences[key, defaultValue]
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
        sharedPreferences[key] = value
    }
}
