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
import org.junit.Assert
import org.junit.Assert.assertEquals
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

    @Test fun getInteger() {
        val preferences = context.getSharedPreferences("prefs_integer", 0)

        val defaultValue = 8
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"]!!, -1)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertEquals(preferences.get<Int>("test_key4"), -1)
    }

    @Test fun getString() {
        val preferences = context.getSharedPreferences("prefs_string", 0)

        val defaultValue = "default"
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        Assert.assertNull(preferences["test_key2"])
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        Assert.assertNull(preferences.get<String>("test_key4"))
    }

    @Test fun getBoolean() {
        val preferences = context.getSharedPreferences("prefs_boolean", 0)

        val defaultValue = true
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        Assert.assertFalse(preferences["test_key2"]!!)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        Assert.assertFalse(preferences.get("test_key4")!!)
    }

    @Test fun getFloat() {
        val preferences = context.getSharedPreferences("prefs_float", 0)
        val defaultValue = 8f
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"], -1f)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        //assertEquals(preferences.get<String>("test_key4"), -1f)
    }

    @Test fun getLong() {
        val preferences = context.getSharedPreferences("prefs_long", 0)
        val defaultValue: Long = 8978728734832743
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        assertEquals(preferences["test_key2"]!!, -1)
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        assertEquals(preferences.get<Long>("test_key4"), -1)
    }

    @Test fun getStringSet() {
        val preferences = context.getSharedPreferences("prefs_string_set", 0)

        val defaultValue = "default"
        assertEquals(preferences["test_key1", defaultValue], defaultValue)
        Assert.assertNull(preferences["test_key2"])
        assertEquals(preferences.get("test_key3", defaultValue), defaultValue)
        Assert.assertNull(preferences.get<String>("test_key4"))
    }

    @Test fun setInteger() {
        val preferences = context.getSharedPreferences("prefs_set_integer", 0)

        val value = 8
        val defaultValue = 50

        preferences["test_key1"] = value
        assertEquals(preferences.getInt("test_key1", defaultValue), value)

        preferences.set("test_key2", value)
        assertEquals(preferences.getInt("test_key2", defaultValue), value)

        preferences.set<Int>("test_key3", value)
        assertEquals(preferences.getInt("test_key3", defaultValue), value)
    }

    @Test fun setString() {
        val preferences = context.getSharedPreferences("prefs_set_string", 0)

        val value = "value"
        val defaultValue = "default"

        preferences["test_key1"] = value
        assertEquals(preferences.getString("test_key1", defaultValue), value)

        preferences.set("test_key2", value)
        assertEquals(preferences.getString("test_key2", defaultValue), value)

        preferences.set<String>("test_key3", value)
        assertEquals(preferences.getString("test_key3", defaultValue), value)
    }

    @Test fun setBoolean() {
        val preferences = context.getSharedPreferences("prefs_set_boolean", 0)

        val value = true
        val defaultValue = false

        preferences["test_key1"] = value
        Assert.assertTrue(preferences.getBoolean("test_key1", defaultValue))

        preferences.set("test_key2", value)
        Assert.assertTrue(preferences.getBoolean("test_key1", defaultValue))

        preferences.set<Boolean>("test_key3", value)
        Assert.assertTrue(preferences.getBoolean("test_key1", defaultValue))
    }

    @Test fun setFloat() {
        val preferences = context.getSharedPreferences("prefs_set_float", 0)

        val value = 8f
        val defaultValue = 5f

        preferences["test_key1"] = value
        assertEquals(preferences.getFloat("test_key1", defaultValue), value)

        preferences.set("test_key2", value)
        assertEquals(preferences.getFloat("test_key2", defaultValue), value)

        preferences.set<Float>("test_key3", value)
        assertEquals(preferences.getFloat("test_key3", defaultValue), value)
    }

    @Test fun setLong() {
        val preferences = context.getSharedPreferences("prefs_set_long", 0)

        val value: Long = 890898980890890808
        val defaultValue: Long = 5432434234234

        preferences["test_key1"] = value
        assertEquals(preferences.getLong("test_key1", defaultValue), value)

        preferences.set("test_key2", value)
        assertEquals(preferences.getLong("test_key2", defaultValue), value)

        preferences.set<Long>("test_key3", value)
        assertEquals(preferences.getLong("test_key3", defaultValue), value)
    }
}
