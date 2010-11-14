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

import static java.util.UUID.randomUUID;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.ui.testing.junit.rule.SystemProperties.fileSeparator;
import static org.fest.util.Files.*;

import java.io.File;
import java.io.IOException;

import org.junit.*;

/**
 * Tests for <code>{@link FolderFactory#createFolder(File, String)}</code>.
 *
 * @author Alex Ruiz
 */
public class FolderFactory_createFolder_Test {

  private File parentFolder;
  private String newFolderName;
  private File createdFolder;

  private FolderFactory folderFactory;

  @Before public void setUp() {
    parentFolder = temporaryFolder();
    newFolderName = randomUUID().toString();
    folderFactory = new FolderFactory(fileSeparator("/"));
  }

  @After public void tearDown() {
    if (createdFolder != null && createdFolder.isDirectory()) delete(createdFolder);
  }

  @Test public void should_create_folder() throws IOException {
    createdFolder = folderFactory.createFolder(parentFolder, newFolderName);
    assertThat(createdFolder.getName()).isEqualTo(newFolderName);
    assertThat(pathOf(createdFolder.getParentFile())).isEqualTo(pathOf(parentFolder));
  }

  private String pathOf(File f) throws IOException {
    return f.getCanonicalPath();
  }
}
