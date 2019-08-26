#!/bin/bash
# FileTransfer
#
# Setup Path Variables, if not set
# JAVA_HOME=...
#
# Setup Program
PORT=8080
ROUTES=route-sample.properties

echo Starting ...
$JAVA_HOME\bin\java -Dvertx.logger-delegate-factory-class-name=io.vertx.core.logging.SLF4JLogDelegateFactory \
                    -Dlogback.configurationFile=file:%CD%\logback.xml \
                    -jar filetransfer-standalone.jar \
                    --port %PORT% \
                    --routes %ROUTES%
