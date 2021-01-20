/*
  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
  the License. You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
  specific language governing permissions and limitations under the License.

  Copyright 2016 the original author or authors.
 */
package com.skypremiuminternational.app.data.aws;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Canonical Headers.
 * <p>
 * See http://docs.aws.amazon.com/general/latest/gr/sigv4-create-canonical-request.html for more information
 * </p>
 *
 * @author Richard Lucas
 */
class CanonicalHeaders {

  private final String names;
  private final String canonicalizedHeaders;
  private final TreeMap<String, List<String>> internalMap;

  private CanonicalHeaders(String names, String canonicalizedHeaders,
                           TreeMap<String, List<String>> internalMap) {
    this.names = names;
    this.canonicalizedHeaders = canonicalizedHeaders;
    this.internalMap = internalMap;
  }

  String get() {
    return canonicalizedHeaders;
  }

  String getNames() {
    return names;
  }

  @Nullable
  String getFirstValue(String name) {
    List<String> list = internalMap.get(name.toLowerCase());
    if (list != null && list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  static Builder builder() {
    return new Builder();
  }

  static class Builder {

    private final TreeMap<String, List<String>> internalMap = new TreeMap<>();

    Builder add(String name, String value) {

      if (name == null) {
        throw new IllegalArgumentException("name is null");
      }

      if (value == null) {
        throw new IllegalArgumentException("value is null");
      }
      String lowerCaseName = name.toLowerCase();
      List<String> curValues = internalMap.get(lowerCaseName);
      if (curValues == null) {
        internalMap.put(lowerCaseName, newValueListWithValue(value));
      } else {
        curValues.add(value);
        internalMap.put(lowerCaseName, curValues);
      }
      return this;
    }

    CanonicalHeaders build() {
      StringBuilder namesBuilder = new StringBuilder();
      rx.Observable.from(internalMap.keySet())
          .map(String::toLowerCase)
          .reduce("", (s, s2) -> s + s2 + ";")
          .map(joined -> joined.substring(0, joined.lastIndexOf(";")))
          .toBlocking()
          .subscribe(namesBuilder::append);

      StringBuilder canonicalizedHeadersBuilder = new StringBuilder();
      rx.Observable.from(internalMap.entrySet())
          .map(header -> header.getKey().toLowerCase() + ":" + getValue(header.getValue()) + "\n")
          .toBlocking()
          .subscribe(canonicalizedHeadersBuilder::append);

      return new CanonicalHeaders(namesBuilder.toString(), canonicalizedHeadersBuilder.toString(),
          internalMap);
    }

    private String getValue(List<String> values) {
      StringBuilder builder = new StringBuilder();
      rx.Observable.from(values)
          .map(Builder::normalizeHeaderValue)
          .reduce("", (s, s2) -> s + s2 + ",")
          .map(joined -> joined.substring(0, joined.lastIndexOf(",")))
          .toBlocking()
          .subscribe(builder::append);
      return builder.toString();
    }

    private List<String> newValueListWithValue(String value) {
      List<String> values = new ArrayList<>();
      values.add(value);
      return values;
    }

    private static String normalizeHeaderValue(String value) {
      /*
       * Strangely, the AWS test suite expects us to handle lines in
       * multi-line values as individual values, even though this is not
       * mentioned in the specs.
       */
      StringBuilder valueBuilder = new StringBuilder();
      rx.Observable.from(value.split("\n"))
          .map(String::trim)
          .map(s -> s.replaceAll(" +", " "))
          .reduce("", (s, s2) -> s + s2 + ",")
          .map(joined -> joined.substring(0, joined.lastIndexOf(",")))
          .toBlocking()
          .subscribe(valueBuilder::append);

      return valueBuilder.toString();
    }
  }
}
