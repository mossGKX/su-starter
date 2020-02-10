package com.cntest.su.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {
  private String origString = "com.github.jnoee:coo:base";

  @Test
  public void testIsEmpty() {
    Assert.assertTrue(StringUtils.isEmpty(""));
    Assert.assertTrue(StringUtils.isEmpty(null));
    Assert.assertFalse(StringUtils.isEmpty(" "));
  }

  @Test
  public void testIsNotEmpty() {
    Assert.assertTrue(StringUtils.isNotEmpty(" "));
    Assert.assertFalse(StringUtils.isNotEmpty(null));
    Assert.assertFalse(StringUtils.isNotEmpty(""));
  }

  @Test
  public void testIsBlank() {
    Assert.assertTrue(StringUtils.isBlank(""));
    Assert.assertTrue(StringUtils.isBlank(null));
    Assert.assertTrue(StringUtils.isBlank(" "));
  }

  @Test
  public void testIsNotBlank() {
    Assert.assertFalse(StringUtils.isNotBlank(" "));
    Assert.assertFalse(StringUtils.isNotBlank(null));
    Assert.assertFalse(StringUtils.isNotBlank(""));
  }

  @Test
  public void testSubstringBefore() {
    Assert.assertEquals("com", StringUtils.substringBefore(origString, "."));
  }

  @Test
  public void testSubstringBeforeLast() {
    Assert.assertEquals("com.github", StringUtils.substringBeforeLast(origString, "."));
  }

  @Test
  public void testSubstringAfter() {
    Assert.assertEquals("coo:base", StringUtils.substringAfter(origString, ":"));
  }

  @Test
  public void testSubstringAfterLast() {
    Assert.assertEquals("base", StringUtils.substringAfterLast(origString, ":"));
  }

  @Test
  public void testSubstringBetween() {
    Assert.assertEquals(".github.", StringUtils.substringBetween(origString, "com", "jnoee"));
  }

  @Test
  public void testMid() {
    Assert.assertEquals("github", StringUtils.mid(origString, 4, 6));
  }

  @Test
  public void testJoinStringArrayString() {
    String[] strs = new String[] {"a", "b", "c"};
    Assert.assertEquals("a,b,c", StringUtils.join(strs, ","));
  }

  @Test
  public void testJoinCollectionOfStringString() {
    List<String> strs = new ArrayList<>();
    strs.add("a");
    strs.add("b");
    strs.add("c");
    Assert.assertEquals("a,b,c", StringUtils.join(strs, ","));
  }
}
