package io.github.fobo66.factcheckerassistant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.fobo66.factcheckerassistant.ui.main.MainFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment::class.java, null)
                .commitNow()
        }
    }
}