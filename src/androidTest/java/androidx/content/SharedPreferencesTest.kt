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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SharedPreferencesTest {

    private val context = InstrumentationRegistry.getContext()

    @Test fun edit() {
        val preferences = context.getSharedPreferences("prefs", 0)

        preferences.edit {
            putString("test_key1", "test_value")
            putInt("test_key2", 100)
        }

        assertEquals("test_value", preferences.getString("test_key1", null))
        assertEquals(100, preferences.getInt("test_key2", 0))
    }

    @Test fun getSharedPreferencesInteger() {
        val preferences = context.getSharedPreferences("prefs_integer", 0)

        val defaultValue = 8
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"]!!, -1)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertEquals(preferences.get<Int>("test_key4"), -1)
    }

    @Test fun getSharedPreferencesString() {
        val preferences = context.getSharedPreferences("prefs_string", 0)

        val defaultValue = "default"
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertNull(preferences["test_key2"])
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertNull(preferences.get<String>("test_key4"))
    }

    @Test fun getSharedPreferencesBoolean() {
        val preferences = context.getSharedPreferences("prefs_boolean", 0)

        val defaultValue = true
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertFalse(preferences["test_key2"]!!)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertFalse(preferences.get("test_key4")!!)
    }

    @Test fun getSharedPreferencesFloat() {
        val preferences = context.getSharedPreferences("prefs_float", 0)
        val defaultValue = 8f

        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"], -1f)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertEquals(preferences.get<String>("test_key4"), -1f)
    }

    @Test fun getSharedPreferencesLong() {
        val preferences = context.getSharedPreferences("prefs_long", 0)
        val defaultValue: Long = 8978728734832743

        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"]!!, -1)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertEquals(preferences.get<Long>("test_key4"), -1)
    }

    @Test fun getSharedPreferencesStringSet() {
        val preferences = context.getSharedPreferences("prefs_string_set", 0)

        val defaultValue = "default"
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertNull(preferences["test_key2"])
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertNull(preferences.get<String>("test_key4"))
    }

    @Test fun setSharedPreferencesInteger() {
        val preferences = context.getSharedPreferences("prefs_set_integer", 0)

        val value = 8
        val defaultValue = 50

        preferences["test_key"] = value
        assertEquals(preferences.getInt("test_key1", defaultValue), value)
    }

    @Test fun setSharedPreferencesString() {
        val preferences = context.getSharedPreferences("prefs_set_string", 0)

        val value = "value"
        val defaultValue = "default"

        preferences["test_key"] = value
        assertEquals(preferences.getString("test_key1", defaultValue), value)
    }

    @Test fun setSharedPreferencesBoolean() {
        val preferences = context.getSharedPreferences("prefs_set_boolean", 0)

        val value = true
        val defaultValue = false

        preferences["test_key1"] = value
        assertTrue(preferences.getBoolean("test_key1", defaultValue))

        assertFalse(preferences.getBoolean("test_key2", defaultValue))
    }

    @Test fun setSharedPreferencesFloat() {
        val preferences = context.getSharedPreferences("prefs_set_float", 0)

        val defaultValue = 1f
        val testDefaultValue = 2f
        val testKey = "test_key"

        assertNotEquals(preferences[testKey, defaultValue], testDefaultValue)
        assertEquals(preferences[testKey, defaultValue], defaultValue)

        val value = 3f

        preferences[testKey] = value
        assertEquals(preferences.getFloat(testKey, defaultValue), value)
    }

    @Test fun setSharedPreferencesLong() {
        val preferences = context.getSharedPreferences("prefs_set_long", 0)

        val value: Long = 890898980890890808
        val defaultValue: Long = 5432434234234
        val testKey = "test_key"

        preferences[testKey] = value
        assertEquals(preferences.getLong(testKey, defaultValue), value)
    }
}
