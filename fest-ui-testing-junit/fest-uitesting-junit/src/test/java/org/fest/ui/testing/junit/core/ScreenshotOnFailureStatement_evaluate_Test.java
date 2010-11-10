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
package org.fest.ui.testing.junit.core;

import static org.fest.reflect.core.Reflection.method;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.fest.ui.testing.junit.core.ScreenshotOnFailure.ScreenshotOnFailureStatement;
import org.fest.ui.testing.screenshot.ScreenshotTaker;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Tests for <code>{@link ScreenshotOnFailureStatement#evaluate()}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenshotOnFailureStatement_evaluate_Test {

  @Rule public ExpectedException thrown = none();

  private static Throwable toBeThrown;
  private static Method realMethod;

  private Statement base;
  private FrameworkMethod method;
  private GuiTestFilter filter;
  private ScreenshotTaker screenshotTaker;

  private ScreenshotOnFailureStatement statement;

  @BeforeClass public static void setUpOnce() {
    toBeThrown = new Throwable("On purpose");
    realMethod = method("toString").in(Object.class).info();
  }

  @Before public void setUp() {
    base = mock(Statement.class);
    method = mock(FrameworkMethod.class);
    filter = mock(GuiTestFilter.class);
    screenshotTaker = mock(ScreenshotTaker.class);
    statement = new ScreenshotOnFailureStatement(base, method);
    statement.guiTestFilter = filter;
    statement.screenshotTaker = screenshotTaker;
  }

  @Test public void should_take_screenshot_if_test_is_GuiTest() throws Throwable {
    doThrow(toBeThrown).when(base).evaluate();
    when(filter.isGuiTest(method)).thenReturn(true);
    when(method.getMethod()).thenReturn(realMethod);
    expectErrorWhenEvaluatingStatement();
    statement.evaluate();
    verify(screenshotTaker).takeScreenshot(realMethod);
  }

  @Test public void should_not_take_screenshot_if_test_is_not_GuiTest() throws Throwable {
    doThrow(toBeThrown).when(base).evaluate();
    when(filter.isGuiTest(method)).thenReturn(false);
    expectErrorWhenEvaluatingStatement();
    statement.evaluate();
    verifyZeroInteractions(screenshotTaker);
  }

  private void expectErrorWhenEvaluatingStatement() {
    thrown.expect(toBeThrown.getClass());
    thrown.expectMessage(toBeThrown.getMessage());
  }

}
