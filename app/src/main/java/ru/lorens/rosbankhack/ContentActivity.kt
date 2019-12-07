package ru.lorens.rosbankhack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_content.*
import ru.lorens.rosbankhack.repositories.CardRepositories

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)


        val cardId = intent.extras?.getInt("cardArticleId")

        val card = cardId?.let {
            CardRepositories.getCardsForArticleId(
                it
            )
        }
        if (card != null) {

            Glide
                .with(this)
                .load(card.article.image)
                .centerCrop()
                .into(imageArtick)

            textTitleArticle.text = card.article.title
            textDescArticle.text = card.article.desc
        }
    }
}
