/*
 * Copyright (C) 2021 The Android Open Source Project
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

package android.device.collectors;

import android.os.Bundle;
import android.device.collectors.annotations.OptionClass;

import com.android.helpers.LyricMemProfilerHelper;

@OptionClass(alias = "lyric-mem-profiler-collector")
public class LyricMemProfilerCollector extends BaseCollectionListener<Integer> {

    private static final String TAG = LyricMemProfilerCollector.class.getSimpleName();
    private static final String PROFILE_PERIOD_KEY = "profile-period-ms-enable";
    private LyricMemProfilerHelper mHelper = new LyricMemProfilerHelper();

    public LyricMemProfilerCollector() {
        createHelperInstance(mHelper);
    }

    /** Adds the options for total pss collector. */
    @Override
    public void setupAdditionalArgs() {
        Bundle args = getArgsBundle();
        String argString = args.getString(PROFILE_PERIOD_KEY);
        if (argString != null) {
            mHelper.setProfilePeriodMs(Integer.parseInt(argString));
        }
    }
}