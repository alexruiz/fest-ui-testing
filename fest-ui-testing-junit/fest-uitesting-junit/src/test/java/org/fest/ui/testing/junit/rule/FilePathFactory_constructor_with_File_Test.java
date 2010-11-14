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
package org.fest.ui.testing.junit.rule;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Test;

/**
 * Tests for <code>{@link FilePathFactory#ScreenshotFilePathCreator(File)}</code>.
 *
 * @author Alex Ruiz
 */
public class FilePathFactory_constructor_with_File_Test {

  @Test public void should_create_FilePathFactory_with_singleton_SystemProperties() {
    FilePathFactory pathFactory = new FilePathFactory(mock(File.class));
    assertThat(pathFactory.system).isSameAs(SystemProperties.instance());
  }
}
