#!/bin/bash

if [ ! -d build ]
then
	mkdir build
fi

cp -r src/* build/
cp ../client/desktop/build/libs/desktop-0.0.1.jar build/game.jar

cd build

if [ ! -d jdk-17.0.5+8 ]
then
	wget https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.5%2B8/OpenJDK17U-jdk_x64_linux_hotspot_17.0.5_8.tar.gz
	tar -xvf OpenJDK17U-jdk_x64_linux_hotspot_17.0.5_8.tar.gz
	rm OpenJDK17U-jdk_x64_linux_hotspot_17.0.5_8.tar.gz
fi

tar -czvf ../launcher.tar.gz .

cd ..

echo "Finished!"