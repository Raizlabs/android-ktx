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
import kotlin.reflect.KProperty

class SharedPreferencesProperty<T : Any>(
    context: Context,
    name: String? = null,
    mode: Int = Context.MODE_PRIVATE,
    private val key: String,
    private val defaultValue: T?,
    private val clazz: Class<T>
) {
    private val preferences by context.bindSharedPreferences(name, mode)

    operator fun getValue(thisRef: Any, property: KProperty<*>): T? =
        preferences[clazz, key, defaultValue]

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        preferences[clazz, key] = value
    }
}