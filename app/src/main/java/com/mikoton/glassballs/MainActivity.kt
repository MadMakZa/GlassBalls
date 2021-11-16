package com.mikoton.glassballs

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.mikoton.glassballs.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var bindingMain: ActivityMainBinding
    private var handLayout: GridLayout? = null
    //очки игроков
    private var pointsPlayerOne = 10
    private var pointsPlayerTwo = 10
    //ставки игроков
    private var flagBetOrQuest = false
    private var betPlayerOne = 0
    private var betPlayerTwo = 0
    private var questNumPlayerOne = 1
    private var questNumPlayerTwo = 1
    //что нажато чёт или нечёт
    private var buttonClicked = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        init()
    }

    private fun init() {
        bindingMain.tvScorePlayerOne.text = pointsPlayerOne.toString()
        bindingMain.tvScorePlayerTwo.text = pointsPlayerTwo.toString()
        startNewGame()
    }

    private fun startNewGame() {
        bindingMain.btnStart.setOnClickListener {
            //start new Game
            openDialogBet()

        }
    }

    //Генерация картинок шаров
    private fun generateBalls(count: Int) {
        val img = ImageView(this)
        handLayout!!.addView(img)
        val params = img.layoutParams
        params.width = 100
        params.height = 100
        if (count %2 == 0) {
            img.setImageResource(R.drawable.ballfiolent)
        }else{
            img.setImageResource(R.drawable.ballblue)
        }
    }

    //генерация рандомной ставки playerTwo
    private fun generateRandomBetPlayerTwo(){
        if (pointsPlayerTwo >= 1) {
            questNumPlayerTwo = Random.nextInt(1, pointsPlayerTwo)
            println("player two quest num = $questNumPlayerTwo")
        }else{
            pointsPlayerOne = 10
            pointsPlayerTwo = 10
            Toast.makeText(this, "Points refreshed!", Toast.LENGTH_SHORT).show()
            questNumPlayerTwo = Random.nextInt(1, pointsPlayerTwo)
            println("player two quest num = $questNumPlayerTwo")
        }
    }

    /**
     * Dialog hand Bets
     */
    private fun openDialogBet() {
        val dialogBet = Dialog(this)
        dialogBet.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogBet.setContentView(R.layout.dialog_bet)
        //ладонь
        handLayout = dialogBet.findViewById<View>(R.id.handContainer) as GridLayout?
        dialogBet.setTitle("Dialog Bet")
        dialogBet.show()

        val buttonAdd = dialogBet.findViewById<Button>(R.id.btnAdd)
        buttonAdd.setOnClickListener {
            //add balls
            generateBalls(betPlayerOne)
            betPlayerOne++
        }
        val buttonAcceptBet = dialogBet.findViewById<Button>(R.id.btnOk)
        buttonAcceptBet.setOnClickListener {
            //accept bet
            //start new dialog_choose
            openDialogChoose()
            dialogBet.hide()
        }
    }

    /**
     * Dialog choose
     */
    private fun openDialogChoose() {
        generateRandomBetPlayerTwo()

        val dialogChoose = Dialog(this)
        dialogChoose.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogChoose.setContentView(R.layout.dialog_choose)
        dialogChoose.setTitle("Dialog Choose")
        dialogChoose.show()

        val buttonEven = dialogChoose.findViewById<Button>(R.id.btnEven)
        buttonEven.setOnClickListener {
            //logic
            buttonClicked = true
            dialogChoose.hide()
            openDialogOpened()

        }
        val buttonUneven = dialogChoose.findViewById<Button>(R.id.btnUneven)
        buttonUneven.setOnClickListener {
            //logic
            buttonClicked = false
            dialogChoose.hide()
            openDialogOpened()

        }
    }

    /**
     * Dialog hand Opened
     */
    private fun openDialogOpened() {
        val dialogOpened = Dialog(this)
        dialogOpened.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogOpened.setContentView(R.layout.dialog_bet)
        if (buttonClicked && questNumPlayerTwo %2 == 0){
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        }
        else if(!buttonClicked && (questNumPlayerTwo %2 == 1)){
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        }else{
            Toast.makeText(this, "You Loose!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne -= betPlayerOne
            pointsPlayerTwo += betPlayerOne
        }
        //refresh points
        if (pointsPlayerOne <= 0){
            Toast.makeText(this, "You Dead!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne = 10
            pointsPlayerTwo = 10
        }else if (pointsPlayerTwo <= 0) {
            Toast.makeText(this, "Victory!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne = 10
            pointsPlayerTwo = 10
        }
        bindingMain.tvScorePlayerOne.text = pointsPlayerOne.toString()
        bindingMain.tvScorePlayerTwo.text = pointsPlayerTwo.toString()
        betPlayerOne = 0
        //ладонь
        handLayout = dialogOpened.findViewById<View>(R.id.handContainer) as GridLayout?
        dialogOpened.setTitle("Dialog Opened")
        dialogOpened.show()



        var num = 0
        for (n in 1..questNumPlayerTwo){
            num++
            generateBalls(num)
        }

        val buttonAdd = dialogOpened.findViewById<Button>(R.id.btnAdd)
        buttonAdd.visibility = View.INVISIBLE

        val buttonAcceptBet = dialogOpened.findViewById<Button>(R.id.btnOk).setOnClickListener {
            dialogOpened.hide()
        }

    }
}

