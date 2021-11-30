package com.caladania.meniff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import com.caladania.meniff.MainActivity.Global_ma.betPlayerOne
import com.caladania.meniff.MainActivity.Global_ma.buttonClicked
import com.caladania.meniff.MainActivity.Global_ma.flagBetPlayer
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerOne
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerTwo
import com.caladania.meniff.MainActivity.Global_ma.questNumPlayerTwo
import kotlin.random.Random


class OpenHandFragment : Fragment() {

    companion object {
        fun newInstance() = OpenHandFragment()
    }

    private lateinit var handLayout: GridLayout
    private lateinit var buttonAdd: Button
    private lateinit var buttonAcceptBet: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_hand, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handLayout = activity?.findViewById(R.id.handContainer)!!
        buttonAdd = activity?.findViewById(R.id.btnAdd)!!
        buttonAcceptBet = activity?.findViewById(R.id.btnOk)!!

        if (flagBetPlayer) {
            getBet()
        }else{
            //показать что загадал игрок два
            openHandPlayerTwo()
        }
    }

    //Генерация картинок шаров
    private fun generateBalls() {
        val randomColorBall = Random.nextInt(1, 4)
        val img = ImageView(context?.applicationContext)
        handLayout.addView(img)
        val params = img.layoutParams
        params.width = 100
        params.height = 100
        when (randomColorBall) {
            1 -> {
                img.setImageResource(R.drawable.bf)
            }
            2 -> {
                img.setImageResource(R.drawable.bg)
            }
            else -> {
                img.setImageResource(R.drawable.bb)
            }
        }
    }

    private fun getBet() {
        //ладонь
        generateBalls()

        buttonAdd.setOnClickListener {
            //add balls
            if(pointsPlayerOne > 0
                && betPlayerOne < 6
                && betPlayerOne < pointsPlayerOne
                && betPlayerOne <= pointsPlayerTwo-1) {
                generateBalls()
                betPlayerOne++

            }
        }
        buttonAcceptBet.setOnClickListener {
            flagBetPlayer = false
            //accept bet
            parentFragmentManager.beginTransaction().run {
                replace(R.id.containerFragments, ChooseFragment())
                this.commit()
            }
        }
    }

    /**
    //     * Dialog hand Opened
    //     */
    private fun openHandPlayerTwo() {
        if (buttonClicked && questNumPlayerTwo % 2 == 0) {
            Toast.makeText(activity, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        } else if (!buttonClicked && (questNumPlayerTwo % 2 == 1)) {
            Toast.makeText(activity, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        } else {
            Toast.makeText(activity, "You Loose!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne -= betPlayerOne
            pointsPlayerTwo += betPlayerOne
        }

        //Win or Loose open dialog
        if (pointsPlayerOne <= 0) {
            Toast.makeText(activity, "You Dead!", Toast.LENGTH_SHORT).show()
//            openDialogLoose()
//            MainFragment.newInstance().btnStart.text = "Refresh Score"
        } else if (pointsPlayerTwo <= 0) {
            Toast.makeText(activity, "Victory!", Toast.LENGTH_SHORT).show()
//            openDialogWin()
//            MainFragment.newInstance().btnStart.text = "Refresh Score"
        }
//        MainFragment.newInstance().tvScorePlayerOne.text = pointsPlayerOne.toString()
//        MainFragment.newInstance().tvScorePlayerTwo.text = pointsPlayerTwo.toString()
        betPlayerOne = 1

        var num = 0
        for (n in 1..questNumPlayerTwo) {
            num++
            generateBalls()
        }
        buttonAdd.visibility = View.INVISIBLE

        buttonAcceptBet.setOnClickListener {
            flagBetPlayer = true
            parentFragmentManager.beginTransaction().run {
                replace(R.id.containerFragments, MainFragment())
                this.commit()
            }
        }
    }
}