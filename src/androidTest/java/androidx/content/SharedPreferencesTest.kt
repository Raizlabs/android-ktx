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

import android.support.test.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SharedPreferencesTest {

    private val context = InstrumentationRegistry.getContext()

    @Test fun edit() {
        val preferences = context.getSharedPreferences("prefs_edit", 0)

        preferences.edit {
            putString("test_key1", "test_value")
            putInt("test_key2", 100)
        }

        assertEquals("test_value", preferences.getString("test_key1", null))
        assertEquals(100, preferences.getInt("test_key2", 0))
    }

    @Test fun commit() {
        val preferences = context.getSharedPreferences("prefs_commit", 0)

        val testKeyString = "test_key_string"
        val testString = "test"

        val testKeyInt = "test_key_int"
        val defaultInt = 0
        val testInt = 1

        assertNotEquals(preferences.getString(testKeyString, null), testString)
        assertNotEquals(preferences.getInt(testKeyInt, defaultInt), testInt)

        preferences.commit {
            putString(testKeyString, testKeyString)
            putInt(testKeyInt, testInt)
        }

        assertEquals(preferences.getString(testKeyString, null), testString)
        assertEquals(preferences.getInt(testKeyInt, defaultInt), testInt)
    }

    @Test fun getSharedPreferencesNonNullInteger() {
        val preferences = context.getSharedPreferences("prefs_integer", 0)

        val testKey = "test_key"
        val defaultValue = 1
        val value = 2
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        assertEquals(preferences.get(testKey, defaultValue), defaultValue)
        preferences.edit().putInt(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
        assertEquals(preferences.get(testKey, defaultValue), value)
    }

    @Test fun getSharedPreferencesNonNullFloat() {
        val preferences = context.getSharedPreferences("prefs_float", 0)

        val testKey = "test_key"
        val defaultValue = 1f
        val value = 2f
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        assertEquals(preferences.get(testKey, defaultValue), defaultValue)
        preferences.edit().putFloat(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
        assertEquals(preferences.get(testKey, defaultValue), value)
    }

    @Test fun getSharedPreferencesNonNullLong() {
        val preferences = context.getSharedPreferences("prefs_long", 0)

        val testKey = "test_key"
        val defaultValue: Long = 1874287482374284
        val value: Long = 231231231312312312
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        assertEquals(preferences.get(testKey, defaultValue), defaultValue)
        preferences.edit().putLong(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
        assertEquals(preferences.get(testKey, defaultValue), value)
    }

    @Test fun getSharedPreferencesNonNullBoolean() {
        val preferences = context.getSharedPreferences("prefs_boolean", 0)

        val testKey = "test_key"
        val defaultValue = false
        val value = true
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        assertEquals(preferences.get(testKey, defaultValue), defaultValue)
        preferences.edit().putBoolean(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
        assertEquals(preferences.get(testKey, defaultValue), value)
    }

    @Test fun getNullableSharedPreferences() {
        val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyString = "test_key_string"
        val testString = "test"
        val defaultString = "default"

        assertNull(preferences[testKeyString])
        assertEquals(preferences[testKeyString, defaultString], defaultString)
        preferences.edit().putString(testKeyString, testString).apply()
        assertNotNull(preferences[testKeyString])
        assertEquals(preferences[testKeyString], testString)
        assertEquals(preferences[testKeyString, defaultString], testString)

        val testKeyInteger = "test_key_integer"
        val testInteger = 1
        val defaultInteger = 0

        assertNull(preferences[testKeyInteger])
        assertEquals(preferences[testKeyInteger, defaultInteger], defaultInteger)
        preferences.edit().putInt(testKeyInteger, testInteger).apply()
        assertNotNull(preferences[testKeyInteger])
        assertEquals(preferences[testKeyInteger], testInteger)
        assertEquals(preferences[testKeyInteger, defaultInteger], testInteger)

        val testKeyFloat = "test_key_float"
        val testFloat = 1f
        val defaultFloat = 0f

        assertNull(preferences[testKeyFloat])
        assertEquals(preferences[testKeyFloat, defaultFloat], defaultFloat)
        preferences.edit().putFloat(testKeyFloat, testFloat).apply()
        assertNotNull(preferences[testKeyFloat])
        assertEquals(preferences[testKeyFloat], testFloat)
        assertEquals(preferences[testKeyFloat, defaultFloat], testFloat)

        val testKeyLong = "test_key_long"
        val testLong: Long = 1234762378231647126
        val defaultLong: Long = 12126152

        assertNull(preferences[testKeyLong])
        assertEquals(preferences[testKeyLong, defaultLong], defaultLong)
        preferences.edit().putLong(testKeyLong, testLong).apply()
        assertNotNull(preferences[testKeyLong])
        assertEquals(preferences[testKeyLong]!!, testLong)
        assertEquals(preferences[testKeyLong, defaultLong], testLong)

        val testKeyBoolean = "test_key_boolean"
        val testBoolean = true
        val defaultBoolean = false

        assertNull(preferences[testKeyBoolean])
        assertEquals(preferences[testKeyBoolean, defaultBoolean], defaultBoolean)
        preferences.edit().putBoolean(testKeyBoolean, testBoolean).apply()
        assertNotNull(preferences[testKeyBoolean])
        assertEquals(preferences[testKeyBoolean], testBoolean)
        assertEquals(preferences[testKeyBoolean, defaultBoolean], testBoolean)

        val testKeyStringSet = "test_key_string_set"
        val testStringSet = setOf("test")
        val defaultStringSet = setOf("default")

        assertNull(preferences[testKeyStringSet])
        assertEquals(preferences[testKeyStringSet, defaultStringSet], defaultStringSet)
        preferences.edit().putStringSet(testKeyStringSet, testStringSet).apply()
        assertNotNull(preferences[testKeyStringSet])
        assertEquals(preferences[testKeyStringSet], testStringSet)
        assertEquals(preferences[testKeyStringSet, defaultStringSet], testStringSet)
    }

    @Test fun setSharedPreferencesString() {
        val preferences = context.getSharedPreferences("prefs_set_string", 0)

        val testKey = "test_key"
        val value = "value"
        val defaultNullableValue = null
        val defaultValue = "default"

        assertNull(preferences.getString(testKey, defaultNullableValue))
        assertNotNull(preferences.getString(testKey, defaultValue))
        preferences[testKey] = value
        assertEquals(preferences.getString(testKey, defaultValue), value)
    }

    @Test fun setSharedPreferencesInteger() {
        val preferences = context.getSharedPreferences("prefs_set_integer", 0)

        val testKey = "test_key"
        val defaultValue = 50
        val value = 8

        assertEquals(preferences.getInt(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getInt(testKey, defaultValue), value)
    }

    @Test fun setSharedPreferencesFloat() {
        val preferences = context.getSharedPreferences("prefs_set_float", 0)

        val testKey = "test_key"
        val defaultValue = 1f
        val value = 3f

        assertEquals(preferences.getFloat(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getFloat(testKey, defaultValue), value)
    }

    @Test fun setSharedPreferencesLong() {
        val preferences = context.getSharedPreferences("prefs_set_long", 0)

        val testKey = "test_key"
        val value: Long = 890898980890890808
        val defaultValue: Long = 5432434234234

        assertEquals(preferences.getLong(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getLong(testKey, defaultValue), value)
    }

    @Test fun setSharedPreferencesBoolean() {
        val preferences = context.getSharedPreferences("prefs_set_boolean", 0)

        val testKey = "test_key"
        val value = true
        val defaultValue = false

        assertFalse(preferences.getBoolean(testKey, defaultValue))
        preferences[testKey] = value
        assertTrue(preferences.getBoolean(testKey, value))
    }

    @Test fun setSharedPreferencesStringSet() {
        val preferences = context.getSharedPreferences("prefs_set_string_set", 0)

        val testKey = "test_key"
        val value = setOf("value")
        val defaultValue = setOf("default")

        assertNull(preferences.getStringSet(testKey, null))
        assertNotNull(preferences.getStringSet(testKey, defaultValue))
        preferences[testKey] = value
        assertEquals(preferences.getStringSet(testKey, defaultValue), value)
    }
}
