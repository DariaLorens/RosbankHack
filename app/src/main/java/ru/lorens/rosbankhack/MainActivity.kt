package ru.lorens.rosbankhack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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


        val intent = Intent(this, CardActivity::class.java)
        intent.putExtra("cardArticleId", 1)
        startActivity(intent)
    }

    private fun getData() {
        GlobalScope.launch {
            val usersList = withContext(Dispatchers.Default) {
                try {
                    RestClient.getClient.getUsers().users
                } catch (e: Throwable) {
                    print(e)
                    null
                }
            }

            if (usersList != null) {
                runOnUiThread {

                }
            }
        }
    }
}

