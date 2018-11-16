#!/usr/bin/env bash

DEST_DIR='/Users/kk/work/tmp/demo/deploy'

gradle build

rm -rvf $DEST_DIR/*

unzip api_server/build/distributions/api_server.zip -d $DEST_DIR

