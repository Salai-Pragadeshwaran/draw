package com.example.draw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TopCanvas.TopCanvasListener, BottomCanvas.BottomCanvasListener {

    lateinit var topFragment: TopFragment
    lateinit var bottomFragment: BottomFragment
    lateinit var bottomCanvas: BottomCanvas
    lateinit var topCanvas: TopCanvas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        topFragment = TopFragment.newInstance()
        bottomFragment = BottomFragment.newInstance()
        bottomCanvas = BottomCanvas(this)
        topCanvas = TopCanvas(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.topContainer, topFragment)
            .replace(R.id.bottomContainer, bottomFragment)
            .commitNow()

        undo.setOnClickListener {
            topCanvas.undoTop()
            bottomCanvas.undoBottom()
        }

        changeWhite.setOnClickListener {
            changeColors(1)
        }
        changeBlack.setOnClickListener {
            changeColors(2)
        }
        changeRed.setOnClickListener {
            changeColors(3)
        }
        changeYellow.setOnClickListener {
            changeColors(4)
        }
        changeGreen.setOnClickListener {
            changeColors(5)
        }
        changeBlue.setOnClickListener {
            changeColors(6)
        }
    }

    fun changeColors(n: Int){
        topCanvas.updateTopColor(n)
        bottomCanvas.changeBottomColor(n)
    }

    override fun onInputTop(input: TouchPath?, start: Boolean?): Void? {
        bottomCanvas.updateTouchPaths(input, start)
        return null
    }

    override fun onInputBottom(input: TouchPath?, start: Boolean?): Void? {
        topCanvas.updateTouchPaths(input, start)
        return null
    }


}