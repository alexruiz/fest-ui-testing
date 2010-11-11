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
package org.fest.ui.testing.junit.rule;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.ui.testing.junit.rule.ScreenshotOnFailure.newRule;
import static org.mockito.Mockito.mock;

import org.fest.ui.testing.junit.rule.ScreenshotOnFailure;
import org.fest.ui.testing.junit.rule.ScreenshotOnFailure.ScreenshotOnFailureStatement;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Tests for <code>{@link ScreenshotOnFailure#apply(Statement, FrameworkMethod, Object)}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenshotOnFailure_apply_Test {

  private static Statement base;
  private static FrameworkMethod method;

  private ScreenshotOnFailure rule;

  @BeforeClass public static void setUpOnce() {
    base = mock(Statement.class);
    method = mock(FrameworkMethod.class);
  }

  @Test public void should_return_a_ScreenshotOnFailureStatement() {
    rule = newRule();
    Statement applied = rule.apply(base, method, new Object());
    assertThat(applied).isInstanceOf(ScreenshotOnFailureStatement.class);
    ScreenshotOnFailureStatement statement = (ScreenshotOnFailureStatement) applied;
    assertThat(statement.base).isSameAs(base);
    assertThat(statement.method).isSameAs(method);
  }

  @Test public void should_return_base_Statement_if_ScreenshotFilePathCreator_is_null() {
    rule = new ScreenshotOnFailure(null);
    Statement applied = rule.apply(base, method, new Object());
    assertThat(applied).isSameAs(base);
  }
}
