/*
 * Created on Mar 17, 2010
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
package org.fest.ui.testing.lock;

import static org.fest.ui.testing.test.ExpectedException.none;

import org.fest.ui.testing.test.ExpectedException;

import org.junit.*;

/**
 * Tests for <code>{@link ScreenLock#release(Object)}</code>.
 * 
 * @author Alex Ruiz
 */
public class ScreenLock_release_Test {

  @Rule public ExpectedException thrown = none();
  
  private ScreenLock lock;
  private Object owner;

  @Before public void setUp() {
    lock = new ScreenLock();
    owner = new LockOwner("Owner");
  }

  @After public void tearDown() {
    if (lock.acquiredBy(owner)) lock.release(owner);
  }

  @Test public void should_throw_error_if_trying_to_release_with_different_owner() {
    lock.acquire(owner);
    thrown.expectScreenLockException("<Not an owner> is not the lock owner");
    lock.release(new LockOwner("Not an owner"));
  }

  @Test public void should_throw_error_if_trying_to_release_without_being_locked() {
    thrown.expectScreenLockException("No lock to release");
    lock.release(owner);
  }
  
  private static class LockOwner {
    final String name;

    LockOwner(String name) {
      this.name = name;
    }
    
    @Override public String toString() {
      return name;
    }
  }
}
