@echo off
title FileTransfer

REM Setup Path Variables, if not set
REM set JAVA_HOME=...

REM Setup Program
set PORT=8080
set ROUTES=route-sample.properties

echo Starting ...
start %JAVA_HOME%\bin\java  -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory^
                            -Dlogback.configurationFile=file:%CD%\logback.xml^
                            -jar filetransfer-standalone.jar^
                            --port %PORT%^
                            --routes %ROUTES%
