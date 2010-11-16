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

import static org.fest.util.Strings.*;

import java.io.*;

import org.fest.util.VisibleForTesting;

/**
 * Creates folders in the file system.
 *
 * @author Alex Ruiz
 */
class FolderFactory {

  static FolderFactory instance() {
    return LazyLoader.INSTANCE;
  }
  
  @VisibleForTesting final SystemProperties system;

  private FolderFactory() {
    this(SystemProperties.instance());
  }

  @VisibleForTesting FolderFactory(SystemProperties system) {
    this.system = system;
  }

  /**
   * Creates a folder in the given parent folder. The folder will be created only if one with the given name does not
   * exist. If there is a non-directory file with the same name as the folder to create, such file will be deleted and a
   * folder will be created instead.
   * @param parent the parent of the folder to create.
   * @param name the name of the folder to create.
   * @return the created folder.
   * @throws IOException if any I/O error occurs while creating (or recreating) the folder.
   */
  File createFolder(File parent, String name) throws IOException {
    String canonicalPath = parent.getCanonicalPath();
    String fullPath = concat(canonicalPath, system.fileSeparator(), name);
    // TODO find a way to pass this new File as a mock, to test failure to create a folder.
    File folder = new File(fullPath);
    if (folder.isDirectory()) return folder;
    deleteIfNotFolder(folder, fullPath);
    if (!folder.mkdir()) throw new IOException(String.format("Unable to create folder %s", quote(fullPath)));
    return folder;
  }

  private void deleteIfNotFolder(File folder, String fullPath) throws IOException {
    if (!folder.exists()) return;
    if (!folder.delete()) throw new IOException(String.format("Unable to delete %s", quote(fullPath)));
  }

  private static class LazyLoader {
    static final FolderFactory INSTANCE = new FolderFactory();
  }
}
