package com.example.androidniubizintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import lib.visanet.com.pe.visanetlib.VisaNet
import lib.visanet.com.pe.visanetlib.presentation.custom.VisaNetViewAuthorizationCustom

class MainActivity : AppCompatActivity() {
    private val TAG = "Niubiz"
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
            "eyJraWQiOiJmWk1tV3pZR0RBckxHektvalNCK2w3SjFhMnNPXC9zQnNwOTlNNmNuM3F5MD0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI0NzFmYjk3Yy0xNjNlLTQyYjYtOGI3OC03Zjk1YjA1OGM1NTgiLCJldmVudF9pZCI6ImI3NjU4YzZlLWZlMmYtNDJjNC05YjZkLTZkZTFkNjhiZDczYyIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2MTExNzQ2OTgsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xXzJjSjFTZTFmSSIsImV4cCI6MTYxMTE3ODI5OCwiaWF0IjoxNjExMTc0Njk4LCJqdGkiOiIyMzU3NWQ2Ny0wZTFhLTQzYmMtOTMzMy05YmQwMDNhMmZiZGQiLCJjbGllbnRfaWQiOiIxMGx2MDYxN281ZGljNTFlYnNucWVpaWpiNyIsInVzZXJuYW1lIjoiaW50ZWdyYWNpb25lc0BuaXViaXouY29tLnBlIn0.W8b-e-QgGVaYbvyNSiMTTcqDzU97xDFViwsYCatGq5zKpfXfB5FFm_2yuWZM0hKkIy5ofmAsc4HTHO_qpappnQFYVvF_f2Ti-jBnaD8XcQrxYEIZT2vfnLKM6vBQIzjUltbwIAQQG5iHrHQ0WNoLzSLVvcDyuL12xsB8GnmVVrDfwXDBZOJZ-pgkMEsbRDFNVGJu3CCoV3z6IHqf227hKkJ_MdoQaXVeKnsKw9_rsUQ-8MKz9KR2YhXWlmd7qjy2nWf6uqoR0qFiz9OTeuWSNon6xwq9Fy9rMT5CITE25om4W3axsjUGXF6SmWrtQ6yNg5hj6MMkYtL9USSLX6Zplw"
        data[VisaNet.VISANET_MERCHANT] = "456879852"
        data[VisaNet.VISANET_PURCHASE_NUMBER] = "1790"
        data[VisaNet.VISANET_AMOUNT] = "15.22"
        data[VisaNet.VISANET_ENDPOINT_URL] = "https://apisandbox.vnforappstest.com/"
        data[VisaNet.VISANET_CERTIFICATE_HOST] = "apisandbox.vnforappstest.com"
        data[VisaNet.VISANET_CERTIFICATE_PIN] =
            "sha256/lmxiL6uol7hb4UwDxtk2qbF2lBnJc7zqZRT6sFfYWEE="
        data[VisaNet.VISANET_REGISTER_NAME] = "Juan";
        data[VisaNet.VISANET_REGISTER_LASTNAME] = "Perez";
        data[VisaNet.VISANET_REGISTER_EMAIL] = "jperez@test.com";

        val custom = VisaNetViewAuthorizationCustom()
        custom.logoImage = R.drawable.tulogo
        custom.buttonColorMerchant = R.color.visanet_black

        try {
            VisaNet.authorization(this@MainActivity, data, custom)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(TAG, "onClick: " + e.message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VisaNet.VISANET_AUTHORIZATION) {
            // TODO: Implementar respuesta del formulario
        } else {
            Toast.makeText(applicationContext, "Unauthorized", Toast.LENGTH_LONG).show()
        }
    }
}