package com.example.connect3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.gridlayout.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    //0:Yellow, 1:red, -1: empty
    private var gameState = IntArray(9){-1}

    private val winningPosition = arrayOf(
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),
        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),
        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6)
    )

    private var activePlayer = 0

    private var gameActive = true

    private var cnt = 9

    @SuppressLint("SetTextI18n")
    fun dropIn(view : View){
        val counter : ImageView = view as ImageView

        val tappedCounter = (counter.tag).toString().toInt()

        val playAgainButton : Button = findViewById(R.id.button)
        val winnerTextView : TextView = findViewById(R.id.WinnertextView)

        if(gameState[tappedCounter] == -1 && gameActive){
            gameState[tappedCounter] = activePlayer

            counter.translationY = -1500f
            activePlayer = if(activePlayer == 0){
                counter.setImageResource(R.drawable.yellow)
                1
            } else{
                counter.setImageResource(R.drawable.red)
                0
            }

            counter.animate().translationYBy(1500f).rotationBy(3600f).duration = 300
            cnt--

            for (winningPosition in winningPosition){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]]){
                    if(gameState[winningPosition[0]] != -1){
                        //Someone has won
                        gameActive = false

                        val winner : String = if(activePlayer == 0){
                            "Red"
                        } else{
                            "Yellow"
                        }

                        winnerTextView.text = "$winner has won!!"

                        playAgainButton.visibility = View.VISIBLE
                        winnerTextView.visibility = View.VISIBLE
                    }
                }
            }

            if(cnt == 0 && gameActive){
                // No one won
                gameActive = false

                winnerTextView.text = "No one has won!!" //+ System.getProperty("line.separator") + "It's a Tie!!"

                playAgainButton.visibility = View.VISIBLE
                winnerTextView.visibility = View.VISIBLE

            }
        }
    }

    fun playAgain(view : View){
        val playAgainButton : Button = findViewById(R.id.button)
        val winnerTextView : TextView = findViewById(R.id.WinnertextView)

        playAgainButton.visibility = View.INVISIBLE
        winnerTextView.visibility = View.INVISIBLE

        val gridLayout : GridLayout = findViewById(R.id.gridLayout)

        for(i in 0..gridLayout.childCount) {
            (gridLayout.getChildAt(i) as? ImageView)?.setImageDrawable(null)
        }

        for(i in 0..8){
            gameState[i] = -1
        }

        activePlayer = 0

        gameActive = true

        cnt = 9
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            setContentView(R.layout.activity_main)
        }
    }
}
