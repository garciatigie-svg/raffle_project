package com.example.raffleroulette

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var roletaImage: ImageView
    private lateinit var spinButton: Button
    private lateinit var shuffleButton: Button
    private lateinit var resultText: TextView

    private var lastDegree = 0f

    // Full list of participants (40 entries)
    private var participants = mutableListOf(
        Pair(1, "Bong Gascon"),
        Pair(2, "Kaiven Charles"),
        Pair(3, "Tigie Garcia"),            // <- Final Winner
        Pair(4, "Saring Palisoc"),
        Pair(5, "Leilanie Macayana"),
        Pair(6, "Monica Castro"),
        Pair(7, "Ate Shaa"),
        Pair(8, "Calixto Oloa"),
        Pair(9, "Frank Giron"),
        Pair(10, "Jasmin Barcarse"),
        Pair(11, "Joy Amo"),
        Pair(12, "Elizabeth Valdez"),
        Pair(13, "Kaiven Charles"),
        Pair(14, "Frank Giron"),
        Pair(15, "Calixto Oloa"),
        Pair(16, "Belan"),
        Pair(17, "Leilanie Macayana"),
        Pair(18, "Calixto Oloa"),
        Pair(19, "Marijoy Manlang"),
        Pair(20, "Calixto Oloa"),
        Pair(21, "Mike Garcia"),
        Pair(22, "Mike Garcia"),
        Pair(23, "Joy Amo"),
        Pair(24, "Tigie Garcia"),
        Pair(25, "Calixto Oloa"),
        Pair(26, "Elizabeth Valdez"),
        Pair(27, "Gendalyn Maramba"),
        Pair(28, "Gendalyn Maramba"),
        Pair(29, "Marijoy Manlang"),
        Pair(30, "Vina Silva"),
        Pair(31, "Calixto Oloa"),
        Pair(32, "Winalyn Valles"),
        Pair(33, "Bong Gascon"),
        Pair(34, "Liza GP"),
        Pair(35, "Jorence Giron"),          // <- Final 2
        Pair(36, "ZD Urbien"),
        Pair(37, "Robz Pascual"),
        Pair(38, "Jaja Urbien"),
        Pair(39, "Bong Gascon"),
        Pair(40, "Robz Pascual")
    )

    private val finalTwo = listOf(3, 35)  // Dapat matira
    private val fixedWinner = 3            // Lagi panalo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roletaImage = findViewById(R.id.roletaImage)
        spinButton = findViewById(R.id.spinButton)
        shuffleButton = findViewById(R.id.shuffleButton)
        resultText = findViewById(R.id.resultText)

        spinButton.setOnClickListener {
            if (participants.size > 1) {
                spinWheel()
            } else {
                resultText.text = "🏆 Winner: ${participants[0].second} (No. ${participants[0].first})"
                spinButton.isEnabled = false
            }
        }

        shuffleButton.setOnClickListener {
            participants.shuffle()
            resultText.text = "🔀 Na-shuffle ang list ng players!"
        }
    }

    private fun spinWheel() {
        val randomDegree = Random.nextInt(360, 3600)
        val rotation = RotateAnimation(
            lastDegree,
            randomDegree.toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotation.duration = 3000
        rotation.fillAfter = true

        rotation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                var index = Random.nextInt(participants.size)
                var chosen = participants[index]

                // Huwag alisin ang finalTwo hanggang last round
                while (participants.size > 2 && chosen.first in finalTwo) {
                    index = Random.nextInt(participants.size)
                    chosen = participants[index]
                }

                if (participants.size > 2) {
                    // Normal elimination
                    resultText.text = "❌ Natanggal si: ${chosen.second} (No. ${chosen.first})"
                    participants.removeAt(index)
                } else if (participants.size == 2) {
                    // Final round: fixed winner
                    val winner = participants.first { it.first == fixedWinner }
                    resultText.text = "🏆 Winner: ${winner.second} (No. ${winner.first})"
                    participants.clear()
                    participants.add(winner)
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        roletaImage.startAnimation(rotation)
        lastDegree = (randomDegree % 360).toFloat()
    }
}
