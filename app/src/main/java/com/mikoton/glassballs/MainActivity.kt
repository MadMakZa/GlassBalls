package com.mikoton.glassballs

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.view.contains
import androidx.core.view.get
import com.mikoton.glassballs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindingMain: ActivityMainBinding
    private var handLayout: GridLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)
        init()
    }

    private fun init() {
        startNewGame()
    }

    private fun startNewGame() {
        bindingMain.btnStartNewGame.setOnClickListener {
            //start new Game
            openDialogBet()

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
            generateBalls()
        }
        val buttonAcceptBet = dialogBet.findViewById<Button>(R.id.btnOk)
        buttonAcceptBet.setOnClickListener {
            //accept bet
            //start new dialog_choose
            openDialogChoose()
            dialogBet.hide()
        }
    }
    //Генерация картинок шаров
    private fun generateBalls() {
        val img = ImageView(this)
        handLayout!!.addView(img)
        val params = img.layoutParams
        params.width = 100
        params.height = 100
        img.setImageResource(R.drawable.ballfiolent)
    }

    /**
     * Dialog choose
     */
    private fun openDialogChoose() {
        val dialogChoose = Dialog(this)
        dialogChoose.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        dialogChoose.setContentView(R.layout.dialog_choose)
        dialogChoose.setTitle("Dialog Choose")
        dialogChoose.show()

        val buttonEven = dialogChoose.findViewById<Button>(R.id.btnEven)
        buttonEven.setOnClickListener {
            //logic
        }
        val buttonUneven = dialogChoose.findViewById<Button>(R.id.btnUneven)
        buttonUneven.setOnClickListener {
            //logic
        }
    }
}

