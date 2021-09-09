package com.example.graduateproject.ui.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.graduateproject.R
import com.example.graduateproject.util.StatusResult
import com.example.graduateproject.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var mview:View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_register, container, false)



        mview.btn_rigister_registerfrag.setOnClickListener {

            val email=mview.et_email_registerfrag.text.toString()
            val password=mview.et_password_regiterfrag.text.toString()

            if (email.isEmpty())
            {
                mview.et_email_registerfrag.error="email empty"
                mview.et_email_registerfrag.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                mview.et_email_registerfrag.error="email not vaild"
                mview.et_email_registerfrag.requestFocus()
                return@setOnClickListener
            }
            if (password.length<1)
            {
                mview.et_password_regiterfrag.error="password is empty"
                mview.et_password_regiterfrag.requestFocus()
                return@setOnClickListener
            }
            if (password.length<6)
            {
                mview.et_password_regiterfrag.error="password length less than 6"
                mview.et_password_regiterfrag.requestFocus()
                return@setOnClickListener
            }
            userViewModel.createEmailWithEmail("",email,password)
        }
        userViewModel.isRegester.observe(viewLifecycleOwner, {
            when {
                it is StatusResult.OnLoading -> {
                    showProgress(true)
                }
                it is StatusResult.OnError -> {
                    Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                    showProgress(false)
                }
                it is StatusResult.OnSuccess -> {
                    findNavController().navigate(R.id.action_registerFragment2_to_logInFragment2)
                    showProgress(false)
                }
            }
        })

        return mview
    }

    fun showProgress(show:Boolean){
        if (show){
            mview.prog_registerfrag.visibility=View.VISIBLE
        }else{
            mview.prog_registerfrag.visibility=View.INVISIBLE
        }
    }
}