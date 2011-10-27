/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.errorprone.fixes;

import com.google.errorprone.checkers.ErrorChecker.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alexeagle@google.com (Alex Eagle)
 */
public class SuggestedFix {

  public static class Replacement {
    // positions are character offset from beginning of the source file
    public int startPosition;
    public int endPosition;
    public String replaceWith;

    public Replacement(int startPosition, int endPosition, String replaceWith) {
      this.startPosition = startPosition;
      this.endPosition = endPosition;
      this.replaceWith = replaceWith;
    }
  }

  public SuggestedFix() {}

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder("replace ");
    for (Replacement replacement : replacements) {
      result
          .append("position " + replacement.startPosition + ":" + replacement.endPosition)
          .append(" with \"" + replacement.replaceWith + "\"");
    }
    return result.toString();
  }

  private List<Replacement> replacements = new ArrayList<Replacement>();

  private SuggestedFix replace(int start, int end, String replaceWith) {
    replacements.add(new Replacement(start, end, replaceWith));
    return this;
  }

  public static SuggestedFix delete(Position statementPos) {
    return new SuggestedFix().replace(statementPos.start, statementPos.end, "");
  }

  public static SuggestedFix prefixWith(Position pos, String s) {
    return new SuggestedFix().replace(pos.start, pos.start, s);
  }

  public static SuggestedFix swap(Position pos1, Position pos2) {
    return new SuggestedFix()
        .replace(pos1.start, pos1.end, pos2.getSource())
        .replace(pos2.start, pos2.end, pos1.getSource());
  }
}
