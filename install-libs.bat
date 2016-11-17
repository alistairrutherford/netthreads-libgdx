setlocal

call settings.bat

call %MAVEN_HOME%\bin\mvn install:install-file -Dfile=libs\tween-engine-api.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar

call %MAVEN_HOME%\bin\mvn install:install-file -Dfile=libs\tween-engine-api-sources.jar -DgroupId=aurelienribon -DartifactId=tweenengine -Dversion=6.3.3 -Dpackaging=jar -Dclassifier=sources

call %MAVEN_HOME%\bin\mvn install:install-file -Dfile=libs\libgdx-fixtureatlas.jar -DgroupId=aurelienribon -DartifactId=fixtureatlas -Dversion=1.0.0 -Dpackaging=jar

endlocal
