/*
 * Created on Mar 17, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009 the original author or authors.
 */
package org.fest.ui.testing.junit.rule;

import static org.fest.util.Files.currentFolder;

import java.io.File;
import java.io.IOException;

import org.fest.util.FilesException;
import org.fest.util.VisibleForTesting;

/**
 * Creates the folder where screenshots of failed GUI tests will be saved to.
 *
 * @author Alex Ruiz
 */
final class ScreenshotsFolderFactory {

  private static final ScreenshotsFolderFactory INSTANCE = new ScreenshotsFolderFactory();

  static ScreenshotsFolderFactory instance() {
    return INSTANCE;
  }

  private final FolderFactory folderCreator;

  private ScreenshotsFolderFactory() {
    this(FolderFactory.instance());
  }

  @VisibleForTesting ScreenshotsFolderFactory(FolderFactory folderCreator) {
    this.folderCreator = folderCreator;
  }

  /**
   * Creates the folder where to save screenshots of failing GUI tests. The name of the folder to create is
   * "failed-gui-tests." The folder will be created only if one with the given name does not exist. If there is a
   * non-directory file with the same name as the folder to create, such file will be deleted and a folder will be
   * created instead.
   * @return the created folder.
   * @throws FilesException if any I/O error occurs while creating the folder.
   */
  File createFolderForScreenshots() throws IOException {
    return folderCreator.createFolder(currentFolder(), "failed-gui-tests");
  }
}
