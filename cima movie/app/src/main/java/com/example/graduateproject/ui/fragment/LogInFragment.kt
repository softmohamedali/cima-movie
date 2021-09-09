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
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_log_in.view.*

@AndroidEntryPoint
class LogInFragment : Fragment() {

    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var mview:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_log_in, container, false)

        mview.btn_login_loginfrag.setOnClickListener {
            mview.btn_login_loginfrag.setOnClickListener {
                val email = mview.et_email_loginfrag.text.toString()
                val password = mview.et_password_loginfrag.text.toString()
                if (email.isEmpty()) {
                    mview.et_email_loginfrag.error = "email empty"
                    mview.et_email_loginfrag.requestFocus()
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mview.et_email_loginfrag.error = "email not vaild"
                    mview.et_email_loginfrag.requestFocus()
                    return@setOnClickListener
                }
                if (password.length < 1) {
                    mview.et_password_loginfrag.error = "password is empty"
                    mview.et_password_loginfrag.requestFocus()
                    return@setOnClickListener
                }
                if (password.length < 6) {
                    mview.et_password_loginfrag.error = "password length less than 6"
                    mview.et_password_loginfrag.requestFocus()
                    return@setOnClickListener
                }

                userViewModel.singInWithEmail(email, password)
            }
        }
        userViewModel.isSingIn.observe(viewLifecycleOwner, {
               cheakUserStatus(it)
        })
        mview.btn_rigister_loginfrag.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment2_to_registerFragment2)
        }
        return mview
    }

    fun showProgress(show:Boolean){
        if (show){
            mview.prog_loginfrag.visibility=View.VISIBLE
        }else{
            mview.prog_loginfrag.visibility=View.INVISIBLE
        }
    }

    private fun cheakUserStatus(it: StatusResult<FirebaseUser>?) {
        when {
            it is StatusResult.OnLoading -> {
                showProgress(true)
            }
            it is StatusResult.OnError -> {
                Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                showProgress(false)
            }
            it is StatusResult.OnSuccess -> {

                findNavController().navigate(R.id.action_logInFragment2_to_mainActivity2)
                showProgress(false)
            }
        }
    }

}