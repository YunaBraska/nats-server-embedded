#!/usr/bin/env bash
set -e
versions="$(dirname "$0")/versions.sh"
cd "$(dirname "$0")/../../" || exit 1
if test -f "pom.xml"; then
  VERSION=$(./mvnw -q -Dexec.executable=echo -Dexec.args="\${project.version}" --non-recursive exec:exec)
  echo "VERSION OLD ${VERSION}"
  VERSION=$("${versions}" -p "${VERSION}")
  echo "VERSION NEW ${VERSION}"
  ./mvnw versions:set -DnewVersion="${VERSION}" -DgenerateBackupPoms=false
else
  echo "Warn no pom.xml found"
fi
