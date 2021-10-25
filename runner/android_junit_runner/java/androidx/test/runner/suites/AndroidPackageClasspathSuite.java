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
package androidx.test.runner.suites;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.test.runner.suites.AndroidClasspathSuite.RunnerSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Test suite that finds all JUnit3 and JUnit4 test classes in the instrumentation context aka test
 * apk, within the java package matching the target context package name aka the Android
 * application-under-test application id.
 *
 * <p>This is usually a more desirable alternative to {@link AndroidClasspathSuite}, as it avoids
 * performance penalities and potential runtime issues with scanning classes from transitive
 * dependencies.
 */
@RunWith(RunnerSuite.class)
public final class AndroidPackageClasspathSuite {

  /**
   * Only called reflectively. Do not use programmatically.
   *
   * @hide
   */
  @RestrictTo(Scope.LIBRARY)
  public AndroidPackageClasspathSuite() {}

  /**
   * Internal suite class that performs the work of class path scanning.
   *
   * @hide
   */
  @RestrictTo(Scope.LIBRARY)
  public static class RunnerSuite extends Suite {

    /**
     * Only called reflectively. Do not use programmatically.
     *
     * @hide
     */
    @RestrictTo(Scope.LIBRARY)
    public RunnerSuite(Class<?> klass, RunnerBuilder builder) throws InitializationError {
      super(
          klass,
          PackageClasspathRunnerSuite.getRunnersForClasses(
              builder, getInstrumentation().getTargetContext().getPackageName()));
    }
  }
}
