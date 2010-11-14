/*
 * Created on Nov 12, 2010
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
package org.fest.ui.testing.screenshot;

import static org.fest.assertions.Assertions.assertThat;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link DisplayOld#sizeOfPrimary()}</code>.
 *
 * @author Alex Ruiz
 */
public class Display_sizeOfPrimary_Test {

  private DisplayOld display;

  @Before public void setUp() {
    display = DisplayOld.instance();
  }

  @Test public void should_return_size_of_primary_screen() {
    Dimension size = display.sizeOfPrimary();
    assertThat(size).isEqualTo(Toolkit.getDefaultToolkit().getScreenSize());
  }
}