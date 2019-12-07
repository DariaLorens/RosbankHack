package ru.lorens.rosbankhack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_card.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.lorens.rosbankhack.adapters.CardPageAdapter
import ru.lorens.rosbankhack.repositories.CardRepositories
import ru.lorens.rosbankhack.rest.RestClient
import ru.lorens.rosbankhack.rest.Screen
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

        checkBox.isEnabled = true

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

            buttonLike.setOnClickListener {
                buttonLike.setOnClickListener {  }
                buttonLike.setImageResource(R.drawable.ic_heart_enabled)
                GlobalScope.launch {
                    try {
                        RestClient.getClient.reaction(cardId, 87, Consts.LIKE_REACTION)
                    } catch (e: Throwable) {
                        print(e)
                        null
                    }
                }
            }

            rightArea.setOnClickListener { nextSlide(cardViewPager!!, card.screens) }
            leftArea.setOnClickListener { previewsSlide(cardViewPager!!, card.screens) }

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
                        checkBox.visibility = View.VISIBLE
                    } else {
                        animation_view.visibility = View.INVISIBLE
                        textMore.visibility = View.INVISIBLE
                        checkBox.visibility = View.INVISIBLE
                    }
                }
            })
        }
    }

    private fun nextSlide(cardViewPager: ViewPager, list: List<Screen>) {
        if (cardViewPager.currentItem + 1 != list.size) {
            cardViewPager.setCurrentItem(cardViewPager.currentItem + 1, true)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun previewsSlide(cardViewPager: ViewPager, list: List<Screen>) {
        if (cardViewPager.currentItem != 0) {
            cardViewPager.setCurrentItem(cardViewPager.currentItem - 1, true)
        }
    }

    private fun startContentActivity(cardId: Int) {
        val intent = Intent(this, ContentActivity::class.java)
        intent.putExtra("cardArticleId", cardId)
        startActivity(intent)

        GlobalScope.launch {
            try {
                RestClient.getClient.reaction(cardId, 87, Consts.READ_REACTION)
            } catch (e: Throwable) {
                print(e)
                null
            }
        }
    }

    fun refreshScroll(int: Int) {
        if (cardViewPager.currentItem + 1 != int) {
            Timer().schedule(5000) {
                runOnUiThread {
                    cardViewPager!!.setCurrentItem(cardViewPager.currentItem + 1, true)
                    refreshScroll(int)
                }
            }
        }
    }
}
