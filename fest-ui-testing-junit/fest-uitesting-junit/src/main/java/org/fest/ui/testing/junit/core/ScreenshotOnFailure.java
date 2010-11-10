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

import org.fest.ui.testing.screenshot.ScreenshotTaker;
import org.fest.util.VisibleForTesting;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * Method rule that takes a screenshot of the desktop if a test belonging to the <code>{@link GuiTest}</code> category
 * fails.
 *
 * @author Alex Ruiz
 */
public class ScreenshotOnFailure implements MethodRule {

  /** {@inheritDoc} */
  public Statement apply(Statement base, FrameworkMethod method, Object target) {
    return new ScreenshotOnFailureStatement(base, method);
  }

  @VisibleForTesting static class ScreenshotOnFailureStatement extends Statement {
    final Statement base;
    final FrameworkMethod method;

    GuiTestFilter guiTestFilter = GuiTestFilter.instance();
    ScreenshotTaker screenshotTaker = new ScreenshotTaker();

    ScreenshotOnFailureStatement(Statement base, FrameworkMethod method) {
      this.base = base;
      this.method = method;
    }

    @Override public void evaluate() throws Throwable {
      try {
        base.evaluate();
      } catch (Throwable t) {
        if (guiTestFilter.isGuiTest(method))
          screenshotTaker.takeScreenshot(method.getMethod());
        throw t;
      }
    }
  }
}
