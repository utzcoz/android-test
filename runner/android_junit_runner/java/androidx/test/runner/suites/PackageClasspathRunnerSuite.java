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

import static androidx.test.internal.runner.ClassPathScanner.getDefaultClasspaths;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import androidx.annotation.RestrictTo;
import androidx.annotation.RestrictTo.Scope;
import androidx.test.internal.runner.ClassPathScanner;
import androidx.test.internal.runner.ClassPathScanner.InclusivePackageNamesFilter;
import androidx.test.internal.runner.ErrorReportingRunner;
import androidx.test.internal.runner.TestLoader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;

/**
 * Runner that finds all JUnit3 and JUnit4 test classes within a java package.
 *
 * <p>*
 *
 * <p>Example usage: * *
 *
 * <pre>
 *  *   package com.android.example;
 *  *
 *  *   {@literal @}RunWith(PackageClasspathRunnerSuite.class)
 *  *   public class AllTests {
 *  *   }</pre>
 *
 * This will find all JUnit3 and JUnit4 classes in com.android.example.* and execute them.
 */
public final class PackageClasspathRunnerSuite extends Suite {

  /**
   * Only called reflectively. Do not use programmatically.
   *
   * @hide
   */
  @RestrictTo(Scope.LIBRARY)
  public PackageClasspathRunnerSuite(Class<?> klass, RunnerBuilder builder)
      throws InitializationError {
    super(klass, getRunnersForClasses(builder, klass.getPackageName()));
  }

  static List<Runner> getRunnersForClasses(RunnerBuilder builder, String packageName) {
    try {
      Collection<String> classNames =
          new ClassPathScanner(getDefaultClasspaths(getInstrumentation()))
              .getClassPathEntries(new InclusivePackageNamesFilter(Arrays.asList(packageName)));
      return TestLoader.Factory.create(null, builder, true).getRunnersFor(classNames);
    } catch (IOException e) {
      return Arrays.asList(
          new ErrorReportingRunner(
              getInstrumentation().getContext().getPackageName(),
              new RuntimeException(
                  "Failed to perform classpath scanning to determine tests to run", e)));
    }
  }
}
