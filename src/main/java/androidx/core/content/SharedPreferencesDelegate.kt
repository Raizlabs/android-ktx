/*
 * Copyright (C) 2018 The Android Open Source Project
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
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class SharedPreferencesDelegate (
    private val context: Context,
    private val name: String? = null,
    private val mode: Int = Context.MODE_PRIVATE
) {
    operator fun getValue(any: Any, property: KProperty<*>): SharedPreferences =
        if (name != null) context.getSharedPreferences(name, mode)
        else PreferenceManager.getDefaultSharedPreferences(context)
}

class SharedPreferencesLoader(
    private val name: String? = null,
    private val mode: Int = Context.MODE_PRIVATE
) {
    operator fun provideDelegate(
        thisRef: Context,
        prop: KProperty<*>
    ): ReadOnlyProperty<Context, SharedPreferences> {
        return object : ReadOnlyProperty<Context, SharedPreferences>{
            override fun getValue(thisRef: Context, property: KProperty<*>): SharedPreferences =
                if (name != null) thisRef.getSharedPreferences(name, mode)
                else PreferenceManager.getDefaultSharedPreferences(thisRef)
        }
    }
}