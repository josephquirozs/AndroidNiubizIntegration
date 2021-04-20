package com.example.androidniubizintegration

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val TAG = "Payment"
    private lateinit var mainScreen: View
    private lateinit var openFormButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainScreen = findViewById(R.id.screen_main)
        openFormButton = findViewById(R.id.button_open_form)
        openFormButton.setOnClickListener {
            GlobalScope.launch {
                openPaymentForm()
            }
        }
    }

    private suspend fun openPaymentForm() {
        try {
            val data = mapOf(
                VisaNet.VISANET_CHANNEL to VisaNet.Channel.MOBILE,
                VisaNet.VISANET_COUNTABLE to true,
                VisaNet.VISANET_SECURITY_TOKEN to getSecurityToken(),
                VisaNet.VISANET_MERCHANT to "456879852",
                VisaNet.VISANET_PURCHASE_NUMBER to "1790",
                VisaNet.VISANET_AMOUNT to "15.22",
                VisaNet.VISANET_ENDPOINT_URL to "https://apisandbox.vnforappstest.com/",
                VisaNet.VISANET_CERTIFICATE_HOST to "apisandbox.vnforappstest.com",
                VisaNet.VISANET_CERTIFICATE_PIN to
                        "sha256/lmxiL6uol7hb4UwDxtk2qbF2lBnJc7zqZRT6sFfYWEE=",
                VisaNet.VISANET_REGISTER_NAME to "Juan",
                VisaNet.VISANET_REGISTER_LASTNAME to "Perez",
                VisaNet.VISANET_REGISTER_EMAIL to "jperez@test.com",
            )
            val custom = VisaNetViewAuthorizationCustom().apply {
                logoImage = R.drawable.tulogo
                buttonColorMerchant = R.color.visanet_black
            }
            VisaNet.authorization(this@MainActivity, data, custom)
        } catch (e: Exception) {
            e.printStackTrace()
            Snackbar.make(
                mainScreen,
                "Unable to open payment form. Check logs for details",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            VisaNet.VISANET_AUTHORIZATION -> {
                val response = when (resultCode) {
                    RESULT_OK -> data?.extras?.getString("keySuccess")
                    else -> data?.extras?.getString("keyError")
                }
                Log.i(TAG, "Payment form response $response")
            }
            else -> Log.i(TAG, "Request code not implemented")
        }
    }

    private suspend fun getSecurityToken(): String = withContext(Dispatchers.IO) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apisandbox.vnforappstest.com/api.security/v1/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            ).build()
        val encodedCredentials =
            encodeCredentials("integraciones@niubiz.com.pe", "_7z3@8fF")
        val headers = mapOf(
            "Authorization" to "Basic $encodedCredentials",
        )
        val call = retrofit.create(SecurityService::class.java)
            .getToken(headers)
        Log.i(TAG, "Request get url ${call.request().url}")
        val response = call.awaitResponse()
        Log.i(TAG, "Response status ${response.code()}")
        Log.i(TAG, "Response body ${response.body()}")
        response.body() ?: throw IOException("Unable to get security token")
    }

    private fun encodeCredentials(username: String, password: String): String {
        val credentials = "$username:$password"
        return Base64.encodeToString(
            credentials.toByteArray(charset("UTF-8")),
            Base64.NO_WRAP
        )
    }
}