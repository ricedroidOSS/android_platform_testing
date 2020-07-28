/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.server.wm.flicker.dsl

import com.android.server.wm.flicker.*
import com.android.server.wm.flicker.assertions.AssertionData
import com.android.server.wm.flicker.traces.ITraceEntry
import com.android.server.wm.flicker.traces.SubjectBase
import com.android.server.wm.flicker.traces.TraceBase

/**
 * Placeholder for the types of assertions are supported by the Flicker DSL
 *
 * Currently supports the initial state [start], the final state [end] and all states [all]
 */
@FlickerDslMarker
data class AssertionType<Subject: SubjectBase<Trace, Entry>, Trace: TraceBase<Entry>, Entry: ITraceEntry>
@JvmOverloads constructor(
    /**
     * List of trace assertions
     */
    private val _assertions: MutableList<AssertionData<Subject>> = mutableListOf()
) : List<AssertionData<Subject>> by _assertions {
    val assertions: List<AssertionData<Subject>> get() = _assertions

    /**
     * Run the assertions only in the first trace entry
     *
     * Used for assertions that check the first state in the test. For example:
     *    initial.shows(A).and().shows(B)
     *
     * This command can be used multiple times, and the results are appended
     *
     * @param name Name of the assertion to appear on errors
     * @param enabled If the assertion is enabled or not
     * @param bugId If the assertion is disabled because of a bug, which bug is it.
     * @param assertion Assertion command
     */
    @JvmOverloads
    fun start(
        name: String = "",
        enabled: Boolean = true,
        bugId: Int = 0,
        assertion: Subject.() -> Subject
    ) {
        add(name, enabled, bugId) { assertion().inTheBeginning() }
    }

    /**
     * Run the assertions only in the last trace entry
     *
     * Used for assertions that check the last state in the test. For example:
     *    endState.shows(A).and().shows(B)
     *
     * This command can be used multiple times, and the results are appended
     *
     * @param name Name of the assertion to appear on errors
     * @param enabled If the assertion is enabled or not
     * @param bugId If the assertion is disabled because of a bug, which bug is it.
     * @param assertion Assertion command
     */
    @JvmOverloads
    fun end(
        name: String = "",
        enabled: Boolean = true,
        bugId: Int = 0,
        assertion: Subject.() -> Subject
    ) {
        add(name, enabled, bugId) { assertion().atTheEnd() }
    }

    /**
     * Run the assertions in all trace entries
     *
     * Used for assertions that check the last state in the test. For example:
     *    trace.shows(A).then().hides(A).and().shows(B)
     *
     * This command can be used multiple times, and the results are appended
     *
     * @param name Name of the assertion to appear on errors
     * @param enabled If the assertion is enabled or not
     * @param bugId If the assertion is disabled because of a bug, which bug is it.
     * @param assertion Assertion command
     */
    @JvmOverloads
    fun all(
        name: String = "",
        enabled: Boolean = true,
        bugId: Int = 0,
        assertion: Subject.() -> Subject
    ) {
        add(name, enabled, bugId) { assertion().forAllEntries() }
    }

    private fun add(name: String, enabled: Boolean, bugId: Int, assertion: Subject.() -> Unit) {
        _assertions.add(AssertionData(name, enabled, bugId, assertion))
    }
}