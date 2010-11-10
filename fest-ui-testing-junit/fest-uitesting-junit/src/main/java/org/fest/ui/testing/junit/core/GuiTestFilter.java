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

import org.fest.util.VisibleForTesting;
import org.junit.runners.model.FrameworkMethod;

/**
 * Indicates whether a test belongs to the <code>{@link GuiTest}</code> category or not.
 *
 * @author Alex Ruiz
 */
class GuiTestFilter {

  private static final GuiTestFilter INSTANCE = new GuiTestFilter();

  static GuiTestFilter instance() {
    return INSTANCE;
  }

  @VisibleForTesting GuiTestFilter() {}

  boolean isGuiTest(FrameworkMethod method) {
    return false;
  }
}
