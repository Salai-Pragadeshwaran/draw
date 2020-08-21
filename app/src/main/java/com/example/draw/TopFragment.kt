package com.example.draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TopFragment : Fragment(){

//    interface TopFragmentListener{
//        fun onInputTop(input: ArrayList<TouchPath>)
//    }

    companion object{
        fun newInstance() = TopFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_top, container, false)

        return root
    }
}