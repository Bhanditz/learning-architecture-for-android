package beepbeep.learning_mvvm.animal

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_animal.*

class AnimalActivity : AppCompatActivity(), AnimalContract.Input {

    lateinit var connector: AnimalPresenter;
    val initialTrigger = BehaviorSubject.createDefault<Unit>(Unit);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal)
        connector = AnimalPresenter(this, AnimalRepo())
        bindViews()
    }

    override fun onCreate(): Observable<Unit> {
        return initialTrigger
    }

    fun bindViews() {
        connector.viewModels.subscribe({
            animalTextView.setText(it.animalName)
        })
    }
}
