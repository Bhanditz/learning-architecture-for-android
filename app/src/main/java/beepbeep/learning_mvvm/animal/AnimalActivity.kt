package beepbeep.learning_mvvm.animal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import kotlinx.android.synthetic.main.activity_animal.*

class AnimalActivity : AppCompatActivity() {

    lateinit var connector: AnimalInteractor;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal)
        connector = AnimalInteractor(AnimalRepo())
        bindViews()
        connector.doStuff()
    }

    fun bindViews() {
        connector.viewModels.subscribe({
            animalTextView.setText(it.animalName)
        })
    }
}
