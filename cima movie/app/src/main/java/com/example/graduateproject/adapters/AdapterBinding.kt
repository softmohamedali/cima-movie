package com.example.graduateproject.adapters


import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import coil.load
import com.example.graduateproject.R
import com.example.graduateproject.models.Result
import com.example.graduateproject.models.ResultX
import com.example.graduateproject.models.ResultXX
import com.example.graduateproject.ui.fragment.*
import com.example.graduateproject.util.Constants

class AdapterBinding {

    companion object
    {
        @BindingAdapter("setImg",requireAll = true)
        @JvmStatic
        fun setImg(imgview:ImageView,url:String){
            imgview.load(Constants.BASE_IMG_URL+url){
                this.crossfade(600)
            }
        }

        @BindingAdapter("setDouble",requireAll = true)
        @JvmStatic
        fun setDouble(text:TextView,num:Double){
            text.text=num.toString()
        }

        @BindingAdapter("setOnClickcard",requireAll = true)
        @JvmStatic
        fun setOnClickMovie(view:ConstraintLayout,result:Any){
            view.setOnClickListener {
                if (view.id==R.id.con_cardmovie)
                {

                    val action=HomeFragmentDirections.actionHomeItemToDetailsFragment(result as Result,null,null)
                    view.findNavController().navigate(action)

                }
                if(view.id==R.id.con_cardseries)
                {
                    val action=HomeFragmentDirections.actionHomeItemToDetailsFragment(null,result as ResultX,null)
                    view.findNavController().navigate(action)
                }
                if (view.id==R.id.con_cardPepole)
                {
                    val action=HomeFragmentDirections.actionHomeItemToDetailsFragment(null,null,result as ResultXX)
                    view.findNavController().navigate(action)
                }

            }

        }

        @BindingAdapter("setOnClickcardMovie",requireAll = true)
        @JvmStatic
        fun setOnClickcardMovie(view:ConstraintLayout,result:Any){
            view.setOnClickListener {
                if (view.id==R.id.con2_cardmovie)
                {
                    val action=MovieFragmentDirections.actionMovieItemToDetailsFragment(result as Result,null,null)
                    view.findNavController().navigate(action)
                }
                if(view.id==R.id.con2_cardseries)
                {
                    val action=TvFragmentDirections.actionTvItemToDetailsFragment(null,result as ResultX,null)
                    view.findNavController().navigate(action)
                }
                if (view.id==R.id.con2_cardPepole)
                {
                    val action=PersonFragmentDirections.actionPersonItemToDetailsFragment2(null,null,result as ResultXX)
                    view.findNavController().navigate(action)
                }

            }

        }
        @BindingAdapter("setOnClickcardMovie2",requireAll = true)
        @JvmStatic
        fun setOnClickcardMovie2(view:ConstraintLayout,result:Any){
            view.setOnClickListener {
                if (view.id==R.id.con3_cardmovie)
                {
                    val action=SearchFragmentDirections.actionSearchFragmentToDetailsFragment(result as Result,null,null)
                    view.findNavController().navigate(action)
                }


            }

        }







    }

}