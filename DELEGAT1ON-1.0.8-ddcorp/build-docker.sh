#!/usr/bin/env bash
set -euo pipefail
echo "Using Docker image: maven:3.9-eclipse-temurin-11"
docker run --rm -v "$PWD":/project -w /project maven:3.9-eclipse-temurin-11 mvn -B -DskipTests package
echo "✔ Done. Check target/delegat1on-1.0.8-ddcorp.jar"
