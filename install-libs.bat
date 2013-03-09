setlocal

call C:\development\java\local\settings-java6.bat

call %MAVEN_HOME%\bin\mvn install:install-file -Dfile=libs\tween-engine-api.jar -DgroupId=aurelienribon.tweenengine -DartifactId=tween-engine-api -Dversion=6.3.3 -Dpackaging=jar -DgeneratePom=true

call %MAVEN_HOME%\bin\mvn install:install-file -Dfile=libs\tween-engine-api-sources.jar -DgroupId=aurelienribon.tweenengine -DartifactId=tween-engine-api-sources -Dversion=6.3.3 -Dpackaging=jar -DgeneratePom=true

endlocal
