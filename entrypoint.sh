#!/bin/bash
set -ex
exec java \
    $* \
    -jar $JAR_FILE