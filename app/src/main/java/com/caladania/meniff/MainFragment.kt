package com.caladania.meniff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerOne
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerTwo

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    lateinit var tvScorePlayerOne: TextView
    lateinit var tvScorePlayerTwo: TextView
    lateinit var btnStart: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvScorePlayerOne = activity?.findViewById(R.id.tvScorePlayerOne)!!
        tvScorePlayerTwo = activity?.findViewById(R.id.tvScorePlayerTwo)!!
        btnStart = activity?.findViewById(R.id.btnStart)!!
        tvScorePlayerOne.text = pointsPlayerOne.toString()
        tvScorePlayerTwo.text = pointsPlayerTwo.toString()
        startNewGame()


    }

    fun startNewGame() {
        btnStart.text = "Start Game"
        btnStart.setOnClickListener {
            if (pointsPlayerOne == 0 || pointsPlayerTwo == 0) {
                pointsPlayerOne = 10
                pointsPlayerTwo = 10
                tvScorePlayerOne.text = pointsPlayerOne.toString()
                tvScorePlayerTwo.text = pointsPlayerTwo.toString()
                btnStart.text = "Place a Bet"
            } else {
                //start new Game
                btnStart.text = "Place a Bet"
                parentFragmentManager.beginTransaction().run {
                    replace(R.id.containerFragments, OpenHandFragment())
                    this.commit()
                }
            }

        }
    }

}