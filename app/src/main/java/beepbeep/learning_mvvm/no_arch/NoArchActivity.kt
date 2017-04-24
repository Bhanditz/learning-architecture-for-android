package beepbeep.learning_mvvm.no_arch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import beepbeep.learning_mvvm.util.TextWatcherImp
import kotlinx.android.synthetic.main.activity_mvp.*

class NoArchActivity : AppCompatActivity() {

    var name = ""
    var animalName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shared)
        supportActionBar?.title = getString(R.string.menu_no_arch)

        nameEditText.addTextChangedListener(TextWatcherImp { s, start, before, count ->
            name = s.toString()
            updateDisplayString()
        })

        favoriteAnimalEditText.addTextChangedListener(TextWatcherImp { s, start, before, count ->
            animalName = s.toString()
            updateDisplayString()
        })

    }

    fun updateDisplayString() {
        displayTextView.text = name + " likes " + animalName
    }
}