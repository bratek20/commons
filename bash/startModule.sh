#!/bin/bash

hlaFolderPath=../hla
profileName=architecture
moduleName=$1

java -jar tool.jar start $hlaFolderPath $profileName $moduleName