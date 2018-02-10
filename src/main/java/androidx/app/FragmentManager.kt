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

package androidx.app

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Commit a fragment transaction
 * @see FragmentTransaction.commit
 */
fun FragmentManager.commit(unit: FragmentTransaction.() -> Unit = {}) : Int =
    beginTransaction().apply(unit).commit()

/**
 * Commit a fragment transaction Allowing State Loss
 * @see FragmentTransaction.commitAllowingStateLoss
 */
fun FragmentManager.commitAllowingStateLoss(unit: FragmentTransaction.() -> Unit = {}) : Int =
    beginTransaction().apply(unit).commitAllowingStateLoss()

/**
 * Commit a fragment transaction Now
 * @see FragmentTransaction.commitNow
 */
@RequiresApi(Build.VERSION_CODES.N)
fun FragmentManager.commitNow(unit: FragmentTransaction.() -> Unit = {}) =
    beginTransaction().apply(unit).commitNow()

/**
 * Commit a fragment transaction Now Allowing State Loss
 * @see FragmentTransaction.commitNowAllowingStateLoss
 */
@RequiresApi(Build.VERSION_CODES.N)
fun FragmentManager.commitNowAllowingStateLoss(unit: FragmentTransaction.() -> Unit = {}) =
    beginTransaction().apply(unit).commitNowAllowingStateLoss()