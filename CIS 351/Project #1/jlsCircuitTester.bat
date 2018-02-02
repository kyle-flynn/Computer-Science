REM This script launches the JLS circuit tester, provided JLS.jar and 
REM JLSCircuitTester.jar are in the same directory
REM as this file.

REM %~dp0 returns argument 0 (the path to this batch file) as a
REM directory (without the filename)

@java -Xmx256m -cp "%~dp0\JLS.jar";"%~dp0\jlsCircuitTester.jar";.;$JLSCT_CLASSPATH -ea edu.gvsu.cis.kurmasz.jls.JLSCircuitTester %*
