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
        val token =
            "eyJraWQiOiJmWk1tV3pZR0RBckxHektvalNCK2w3SjFhMnNPXC9zQnNwOTlNNmNuM3F5MD0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI0NzFmYjk3Yy0xNjNlLTQyYjYtOGI3OC03Zjk1YjA1OGM1NTgiLCJldmVudF9pZCI6IjFjNDY5N2QyLWJjZWQtNDIwMS1hN2QzLThkMmJjM2ExMDFlNSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoiYXdzLmNvZ25pdG8uc2lnbmluLnVzZXIuYWRtaW4iLCJhdXRoX3RpbWUiOjE2MTA3MTkxMjYsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xXzJjSjFTZTFmSSIsImV4cCI6MTYxMDcyMjcyNiwiaWF0IjoxNjEwNzE5MTI2LCJqdGkiOiJkMDdmODE5NC0xYTU0LTQzNzgtYWQzMC00YzA3MjgzNDUxY2IiLCJjbGllbnRfaWQiOiIxMGx2MDYxN281ZGljNTFlYnNucWVpaWpiNyIsInVzZXJuYW1lIjoiaW50ZWdyYWNpb25lc0BuaXViaXouY29tLnBlIn0.L6Dz_443xDSQT_FbOUQLjGe3YJwIdMeuJ9vdl6Rg403Ya51d2YCc7yZF-swE_9idZDzGQ2d1359Kfq1pWBc-DsoC0MO6YI2yXH0pHoKmlXB1Ooi-3lGPKIep1osPX-Bd1gqv6-jVXEst5OA8T5dXZTBw4YBWpp8PHdHyDqrf3c2dnyNCl_GpvT3T4Dxgo6pdhSBtHd5lMMi7jjq8VFpwdEERCtrWg75U6o5n6RxuWRt8qUYHVflQa5KCco2j1RuQaCHHiMKoh-5Nj5vfI5ro0QPL9AhPjbqVsejztpdX07AldhIXr5GZYHCiUzoJ5kZFMTRfGzPdN6nqZbm1mSXRRQ"

        val data = HashMap<String, Any>()
        data[VisaNet.VISANET_CHANNEL] = VisaNet.Channel.MOBILE
        data[VisaNet.VISANET_COUNTABLE] = true
        data[VisaNet.VISANET_SECURITY_TOKEN] = token
        data[VisaNet.VISANET_MERCHANT] = "456879852"
        data[VisaNet.VISANET_PURCHASE_NUMBER] = "1790"
        data[VisaNet.VISANET_AMOUNT] = "15.22"

//        val MDDdata = HashMap<String, String>()
//        MDDdata["19"] = "LIM"
//        MDDdata["20"] = "AQP"
//        MDDdata["21"] = "AFKI345"
//        MDDdata["94"] = "ABC123DEF"

//        data[VisaNet.VISANET_MDD] = MDDdata
        data[VisaNet.VISANET_ENDPOINT_URL] = "https://apisandbox.vnforappstest.com/"
        data[VisaNet.VISANET_CERTIFICATE_HOST] = "apisandbox.vnforappstest.com"
        data[VisaNet.VISANET_CERTIFICATE_PIN] =
            "sha256/lmxiL6uol7hb4UwDxtk2qbF2lBnJc7zqZRT6sFfYWEE="

        val custom = VisaNetViewAuthorizationCustom()
        custom.logoImage = R.drawable.tulogo
        custom.buttonColorMerchant = R.color.visanet_black

        try {
//            VisaNet.authorization(this@MainActivity, data, custom)
            VisaNet.authorization(this@MainActivity, data)
        } catch (e: Exception) {
            Log.i(TAG, "onClick: " + e.message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VisaNet.VISANET_AUTHORIZATION) {
            // TODO: Implementar respuesta del formulario
        } else {
            val toast = Toast.makeText(applicationContext, "Unauthorized", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}