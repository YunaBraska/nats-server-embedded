#!/usr/bin/env bash
set -e
git --version
increment_version="$(dirname "$0")/increment_version.sh"
cd "$(dirname "$0")/../../" || exit 1
# REMOVE COVERAGE FILES
rm -rf coverage*
rm -rf codeclimate*
rm -rf ./*reporter*
# SETUP GIT CONFIG
git config --global user.name 'Kira'
git config --global user.email 'yuna-@web.de'
if [ -z ${1+x} ]; then
  echo "Warn remote url is not set"
else
  git remote set-url origin "$1"
fi
# UPDATE VERSION
if [[ $(git status --porcelain) ]]; then
  echo "Update version"
  "${increment_version}" -p
  echo "Pushing new git changes"
  git add .
  git commit -am "Updated dependencies"
  git push origin -f
else
  echo "No git changes to push"
fi
# CREATE TAG
git fetch --all --force || true
git fetch --tags --force || true
TAG_OLD=$(git describe --tag --always --abbrev=0)
VERSION=$(./mvnw -q -Dexec.executable=echo -Dexec.args="\${project.version}" --non-recursive exec:exec)
git tag "${VERSION}" &>/dev/null || true
TAG_NEW=$(git describe --tag --always --abbrev=0)
echo "TAG_OLD [${TAG_OLD}] <> TAG_NEW [${TAG_NEW}]"
if [ "${TAG_OLD}" == "${TAG_NEW}" ]; then
  echo "Tag already exists"
else
  echo "New tag created ${TAG_NEW}"
  git branch "release" &>/dev/null || true
  # new branch as trigger for new release
  git push origin --all -u -f || true
  git push origin --tags -f || true
fi
