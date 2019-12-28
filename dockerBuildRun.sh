#!/usr/bin/env bash

docker build -t farm-delivery-app . && docker run -p 8887:8887 farm-delivery-app