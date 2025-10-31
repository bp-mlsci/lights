package com.mlsci.lights.action;

import com.mlsci.lights.client.Color;

public record ColorTime(Color color, long seconds, int brightness) {

}
