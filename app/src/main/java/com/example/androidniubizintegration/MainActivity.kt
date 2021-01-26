package com.example.androidniubizintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom

class MainActivity : AppCompatActivity() {
    private val TAG = "Payment"
    private lateinit var openFormButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openFormButton = findViewById(R.id.button_open_form)
        openFormButton.setOnClickListener {
            openPaymentForm()
        }
    }

    private fun openPaymentForm() {
        val data = HashMap<String, Any>()
        data[VisaNet.VISANET_CHANNEL] = VisaNet.Channel.MOBILE
        data[VisaNet.VISANET_COUNTABLE] = true
        data[VisaNet.VISANET_SECURITY_TOKEN] =
            "eyJraWQiOiJmWk1tV3pZR0RBckxHektvalNCK2w3SjFhMnNPXC9zQnNwOTlNNmNuM3F5MD0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI0NzFmYjk3Yy0xNjNlLTQyYjYtOGI3OC03Zjk1YjA1OGM1NTgiLCJldmVudF9pZCI6Ijg3YjRlNDliLWMxMDAtNDExNS05MGY0LThlNmU4MDlhOTQ2ZCIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2MTE2NzQ1NjIsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xXzJjSjFTZTFmSSIsImV4cCI6MTYxMTY3ODE2MiwiaWF0IjoxNjExNjc0NTYyLCJqdGkiOiJlYWE5YzY4Ny01MjRmLTQxYzQtYWQyZS0yNDEyZTVlZTBmMzUiLCJjbGllbnRfaWQiOiIxMGx2MDYxN281ZGljNTFlYnNucWVpaWpiNyIsInVzZXJuYW1lIjoiaW50ZWdyYWNpb25lc0BuaXViaXouY29tLnBlIn0.T_fu0ZrHZfZxMNECWCBa59PRONin0CXnKwcfaqnwvLUBsaWKIeK6lbT4yVB0ps0Ubo4o0JbD5XAMw5jmZNzAG3e6K_Qt1kRRzpTZmZlBNmwFPcmvTSwiiT_ODbLV3o8DwiNUoHJDQL7pQMuAoBwgc_w6-YLNcq1Zip5A1YMMxMoBPf6DKxuljcDuvhC_BRpD395nQ4pxWZO4XGlrPQ1Yl-w_ynEMuFKMkbsaYBLOroX67Ln-wtrjzNDnijPgNywctoAJjj1jarIiF3RXeyVyJCfZKRVJt6hH95S1dXSwYbjBSuuuP_FUszMwABQUD1LNOcabnFbyzkLcWA19ElxLvA"
        data[VisaNet.VISANET_MERCHANT] = "456879852"
        data[VisaNet.VISANET_PURCHASE_NUMBER] = "1790"
        data[VisaNet.VISANET_AMOUNT] = "15.22"
        data[VisaNet.VISANET_ENDPOINT_URL] = "https://apisandbox.vnforappstest.com/"
        data[VisaNet.VISANET_CERTIFICATE_HOST] = "apisandbox.vnforappstest.com"
        data[VisaNet.VISANET_CERTIFICATE_PIN] =
            "sha256/lmxiL6uol7hb4UwDxtk2qbF2lBnJc7zqZRT6sFfYWEE="
        data[VisaNet.VISANET_REGISTER_NAME] = "Juan"
        data[VisaNet.VISANET_REGISTER_LASTNAME] = "Perez"
        data[VisaNet.VISANET_REGISTER_EMAIL] = "jperez@test.com"
//        data[VisaNet.VISANET_USER_TOKEN] = "demo@gmail.com"

        val custom = VisaNetViewAuthorizationCustom()
        custom.logoImage = R.drawable.tulogo
        custom.buttonColorMerchant = R.color.visanet_black

        try {
            VisaNet.authorization(this@MainActivity, data, custom)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            VisaNet.VISANET_AUTHORIZATION -> {
                // TODO: Implementar respuesta del formulario
            }
            else -> Log.i(TAG, "Request code not implemented")
        }
    }
}