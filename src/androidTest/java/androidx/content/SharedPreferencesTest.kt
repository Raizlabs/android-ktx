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
import androidx.assertThrows
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
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

    @Test fun delegate() {
        val preferences = context.getSharedPreferences("unsupported", 0)
        assertThrows<UnsupportedOperationException> { preferences.get<DummyObject>(" ") }
        assertThrows<UnsupportedOperationException> { preferences[" ", DummyObject()] }
    }

    @Test fun getSharedPreferencesNonNullInteger() {
        val preferences = context.getSharedPreferences("prefs_integer", 0)

        val testKey = "test_key"
        val defaultValue = 1
        val value = 2
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        preferences.edit().putInt(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
    }

    @Test fun getSharedPreferencesNonNullFloat() {
        val preferences = context.getSharedPreferences("prefs_float", 0)

        val testKey = "test_key"
        val defaultValue = 1f
        val value = 2f
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        preferences.edit().putFloat(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
    }

    @Test fun getSharedPreferencesNonNullLong() {
        val preferences = context.getSharedPreferences("prefs_long", 0)

        val testKey = "test_key"
        val defaultValue: Long = 1874287482374284
        val value: Long = 231231231312312312
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        preferences.edit().putLong(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
    }

    @Test fun getSharedPreferencesNonNullBoolean() {
        val preferences = context.getSharedPreferences("prefs_boolean", 0)

        val testKey = "test_key"
        val defaultValue = false
        val value = true
        assertEquals(preferences[testKey, defaultValue], defaultValue)
        preferences.edit().putBoolean(testKey, value).apply()
        assertEquals(preferences[testKey, defaultValue], value)
    }

    @Test fun getSharedPreferencesNullableString() {

        val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyString = "test_key_string"
        val testString = "test"
        val defaultString = "default"

        assertNull(preferences.get<String>(testKeyString))
        assertNull(preferences[testKeyString])
        assertEquals(preferences[testKeyString, defaultString], defaultString)

        preferences.edit().putString(testKeyString, testString).apply()

        assertNotNull(preferences.get<String>(testKeyString))
        assertNotNull(preferences[testKeyString])
        assertEquals(preferences[testKeyString], testString)
        assertEquals(preferences[testKeyString, defaultString], testString)
        assertEquals(preferences.get<String>(testKeyString), testString)

    }

    @Test fun getSharedPreferencesNullableInteger() {

        /*val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyInteger = "test_key_integer"
        val testInteger = 1
        val defaultInteger = 0

        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyInteger])
        }
        assertEquals(preferences[testKeyInteger, defaultInteger], defaultInteger)
        preferences.edit().putInt(testKeyInteger, testInteger).apply()
        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyInteger])
        }
        assertEquals(preferences[testKeyInteger], testInteger)
        assertEquals(preferences[testKeyInteger, defaultInteger], testInteger)*/
    }

    @Test fun getSharedPreferencesNullableFloat() {

        /*val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyFloat = "test_key_float"
        val testFloat = 1f
        val defaultFloat = 0f

        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyFloat])
        }
        assertEquals(preferences[testKeyFloat, defaultFloat], defaultFloat)
        preferences.edit().putFloat(testKeyFloat, testFloat).apply()
        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyFloat])
        }
        assertEquals(preferences[testKeyFloat], testFloat)
        assertEquals(preferences[testKeyFloat, defaultFloat], testFloat)*/
    }

    @Test fun getSharedPreferencesNullableLong() {

        /*val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyLong = "test_key_long"
        val testLong: Long = 1234762378231647126
        val defaultLong: Long = 12126152

        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyLong])
        }
        assertEquals(preferences[testKeyLong, defaultLong], defaultLong)
        preferences.edit().putLong(testKeyLong, testLong).apply()
        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyLong])
        }
        assertEquals(preferences[testKeyLong]!!, testLong)
        assertEquals(preferences[testKeyLong, defaultLong], testLong)*/
    }

    @Test fun getNullableBoolean() {

        /*val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyBoolean = "test_key_boolean"
        val testBoolean = true
        val defaultBoolean = false

        assertThrows<UnsupportedOperationException> {
            assertNull(preferences[testKeyBoolean])
        }
        assertEquals(preferences[testKeyBoolean, defaultBoolean], defaultBoolean)
        preferences.edit().putBoolean(testKeyBoolean, testBoolean).apply()
        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyBoolean])
        }
        assertEquals(preferences[testKeyBoolean], testBoolean)
        assertEquals(preferences[testKeyBoolean, defaultBoolean], testBoolean)*/
    }

    @Test fun getNullableStringSet() {

        /*val preferences = context.getSharedPreferences("prefs_nullable", 0)

        val testKeyStringSet = "test_key_string_set"
        val testStringSet = setOf("test")
        val defaultStringSet = setOf("default")

        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyStringSet])
        }
        assertEquals(preferences[testKeyStringSet, defaultStringSet], defaultStringSet)
        preferences.edit().putStringSet(testKeyStringSet, testStringSet).apply()
        assertThrows<UnsupportedOperationException>{
            assertNull(preferences[testKeyStringSet])
        }
        assertEquals(preferences[testKeyStringSet], testStringSet)
        assertEquals(preferences[testKeyStringSet, defaultStringSet], testStringSet)*/
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

    @Test fun setInteger() {
        val preferences = context.getSharedPreferences("prefs_set_integer", 0)

        val testKey = "test_key"
        val defaultValue = 50
        val value = 8

        assertEquals(preferences.getInt(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getInt(testKey, defaultValue), value)
    }

    @Test fun setFloat() {
        val preferences = context.getSharedPreferences("prefs_set_float", 0)

        val testKey = "test_key"
        val defaultValue = 1f
        val value = 3f

        assertEquals(preferences.getFloat(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getFloat(testKey, defaultValue), value)
    }

    @Test fun setLong() {
        val preferences = context.getSharedPreferences("prefs_set_long", 0)

        val testKey = "test_key"
        val value: Long = 890898980890890808
        val defaultValue: Long = 5432434234234

        assertEquals(preferences.getLong(testKey, defaultValue), defaultValue)
        preferences[testKey] = value
        assertEquals(preferences.getLong(testKey, defaultValue), value)
    }

    @Test fun setBoolean() {
        val preferences = context.getSharedPreferences("prefs_set_boolean", 0)

        val testKey = "test_key"
        val value = true
        val defaultValue = false

        assertFalse(preferences.getBoolean(testKey, defaultValue))
        preferences[testKey] = value
        assertTrue(preferences.getBoolean(testKey, value))
    }

    @Test fun setStringSet() {
        val preferences = context.getSharedPreferences("prefs_set_string_set", 0)

        val testKey = "test_key"
        val value = setOf("value")
        val defaultValue = setOf("default")

        assertNull(preferences.getStringSet(testKey, null))
        assertNotNull(preferences.getStringSet(testKey, defaultValue))
        preferences[testKey] = value
        assertEquals(preferences.getStringSet(testKey, defaultValue), value)
    }

    @Test fun getUnsupportedClasses() {
        val preferences = context.getSharedPreferences("unsupported", 0)
        assertThrows<UnsupportedOperationException> { preferences.get<DummyObject>(" ") }
        assertThrows<UnsupportedOperationException> { preferences[" ", DummyObject()] }
    }

    private class DummyObject
}
