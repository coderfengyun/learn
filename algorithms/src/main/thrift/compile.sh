#!/bin/sh
rm -fr ../java/org/tcse/algorithms/learn/enhancer/thrift

thrift --gen java -out ../java Test.thrift
