package com.xanroid.mybubblescandit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xanroid.mybubblescandit.fragment.ScanFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ScanFragment::class.java, null)
            .commit()

    }
}