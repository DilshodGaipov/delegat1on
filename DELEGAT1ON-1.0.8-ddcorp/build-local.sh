#!/usr/bin/env bash
set -euo pipefail
java -version || true
mvn -v || true
mvn -B -DskipTests package
echo "✔ Done. Check target/delegat1on-1.0.8-ddcorp.jar"
