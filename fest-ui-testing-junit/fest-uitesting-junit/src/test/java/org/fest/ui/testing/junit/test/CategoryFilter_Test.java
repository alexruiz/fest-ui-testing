/*
 * Created on Nov 9, 2010
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2010 the original author or authors.
 */
package org.fest.ui.testing.junit.test;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.method;
import static org.junit.runner.Description.*;

import java.lang.reflect.Method;

import org.fest.ui.testing.junit.category.GuiTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Categories.CategoryFilter;

/**
 * @author Alex Ruiz
 */
public class CategoryFilter_Test {

  private CategoryFilter filter;

  @Before public void setUp() {
    filter = new CategoryFilter(GuiTest.class, null);
  }

  @Test public void should_run_A() {
    boolean shouldRun = filter.shouldRun(createSuiteDescription(A.class));
    assertThat(shouldRun).isTrue();
  }

  @Test public void should_not_run_B() {
    boolean shouldRun = filter.shouldRun(createSuiteDescription(B.class));
    assertThat(shouldRun).isFalse();
  }

  @Test public void should_run_C() {
    boolean shouldRun = filter.shouldRun(createSuiteDescription(C.class));
    assertThat(shouldRun).isTrue();
  }

  @Test public void should_run_a_from_C() {
    Class<?> testClass = C.class;
    Method method = method("a").in(testClass).info();
    boolean shouldRun = filter.shouldRun(createTestDescription(testClass, method.getName(), method.getAnnotations()));
    assertThat(shouldRun).isTrue();
  }

  @Test public void should_run_d() {
    Class<?> testClass = D.class;
    Method method = method("d").in(testClass).info();
    boolean shouldRun = filter.shouldRun(createTestDescription(testClass, method.getName(), method.getAnnotations()));
    assertThat(shouldRun).isTrue();
  }

  @Test public void should_run_d_from_E() {
    Class<?> testClass = E.class;
    Method method = method("d").in(testClass).info();
    boolean shouldRun = filter.shouldRun(createTestDescription(testClass, method.getName(), method.getAnnotations()));
    assertThat(shouldRun).isTrue();
  }

  @Test public void should_run_e() {
    Class<?> testClass = E.class;
    Method method = method("e").in(testClass).info();
    boolean shouldRun = filter.shouldRun(createTestDescription(testClass, method.getName(), method.getAnnotations()));
    assertThat(shouldRun).isTrue();
  }
}
