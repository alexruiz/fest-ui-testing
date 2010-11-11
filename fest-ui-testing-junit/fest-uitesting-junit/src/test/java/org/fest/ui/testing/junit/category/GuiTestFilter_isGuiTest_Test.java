/*
 * Created on Nov 11, 2010
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
package org.fest.ui.testing.junit.category;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.*;

import java.lang.reflect.Method;

import org.fest.ui.testing.junit.test.*;
import org.junit.*;
import org.junit.runners.model.FrameworkMethod;

/**
 * Tests for <code>{@link GuiTestFilter#isGuiTest(FrameworkMethod)}</code>.
 *
 * @author Alex Ruiz
 */
public class GuiTestFilter_isGuiTest_Test {

  private GuiTestFilter filter;
  
  @Before public void setUp() {
    filter = GuiTestFilter.instance();
  }
  
  @Test public void should_return_false_if_method_is_null() {
    assertThat(filter.isGuiTest(null)).isFalse();
  }
  
  @Test public void should_include_GuiTest_category() {
    Class<?> includedCategory = field("fIncluded").ofType(Class.class).in(filter.categoryFilter).get();
    assertThat(includedCategory).isEqualTo(GuiTest.class);
  }
  
  @Test public void should_return_true_if_test_class_is_GuiTest() {
    Method realMethod = method("a").in(A.class).info();
    FrameworkMethod method = new FrameworkMethod(realMethod);
    assertThat(filter.isGuiTest(method)).isTrue();
  }
  
  @Test public void should_return_false_if_test_class_is_not_GuiTest() {
    Method realMethod = method("b").in(B.class).info();
    FrameworkMethod method = new FrameworkMethod(realMethod);
    assertThat(filter.isGuiTest(method)).isFalse();
  }
  
  @Test public void should_return_true_if_test_method_is_GuiTest() {
    Method realMethod = method("d").in(D.class).info();
    FrameworkMethod method = new FrameworkMethod(realMethod);
    assertThat(filter.isGuiTest(method)).isTrue();
  }
}
