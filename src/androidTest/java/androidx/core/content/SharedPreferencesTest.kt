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

package androidx.core.content

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.util.Log
import androidx.testutils.assertThrows
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Arrays

class SharedPreferencesTest {
    private val context = InstrumentationRegistry.getContext()

    @Test fun editApply() {
        val preferences = context.getSharedPreferences("prefs", 0)

        preferences.edit {
            putString("test_key1", "test_value")
            putInt("test_key2", 100)
        }

        assertEquals("test_value", preferences.getString("test_key1", null))
        assertEquals(100, preferences.getInt("test_key2", 0))
    }

    @Test fun editCommit() {
        val preferences = context.getSharedPreferences("prefs", 0)
        preferences.edit(commit = true) {
            putString("test_key1", "test_value")
            putInt("test_key2", 100)
        }

        assertEquals("test_value", preferences.getString("test_key1", null))
        assertEquals(100, preferences.getInt("test_key2", 0))
    }
    @Test fun getOperator() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertEquals(true, preferences[Boolean::class.java, "boolean", true])
        preferences.edit { putBoolean("boolean", false) }
        assertEquals(false, preferences[Boolean::class.java, "boolean", true])

        assertEquals(0.1f, preferences[Float::class.java, "float", 0.1f])
        preferences.edit { putFloat("float", 0.2f) }
        assertEquals(0.2f, preferences[Float::class.java, "float", 0.1f])

        assertEquals(100, preferences[Int::class.java, "int", 100])
        preferences.edit { putInt("int", 101) }
        assertEquals(101, preferences[Int::class.java, "int", 100])

        assertEquals(123456789L, preferences[Long::class.java, "long", 123456789L])
        preferences.edit { putLong("long", 123456788L) }
        assertEquals(123456788L, preferences[Long::class.java, "long", 123456789L])

        assertEquals("default", preferences[String::class.java, "string", "default"])
        preferences.edit { putString("string", "test") }
        assertEquals("test", preferences[String::class.java, "string", "default"])

        val defaultSet = HashSet<String>(Arrays.asList("1", "2"))
        val testSet = HashSet<String>(Arrays.asList("3", "4"))

        assertEquals(defaultSet, preferences[Set::class.java, "stringSet", defaultSet])
        preferences.edit { putStringSet("stringSet", testSet) }
        assertEquals(testSet, preferences[Set::class.java, "stringSet", defaultSet])
    }

    @Test fun setOperator() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertEquals(false, preferences.getBoolean("boolean", false))
        preferences[Boolean::class.java, "boolean"] = true
        assertEquals(true, preferences.getBoolean("boolean", false))

        assertEquals(0.2f, preferences.getFloat("float", 0.2f))
        preferences[Float::class.java, "float"] = 0.1f
        assertEquals(0.1f, preferences.getFloat("float", 0.2f))

        assertEquals(101, preferences.getInt("int", 101))
        preferences[Int::class.java, "int"] = 100
        assertEquals(100, preferences.getInt("int", 101))

        assertEquals(123456788L, preferences.getLong("long", 123456788L))
        preferences[Long::class.java, "long"] = 123456789L
        assertEquals(123456789L, preferences.getLong("long", 123456788L))

        assertEquals("default", preferences.getString("string", "default"))
        preferences[String::class.java, "string"] = "test_value"
        assertEquals("test_value", preferences.getString("string", "default"))

        val defaultSet = HashSet<String>(Arrays.asList("1", "2"))
        val testSet = HashSet<String>(Arrays.asList("3", "4"))

        assertEquals(defaultSet, preferences.getStringSet("stringSet", defaultSet))
        preferences[Set::class.java, "stringSet"] = testSet
        assertEquals(testSet, preferences.getStringSet("stringSet", defaultSet))
    }

    @Test fun getInlinedOperator() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertEquals(true, preferences["boolean", true])
        preferences.edit { putBoolean("boolean", false) }
        assertEquals(false, preferences["boolean", true])

        assertEquals(0.1f, preferences["float", 0.1f])
        preferences.edit { putFloat("float", 0.2f) }
        assertEquals(0.2f, preferences["float", 0.1f])

        assertEquals(100, preferences["int", 100])
        preferences.edit { putInt("int", 101) }
        assertEquals(101, preferences["int", 100])

        assertEquals(123456789L, preferences["long", 123456789L])
        preferences.edit { putLong("long", 123456788L) }
        assertEquals(123456788L, preferences["long", 123456789L])

        assertEquals("default", preferences["string", "default"])
        preferences.edit { putString("string", "test") }
        assertEquals("test", preferences["string", "default"])

        val defaultSet = HashSet<String>(Arrays.asList("1", "2"))
        val testSet = HashSet<String>(Arrays.asList("3", "4"))

        assertEquals(defaultSet, preferences["stringSet", defaultSet])
        preferences.edit { putStringSet("stringSet", testSet) }
        assertEquals(testSet, preferences["stringSet", defaultSet])
    }

    @Test fun setInlinedOperator() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertEquals(false, preferences.getBoolean("boolean", false))
        preferences["boolean"] = true
        assertEquals(true, preferences.getBoolean("boolean", false))

        assertEquals(0.2f, preferences.getFloat("float", 0.2f))
        preferences["float"] = 0.1f
        assertEquals(0.1f, preferences.getFloat("float", 0.2f))

        assertEquals(101, preferences.getInt("int", 101))
        preferences["int"] = 100
        assertEquals(100, preferences.getInt("int", 101))

        assertEquals(123456788L, preferences.getLong("long", 123456788L))
        preferences["long"] = 123456789L
        assertEquals(123456789L, preferences.getLong("long", 123456788L))

        assertEquals("default", preferences.getString("string", "default"))
        preferences["string"] = "test_value"
        assertEquals("test_value", preferences.getString("string", "default"))

        val defaultSet = HashSet<String>(Arrays.asList("1", "2"))
        val testSet = HashSet<String>(Arrays.asList("3", "4"))

        assertEquals(defaultSet, preferences.getStringSet("stringSet", defaultSet))
        preferences["stringSet"] = testSet
        assertEquals(testSet, preferences.getStringSet("stringSet", defaultSet))
    }

    @Test fun getOperatorsUnsupportedType() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertThrows<IllegalArgumentException> {
            Log.d("", preferences[Context::class.java, "key", context].toString())
        }

        assertThrows<IllegalArgumentException> {
            Log.d("", preferences["key", context].toString())
        }
    }

    @Test fun setOperatorsUnsupportedType() {
        val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        assertThrows<IllegalArgumentException> { preferences[Context::class.java, "key"] = context }
        assertThrows<IllegalArgumentException> { preferences["key"] = context }

        assertThrows<IllegalArgumentException> { preferences[Any::class.java, "key"] = Any() }
        assertThrows<IllegalArgumentException> { preferences["key"] = Any() }
    }
}
