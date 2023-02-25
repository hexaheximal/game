#!/bin/bash

if [ ! -d build ]
then
	mkdir build
fi

cp -r src/* build/
cp ../client/desktop/build/libs/desktop-0.0.1.jar build/game.jar

cd build

if [ ! -d jre ]
then
	wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.6%2B10/OpenJDK17U-jre_x64_linux_hotspot_17.0.6_10.tar.gz
	tar -xvf OpenJDK17U-jre_x64_linux_hotspot_17.0.6_10.tar.gz
	rm OpenJDK17U-jre_x64_linux_hotspot_17.0.6_10.tar.gz
	mv jdk-17.0.6+10-jre jre
fi

tar -czvf ../launcher.tar.gz .

cd ..

echo "Finished!"