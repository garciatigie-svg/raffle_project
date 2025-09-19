#!/usr/bin/env sh

##############################################################################
##
##  Minimal Gradle wrapper startup script for Unix (Termux-friendly)
##
##############################################################################

# Resolve the directory of this script ("DIR")
DIR="$( cd "$( dirname "$0" )" && pwd )"

# Path to the wrapper JAR
WRAPPER_JAR="$DIR/gradle/wrapper/gradle-wrapper.jar"

# If JAVA_HOME is set, prefer its java; otherwise rely on 'java' in PATH
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/java" ]; then
  JAVACMD="$JAVA_HOME/bin/java"
else
  JAVACMD=$(command -v java 2>/dev/null)
fi

if [ -z "$JAVACMD" ]; then
  echo "ERROR: java not found. Please install Java or set JAVA_HOME." >&2
  exit 1
fi

# Ensure wrapper JAR exists
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "ERROR: Gradle wrapper JAR not found at: $WRAPPER_JAR" >&2
  exit 1
fi

# Run Gradle wrapper main class
exec "$JAVACMD" -cp "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
