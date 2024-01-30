# JavaFX + Spring Boot Boilerplate Project

A simple JavaFX + Spring Boot project that includes application preloading.


## License

MIT License (MIT)

Copyright (c) 2018 Ivan P. Skodje

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.


##
# set opencv to maven
# default
mvn install:install-file -Dfile=/home/.../src/main/resources/opencv.jar -DgroupId=org -DartifactId=opencv -Dversion=4.1.1 -Dpackaging=jar
# current project
mvn install:install-file -Dfile=C:/Users/Administrator/.m2/repository/org/opencv/4.1.1/opencv-4.1.1.jar -DgroupId=org -DartifactId=opencv -Dversion=4.1.1 -Dpackaging=jar
# run javafx
mvn exec:exec -Dexec.executable="java" -Dexec.args="-classpath %classpath -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=1044 com.ivanskodje.Main"
##