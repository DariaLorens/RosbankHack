package ru.lorens.rosbankhack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.rosbankhack.Consts.CLICK_REACTION
import ru.lorens.rosbankhack.Consts.CUSTOMER_ID
import ru.lorens.rosbankhack.adapters.PreviewRecyclerAdapter
import ru.lorens.rosbankhack.repositories.CardRepositories
import ru.lorens.rosbankhack.rest.Article
import ru.lorens.rosbankhack.rest.Card
import ru.lorens.rosbankhack.rest.RestClient
import ru.lorens.rosbankhack.rest.Screen
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
        progressBar.visibility = View.VISIBLE
    }

    private fun setRecycler() {
        val newAdapter = PreviewRecyclerAdapter(CardRepositories.cards) { element ->
            val intent = Intent(this, CardActivity::class.java)
            intent.putExtra("cardArticleId", element.article.id)
            startActivity(intent)

            openCard(element)
        }
        previewRecycler.setHasFixedSize(true)
        previewRecycler.onFlingListener = null
        val snapHelper = LinearSnapHelper() // Or PagerSnapHelper
        snapHelper.attachToRecyclerView(previewRecycler)
        previewRecycler.adapter = newAdapter
    }

    private fun openCard(element: Card) {
        CardRepositories.deleteCard(element)
        GlobalScope.launch {
            try {
                RestClient.getClient.reaction(
                    RestClient.Reaction(
                        user_id = CUSTOMER_ID,
                        article_id = element.article.id,
                        reaction = CLICK_REACTION
                    )
                )
            } catch (e: Throwable) {
                print(e)
                null
            }
        }
    }

    private fun getData() {
        GlobalScope.launch {
            val cardsList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getCards(RestClient.Customer(customer_id = CUSTOMER_ID))
                } catch (e: Throwable) {
                    print(e)
                    null
                }
            }

            if (cardsList != null) {
                cardsList.forEach { CardRepositories.cards.add(it) }
                runOnUiThread {
                    setRecycler()
                    progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}

