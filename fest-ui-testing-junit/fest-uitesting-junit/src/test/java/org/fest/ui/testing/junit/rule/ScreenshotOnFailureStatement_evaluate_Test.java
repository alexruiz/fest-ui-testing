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

import static org.fest.reflect.core.Reflection.method;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.fest.ui.testing.junit.category.GuiTestFilter;
import org.fest.ui.testing.junit.rule.ScreenshotOnFailure.ScreenshotOnFailureStatement;
import org.fest.ui.testing.screenshot.DesktopCamera;
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

  private Statement baseStatement;
  private FrameworkMethod method;
  private FilePathFactory pathFactory;

  private ScreenshotOnFailureStatement statement;

  @BeforeClass public static void setUpOnce() {
    toBeThrown = new Throwable("On purpose");
    realMethod = method("toString").in(Object.class).info();
  }

  @Before public void setUp() {
    baseStatement = mock(Statement.class);
    method = mock(FrameworkMethod.class);
    pathFactory = mock(FilePathFactory.class);
    ScreenshotOnFailure rule = new ScreenshotOnFailure(pathFactory);
    statement = (ScreenshotOnFailureStatement) rule.apply(baseStatement, method, new Object());
    statement.guiTestFilter = mock(GuiTestFilter.class);
    statement.desktopCamera = mock(DesktopCamera.class);
  }

  @Test public void should_not_take_screenshot_if_test_does_not_fail() throws Throwable {
    statement.evaluate();
    verify(baseStatement).evaluate();
    verifyZeroInteractions(method, pathFactory, statement.guiTestFilter, statement.desktopCamera);
  }

  @Test public void should_take_screenshot_if_failing_test_is_GuiTest() throws Throwable {
    doThrow(toBeThrown).when(baseStatement).evaluate();
    when(statement.guiTestFilter.isGuiTest(method)).thenReturn(true);
    when(method.getMethod()).thenReturn(realMethod);
    String path = "/tmp/blue.png";
    when(pathFactory.deriveFilePathFrom(realMethod.getDeclaringClass(), realMethod)).thenReturn(path);
    expectStatementFailure();
    statement.evaluate();
    verify(statement.desktopCamera).saveDesktopAsPng(path);
  }

  @Test public void should_not_take_screenshot_if_failing_test_is_not_GuiTest() throws Throwable {
    doThrow(toBeThrown).when(baseStatement).evaluate();
    when(statement.guiTestFilter.isGuiTest(method)).thenReturn(false);
    expectStatementFailure();
    statement.evaluate();
    verifyZeroInteractions(method, pathFactory, statement.desktopCamera);
  }

  @Test public void should_not_lose_original_exception_if_screenshot_fails() throws Throwable {
    doThrow(toBeThrown).when(baseStatement).evaluate();
    when(statement.guiTestFilter.isGuiTest(method)).thenThrow(new RuntimeException("Thrown on purpose"));
    expectStatementFailure();
    statement.evaluate();
    verifyZeroInteractions(method, pathFactory, statement.desktopCamera);
  }

  private void expectStatementFailure() {
    thrown.expect(toBeThrown.getClass());
    thrown.expectMessage(toBeThrown.getMessage());
  }
}
