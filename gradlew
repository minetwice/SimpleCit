#!/bin/bash

#
# Copyright 2025 [Minetwice]
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0
#

# Resolve APP_HOME
PRG="$0"
while [ -h "$PRG" ]; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> \(.*\)$')
  if expr "$link" : '/.*' >/dev/null; then
    PRG="$link"
  else
    PRG=$(dirname "$PRG")/"$link"
  fi
done
SAVED_PWD="$PWD"
cd "$(dirname "$PRG")" >/dev/null || exit
APP_HOME="$PWD"
cd "$SAVED_PWD" >/dev/null

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

if [ ! -f "$CLASSPATH" ]; then
  echo "ERROR: gradle-wrapper.jar not found at: $CLASSPATH"
  exit 1
fi

# ✅ Critical Fix: JVM opts unquoted, passed as array
exec java -Xmx64m -Xms64m -cp "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
