package com.kreambread.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_show.setOnClickListener {
            KreamNotification.show(this, "Say", "Hello, this is YouTube style notification example.")
        }
    }
}
