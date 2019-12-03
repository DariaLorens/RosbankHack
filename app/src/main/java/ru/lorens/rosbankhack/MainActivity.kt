package ru.lorens.rosbankhack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.lorens.rosbankhack.rest.RestClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

