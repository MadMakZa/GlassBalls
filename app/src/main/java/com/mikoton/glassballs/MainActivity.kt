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
    private var betPlayerOne = 1
    private var betPlayerTwo = 1
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
        bindingMain.btnStart.text = "Start Game"
        bindingMain.btnStart.setOnClickListener {
            if (pointsPlayerOne == 0 || pointsPlayerTwo == 0){
                pointsPlayerOne = 10
                pointsPlayerTwo = 10
                bindingMain.tvScorePlayerOne.text = pointsPlayerOne.toString()
                bindingMain.tvScorePlayerTwo.text = pointsPlayerTwo.toString()
                bindingMain.btnStart.text = "Place a Bet"
            }else {
                //start new Game
                bindingMain.btnStart.text = "Place a Bet"
                openDialogBet()
            }



        }
    }

    //Генерация картинок шаров
    private fun generateBalls() {
        val randomColorBall = Random.nextInt(1, 4)
        val img = ImageView(this)
        handLayout!!.addView(img)
        val params = img.layoutParams
        params.width = 100
        params.height = 100
        when (randomColorBall) {
            1 -> {
                img.setImageResource(R.drawable.ballfiolent)
            }
            2 -> {
                img.setImageResource(R.drawable.ballgreen)
            }
            else -> {
                img.setImageResource(R.drawable.ballblue)
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
                Toast.makeText(this, "Points refreshed!", Toast.LENGTH_SHORT).show()
                questNumPlayerTwo = Random.nextInt(1, 5)
                println("player two quest num = $questNumPlayerTwo")
            }
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
        generateBalls()

        val buttonAdd = dialogBet.findViewById<Button>(R.id.btnAdd)
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
        if (buttonClicked && questNumPlayerTwo % 2 == 0) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        } else if (!buttonClicked && (questNumPlayerTwo % 2 == 1)) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne += betPlayerOne
            pointsPlayerTwo -= betPlayerOne
        } else {
            Toast.makeText(this, "You Loose!", Toast.LENGTH_SHORT).show()
            pointsPlayerOne -= betPlayerOne
            pointsPlayerTwo += betPlayerOne
        }
        //ладонь
        handLayout = dialogOpened.findViewById<View>(R.id.handContainer) as GridLayout?
        dialogOpened.setTitle("Dialog Opened")
        dialogOpened.show()

        //Win or Loose open dialog
        if (pointsPlayerOne <= 0) {
            Toast.makeText(this, "You Dead!", Toast.LENGTH_SHORT).show()
            dialogOpened.hide()
            openDialogLoose()
            bindingMain.btnStart.text = "Refresh Score"
        } else if (pointsPlayerTwo <= 0) {
            Toast.makeText(this, "Victory!", Toast.LENGTH_SHORT).show()
            dialogOpened.hide()
            openDialogWin()
            bindingMain.btnStart.text = "Refresh Score"
        }
        bindingMain.tvScorePlayerOne.text = pointsPlayerOne.toString()
        bindingMain.tvScorePlayerTwo.text = pointsPlayerTwo.toString()
        betPlayerOne = 1

        var num = 0
        for (n in 1..questNumPlayerTwo) {
            num++
            generateBalls()
        }

        val buttonAdd = dialogOpened.findViewById<Button>(R.id.btnAdd)
        buttonAdd.visibility = View.INVISIBLE

        val buttonAcceptBet = dialogOpened.findViewById<Button>(R.id.btnOk).setOnClickListener {
            dialogOpened.hide()
        }
    }

    /**
     * Open dialog loose
     */
    private fun openDialogLoose() {
        val dialogLoose = Dialog(this)
        dialogLoose.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogLoose.setContentView(R.layout.dialog_loose)
        dialogLoose.setTitle("Dialog Loose")
        dialogLoose.show()

        val buttonGameOver = dialogLoose.findViewById<Button>(R.id.btnGameOver)
        buttonGameOver.setOnClickListener {
            //logic
            dialogLoose.hide()
        }
    }

    /**
     * Open dialog Win
     */
    private fun openDialogWin() {
        val dialogWin = Dialog(this)
        dialogWin.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogWin.setContentView(R.layout.dialog_win)
        dialogWin.setTitle("Dialog Win")
        dialogWin.show()

        val buttonGameOver = dialogWin.findViewById<Button>(R.id.btnClose)
        buttonGameOver.setOnClickListener {
            //logic
            dialogWin.hide()
        }
    }
}

