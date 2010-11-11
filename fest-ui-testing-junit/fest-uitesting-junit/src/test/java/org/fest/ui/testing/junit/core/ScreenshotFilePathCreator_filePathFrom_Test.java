/*
 * Created on Nov 10, 2010
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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.reflect.core.Reflection.method;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ScreenshotFilePathCreator#filePathFrom(Class, Method)}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenshotFilePathCreator_filePathFrom_Test {

  private Class<?> type;
  private File parentFolder;
  private ScreenshotFilePathCreator pathCreator;

  @Before public void setUp() {
    type = FakeTestClass.class;
    parentFolder = mock(File.class);
    pathCreator = new ScreenshotFilePathCreator(parentFolder);
  }

  @Test public void should_create_path_for_method_with_no_parameters() throws IOException {
    Method m = method("noParameters").in(type).info();
    when(parentFolder.getCanonicalPath()).thenReturn("/tmp");
    String createdPath = pathCreator.filePathFrom(type, m);
    assertThat(createdPath).isEqualTo("/tmp/org.fest.ui.testing.junit.core.FakeTestClass.noParameters.png");
  }

  @Test public void should_create_path_for_method_with_one_parameter() throws IOException {
    Method m = method("oneParameter").withParameterTypes(float.class).in(type).info();
    when(parentFolder.getCanonicalPath()).thenReturn("/tmp");
    String createdPath = pathCreator.filePathFrom(type, m);
    assertThat(createdPath).isEqualTo("/tmp/org.fest.ui.testing.junit.core.FakeTestClass.oneParameter(float).png");
  }

  @Test public void should_create_path_for_method_with_multiple_parameters() throws IOException {
    Method m = method("multipleParameters").withParameterTypes(String.class, int.class).in(type).info();
    when(parentFolder.getCanonicalPath()).thenReturn("/tmp");
    String createdPath = pathCreator.filePathFrom(type, m);
    assertThat(createdPath).isEqualTo("/tmp/org.fest.ui.testing.junit.core.FakeTestClass.multipleParameters(java.lang.String, int).png");
  }
}
