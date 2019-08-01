/*
 * Copyright (C) 2019 The Android Open Source Project
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
package android.platform.test.scenario.businesscard;

import android.platform.helpers.HelperAccessor;
import android.platform.helpers.IBusinessCardHelper;
import android.platform.test.rule.NaturalOrientationRule;
import android.platform.test.scenario.annotation.Scenario;

import org.junit.AfterClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/** Opens the Business Card application and exits after. */
@Scenario
@RunWith(JUnit4.class)
public class OpenApp {
    // Class-level rules
    @ClassRule public static NaturalOrientationRule orientationRule = new NaturalOrientationRule();

    private static HelperAccessor<IBusinessCardHelper> sHelper =
            new HelperAccessor<>(IBusinessCardHelper.class);

    @Test
    public void testOpen() {
        sHelper.get().open();
    }

    @AfterClass
    public static void closeApp() {
        sHelper.get().exit();
    }
}
