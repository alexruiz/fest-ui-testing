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
import static org.fest.reflect.core.Reflection.method;
import static org.fest.util.Collections.list;
import static org.junit.runner.Description.createTestDescription;
import static org.mockito.Mockito.*;

import java.util.List;

import org.fest.ui.testing.junit.test.D;
import org.junit.*;
import org.junit.experimental.categories.Categories.CategoryFilter;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.FrameworkMethod;

/**
 * Tests for <code>{@link GuiTestFilter#isGuiTest(FrameworkMethod)}</code>.
 *
 * @author Alex Ruiz
 */
@RunWith(Parameterized.class)
public class GuiTestFilter_isGuiTest_with_mocks_Test {

  private final boolean isGuiTest;

  @Parameters public static List<Object[]> parameters() {
    return list(new Object[][] { 
        { true }, { false }
    });
  }
  
  public GuiTestFilter_isGuiTest_with_mocks_Test(boolean isGuiTest) {
    this.isGuiTest = isGuiTest;
  }
  
  private CategoryFilter categoryFilter;
  private GuiTestFilter filter;
  
  @Before public void setUp() {
    categoryFilter = mock(CategoryFilter.class);
    filter = new GuiTestFilter(categoryFilter);
  }
  
  @Test public void should_use_CategoryFilter_to_verify_a_test_belongs_to_some_category() {
    Description testDescription = createTestDescription(D.class, "d");
    when(categoryFilter.shouldRun(testDescription)).thenReturn(isGuiTest);
    FrameworkMethod method = new FrameworkMethod(method("d").in(D.class).info());
    assertThat(filter.isGuiTest(method)).isEqualTo(isGuiTest);
  }
}
