@echo off
call mvnw.cmd clean verify sonar:sonar ^
  -Dsonar.host.url=http://localhost:9000 ^
  -Dsonar.token=sqa_251b116131382c464863d03be9e966babfbb27ea ^
  -Dsonar.projectKey=apirfds220262 ^
  -Dsonar.projectName="Sistema de Denuncias y Sugerencias - Backend"
pause