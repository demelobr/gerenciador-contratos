@echo off
setlocal enabledelayedexpansion

:: Defina as variáveis do MySQL
set mysqlPath="C:\Program Files\MySQL\MySQL Server 8.0\bin"
set mysqlUser="admin"
set mysqlPassword="admin"
set dbName="dbgc"
set backupPath=C:\Users\Bruno Melo\OneDrive\backup
set backupFileName="backup.sql"
set mysqlHost="localhost"

:: Formate a data e hora no formato desejado
@REM set LogDate=%DateTime:~0,4%-%DateTime:~4,2%-%DateTime:~6,2%
@REM set LogTime=%DateTime:~8,2%:%DateTime:~10,2%:%DateTime:~12,2%

:: Navegue até a pasta onde o mysqldump está localizado
cd %mysqlPath%

:: Obtenha a data e hora atual
@REM for /f %%a in ('wmic os get LocalDateTime ^| find "."') do set DateTime=%%a

:: Tente fazer o dump do banco de dados
echo "%backupPath%/mysql_backup_log.txt"
echo %date:~0,2%-%date:~3,2%-%date:~6,10%-%time:~0,2%-%time:~3,2% - Iniciando o dump do banco de dados... >> "%backupPath%/mysql_backup_log.txt"
:: Use o mysqldump para criar o backup
".\mysqldump" -u %mysqlUser% -p%mysqlPassword% %dbName% > "%backupPath%\%backupFileName%"

:: Verifique o código de saída para determinar se o backup foi bem-sucedido
if %errorlevel% equ 0 (
    (echo %date:~0,2%-%date:~3,2%-%date:~6,10%-%time:~0,2%-%time:~3,2% - Backup feito com sucesso!) >> "%backupPath%/mysql_backup_log.txt"
) else (
    (echo %date:~0,2%-%date:~3,2%-%date:~6,10%-%time:~0,2%-%time:~3,2% - Erro ao tentar criar backup.) >> "%backupPath%/mysql_backup_log.txt"
)

endlocal
