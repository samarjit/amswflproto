set expDir=something

set localDir=F:\eclipse\workspace\drools\amswflproto


cd %localDir%

mkdir tempjar

set intermDir=%localDir%\tempjar

xcopy /E /Y %localDir%\src\* %intermDir%\src\

xcopy /E /Y %localDir%\designer\* %intermDir%\designer\


copy /Y  %localDir%\build.bat %intermDir%\

copy /Y  %localDir%\build.xml %intermDir%\

copy  /Y  %localDir%\*.sql %intermDir%\

xcopy /E /Y  %localDir%\WebContent\*.jsp %intermDir%\WebContent\

xcopy /E /Y  %localDir%\WebContent\*.js %intermDir%\WebContent\

xcopy /E /Y  %localDir%\WebContent\WEB-INF\web.xml %intermDir%\WebContent\WEB-INF\


copy   /Y  %localDir%\export.bat %intermDir%\

cd  %intermDir%

jar -cvf generatehtml.zip .


copy  %intermDir%\generatehtml.zip  %expDir%\

cd ..



pause