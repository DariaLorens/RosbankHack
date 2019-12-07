package ru.lorens.rosbankhack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_card.*
import ru.lorens.rosbankhack.adapters.CardPageAdapter
import ru.lorens.rosbankhack.repositories.CardRepositories
import java.util.*
import kotlin.concurrent.schedule

class CardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        val cardId = intent.extras?.getInt("cardArticleId")

        val card = cardId?.let {
            CardRepositories.getCardsForArticleId(
                it
            )
        }
        if (card != null) {
            cardViewPager.adapter = CardPageAdapter(supportFragmentManager, card.screens)
            val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
            tabLayout.setupWithViewPager(cardViewPager, true)
            counterText.text = "${cardViewPager.currentItem + 1}/${card.screens.size}"

            refreshScroll(card.screens.size)

            textMore.setOnClickListener {
                startContentActivity(cardId)
            }

            animation_view.setOnClickListener {
                startContentActivity(cardId)
            }

            cardViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == card.screens.size - 1) {
                        refreshScroll(card.screens.size)
                    }
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    counterText.text = "${position + 1}/${card.screens.size}"
                    if (position + 1 == card.screens.size) {
                        animation_view.visibility = View.VISIBLE
                        textMore.visibility = View.VISIBLE
                    } else {
                        animation_view.visibility = View.INVISIBLE
                        textMore.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun startContentActivity(cardId: Int) {
        val intent = Intent(this, ContentActivity::class.java)
        intent.putExtra("cardArticleId", cardId)
        startActivity(intent)
    }

    fun refreshScroll(int: Int) {
        for (i in 0..int) {
            if (i != int - 1) {
                Timer().schedule(3000) {
                    runOnUiThread {
                        cardViewPager!!.setCurrentItem(cardViewPager.currentItem + 1, true)
                    }
                }
            }
        }
    }
}
