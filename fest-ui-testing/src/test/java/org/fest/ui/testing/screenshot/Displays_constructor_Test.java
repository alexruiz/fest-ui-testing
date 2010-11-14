/*
 * Created on Nov 13, 2010
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
import static org.mockito.Mockito.*;

import java.awt.*;

import org.junit.*;

/**
 * Tests for <code>{@link Displays#Displays(RobotFactory, GraphicsEnvironment)}</code>.
 *
 * @author Alex Ruiz
 */
public class Displays_constructor_Test {

  private RobotFactory robotFactory;
  private GraphicsEnvironment environment;
  private int screenCount;
  private GraphicsDevice[] screens;
  
  @Before public void setUp() {
    robotFactory = mock(RobotFactory.class);
    environment = mock(GraphicsEnvironment.class);
    screenCount = 6;
    screens = new GraphicsDevice[screenCount];
    for (int i = 0; i < screenCount; i++) screens[i] = mock(GraphicsDevice.class);
  }
  
  @Test public void should_create_one_Screen_per_GraphicsDevice() throws AWTException {
    when(environment.getScreenDevices()).thenReturn(screens);
    Displays displays = new Displays(robotFactory, environment);
    assertThat(displays.displays).hasSize(screenCount);
    // TODO assert displays are not null
  }
}
