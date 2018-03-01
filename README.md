# TinyDelver

Download the java dependencies

mkdir lib
cd lib
wget https://github.com/trystan/AsciiPanel/blob/master/dist/asciiPanel.jar


Build the application

mvn clean install


Run the application

java -cp "lib/*:target/*" com.tinydelver.ApplicationMain

