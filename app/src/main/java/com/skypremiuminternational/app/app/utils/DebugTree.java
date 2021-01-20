package com.skypremiuminternational.app.app.utils;

import timber.log.Timber;

/**
 * Created by hein on 5/18/17.
 */

public class DebugTree extends Timber.DebugTree {

  @Override
  protected String createStackElementTag(StackTraceElement element) {
    String fullClassName = element.getClassName();
    String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    String methodName = element.getMethodName();
    int lineNumber = element.getLineNumber();
    return className + "." + methodName + "(" + className + ".java:" + lineNumber + ")";
  }
}
