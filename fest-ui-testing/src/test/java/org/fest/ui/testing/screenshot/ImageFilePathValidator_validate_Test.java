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
package org.fest.ui.testing.screenshot;

import static org.fest.ui.testing.test.ExpectedException.none;

import org.fest.ui.testing.test.ExpectedException;
import org.junit.*;

/**
 * Tests for <code>{@link ImageFilePathValidator#validate(String)}</code>.
 *
 * @author Alex Ruiz
 */
public class ImageFilePathValidator_validate_Test {

  @Rule public ExpectedException thrown = none();

  private ImageFilePathValidator validator;

  @Before public void setUp() {
    validator = ImageFilePathValidator.instance();
  }

  @Test public void should_fail_if_path_is_null() {
    thrown.expect(NullPointerException.class, "The path of the image to save should not be null");
    validator.validate(null);
  }

  @Test public void should_fail_if_path_is_empty() {
    thrown.expectIllegalArgumentException("The path '' should end with '.png'");
    validator.validate("");
  }

  @Test public void should_fail_if_path_does_not_end_with_png() {
    thrown.expectIllegalArgumentException("The path '/tmp/blue' should end with '.png'");
    validator.validate("/tmp/blue");
  }

  @Test public void should_pass_if_path_ends_with_png() {
    validator.validate("/tmp/blue.png");
  }
}
