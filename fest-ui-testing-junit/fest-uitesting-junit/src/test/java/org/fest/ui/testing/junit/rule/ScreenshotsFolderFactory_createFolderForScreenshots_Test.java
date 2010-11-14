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
import static org.fest.util.Files.currentFolder;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for <code>{@link ScreenshotsFolderFactory#createFolderForScreenshots()}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenshotsFolderFactory_createFolderForScreenshots_Test {

  private FolderFactory folderFactory;
  private ScreenshotsFolderFactory screenshotsFolderFactory;

  @Before public void setUp() {
    folderFactory = mock(FolderFactory.class);
    screenshotsFolderFactory = new ScreenshotsFolderFactory(folderFactory);
  }

  @Test public void should_create_folder_for_screenshots() throws IOException {
    File folderToCreate = mock(File.class);
    when(folderFactory.createFolder(currentFolder(), "failed-gui-tests")).thenReturn(folderToCreate);
    File createdFolder = screenshotsFolderFactory.createFolderForScreenshots();
    assertThat(createdFolder).isSameAs(folderToCreate);
  }
}
