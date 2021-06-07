package ru.btelepov.moviemix.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.btelepov.moviemix.databinding.ActivityAuthBinding
import ru.btelepov.moviemix.models.auth.RequestLoginBody
import ru.btelepov.moviemix.models.auth.SessionId
import ru.btelepov.moviemix.utils.CustomLogger
import ru.btelepov.moviemix.utils.Functions.Companion.showToast
import ru.btelepov.moviemix.utils.NetworkResult
import ru.btelepov.moviemix.utils.SessionManager
import ru.btelepov.moviemix.viewmodels.AuthActivityViewModel

@AndroidEntryPoint
class AuthActivity : AppCompatActivity(), CustomLogger {

    private val authActivityViewModel: AuthActivityViewModel by viewModels()

    private lateinit var sessionManager: SessionManager

    private var requestToken: String? = null
    private lateinit var sessionId: SessionId


    override val tag: String
        get() = AuthActivity::class.java.simpleName


    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sessionManager = SessionManager(this)

        binding.btnLogin.setOnClickListener {
            fetchToken()

        }
    }


    private fun fetchToken() {
        authActivityViewModel.createToken()
        authActivityViewModel.requestTokenResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    requestToken = response.data?.requestToken ?: "Empty :("
                    login()

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }


    private fun login() {

        val password = binding.etInputPassword.text.toString().trim()
        val login = binding.etInputLogin.text.toString().trim()
        val requestLoginBody = requestToken?.let {
            RequestLoginBody(
                password,
                it, login
            )
        }

        sessionId = requestToken?.let { SessionId(it) }!!



        if (requestLoginBody != null) {
            authActivityViewModel.login(requestLoginBody)
        }


        authActivityViewModel.sessionWithLoginResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    getSessionId(sessionId)
                    startActivity(intent)
                    finish()
                }
                is NetworkResult.Error -> {
                    showToast(response.message.toString())

                }
                is NetworkResult.Loading -> {

                }
            }

        }


    }

    private fun getSessionId(sessionId: SessionId) {
        authActivityViewModel.getSessionId(sessionId)
        authActivityViewModel.sessionIdResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    sessionManager.saveSessionId(response.data?.session_id)

                }

                is NetworkResult.Error -> {
                    showToast(response.message.toString())
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }


    override fun showLog(message: String) {
        Log.d(tag, message)
    }


}