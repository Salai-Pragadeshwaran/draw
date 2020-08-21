package com.example.draw

import android.graphics.Path

data class TouchPath(
    var path: Path,
    var color: Int = 1
) {
}