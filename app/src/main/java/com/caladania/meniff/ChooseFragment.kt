package com.caladania.meniff

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.caladania.meniff.MainActivity.Global_ma.buttonClicked
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerOne
import com.caladania.meniff.MainActivity.Global_ma.pointsPlayerTwo
import com.caladania.meniff.MainActivity.Global_ma.questNumPlayerTwo
import kotlin.random.Random

class ChooseFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseFragment()
    }

    private lateinit var buttonEven: Button
    private lateinit var buttonUneven: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonEven = activity?.findViewById(R.id.btnEven)!!
        buttonUneven = activity?.findViewById(R.id.btnUneven)!!

        getChoose()
    }


    private fun getChoose() {
        generateRandomBetPlayerTwo()

        buttonEven.setOnClickListener {
            //logic
            buttonClicked = true
            //open fragment opened hand!
            parentFragmentManager.beginTransaction().run {
                replace(R.id.containerFragments, OpenHandFragment())
                this.commit()
            }

        }
        buttonUneven.setOnClickListener {
            //logic
            buttonClicked = false
            //open fragment opened hand!
            parentFragmentManager.beginTransaction().run {
                replace(R.id.containerFragments, OpenHandFragment())
                this.commit()
            }

        }
    }

    //генерация рандомной ставки playerTwo
    private fun generateRandomBetPlayerTwo() {
        when {
            pointsPlayerTwo > 1 -> {
                questNumPlayerTwo = Random.nextInt(1, 5)
                println("player two quest num = $questNumPlayerTwo")
            }
            pointsPlayerTwo == 1 -> {
                questNumPlayerTwo = 1
                println("player two quest num = $questNumPlayerTwo")
            }
            else -> {
                pointsPlayerOne = 10
                pointsPlayerTwo = 10
//                Toast.makeText(this, "Points refreshed!", Toast.LENGTH_SHORT).show()
                questNumPlayerTwo = Random.nextInt(1, 5)
                println("player two quest num = $questNumPlayerTwo")
            }
        }
    }

}
