package ru.lorens.rosbankhack.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_screen.view.*
import ru.lorens.rosbankhack.R
import ru.lorens.rosbankhack.rest.Screen


class ScreenFragment(val screen: Screen) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_screen, container, false)
        view.conteiner.setBackgroundColor(Color.parseColor(screen.back_color))
        val anim = AnimationUtils.loadAnimation(view.context, R.anim.tv_animation)
        if (screen.title != null){
            view.titleText.text = screen.title
            view.titleText.visibility = View.VISIBLE
            view.titleText.startAnimation(anim)
        }
        if (screen.desc != null){
            view.descText.text = screen.desc
            view.descText.visibility = View.VISIBLE
                view.titleText.startAnimation(anim)
        }
        if (screen.image != null){
            Glide
                .with(view.context)
                .load(screen.image)
                .centerCrop()
                .into(view.image)
            view.image.visibility = View.VISIBLE
        }
        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}