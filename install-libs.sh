#!/bin/bash

export MAVEN_HOME=/Users/alistairrutherford/Documents/development/java/android/sdk/apache-maven-3.2.5/

$MAVEN_HOME/bin/mvn install:install-file -Dfile=libs/tween-engine-api.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar

$MAVEN_HOME/bin/mvn install:install-file -Dfile=libs/tween-engine-api-sources.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar -Dclassifier=sources

$MAVEN_HOME/bin/mvn install:install-file -Dfile=libs/libgdx-fixtureatlas.jar -DgroupId=aurelienribon -DartifactId=fixtureatlas -Dversion=1.0.0 -Dpackaging=jar
