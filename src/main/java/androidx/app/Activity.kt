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

@file:Suppress("unused")

package androidx.app

import android.app.Activity
import android.app.FragmentTransaction
import android.os.Build
import android.support.annotation.RequiresApi

/**
 * Commit a fragment transaction
 * @see FragmentTransaction.commit
 */
fun Activity.commit(unit: FragmentTransaction.() -> Unit = {}) : Int =
    fragmentManager.commit(unit)

/**
 * Commit a fragment transaction Allowing State Loss
 * @see FragmentTransaction.commitAllowingStateLoss
 */
fun Activity.commitAllowingStateLoss(unit: FragmentTransaction.() -> Unit = {}) : Int =
    fragmentManager.commitAllowingStateLoss(unit)

/**
 * Commit a fragment transaction Now
 * @see FragmentTransaction.commitNow
 */
@RequiresApi(Build.VERSION_CODES.N)
fun Activity.commitNow(unit: FragmentTransaction.() -> Unit = {}) =
    fragmentManager.commitNow(unit)

/**
 * Commit a fragment transaction Now Allowing State Loss
 * @see FragmentTransaction.commitNowAllowingStateLoss
 */
@RequiresApi(Build.VERSION_CODES.N)
fun Activity.commitNowAllowingStateLoss(unit: FragmentTransaction.() -> Unit = {}) =
    fragmentManager.commitNowAllowingStateLoss(unit)