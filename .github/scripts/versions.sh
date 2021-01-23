#!/usr/bin/env bash
#https://github.com/fmahnke/shell-semver/blob/master/increment_version.sh
while getopts ":Mmp" Option; do
  case $Option in
  M) major=true ;;
  m) minor=true ;;
  p) patch=true ;;
  *) echo "Invalid input" ;;
  esac
done

shift $(("$OPTIND" - 1))

version=$1

# Build array from version string.
a=(${version//./ })

# If version string is missing or has the wrong number of members, show usage message.
if [ ${#a[@]} -ne 3 ]; then
  echo "usage: $(basename "$0") [-Mmp] major.minor.patch"
  exit 1
fi

# Increment version numbers as requested.
if [ -n "$major" ]; then
  ((a[0]++))
  a[1]=0
  a[2]=0
fi

if [ -n "$minor" ]; then
  ((a[1]++))
  a[2]=0
fi

if [ -n "$patch" ]; then
  ((a[2]++))
fi

echo "${a[0]}.${a[1]}.${a[2]}"
