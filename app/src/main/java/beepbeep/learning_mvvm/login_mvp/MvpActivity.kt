package beepbeep.learning_mvvm.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*

class MvpActivity : AppCompatActivity() {

    lateinit var connector: LoginInteractor;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        connector = LoginInteractor(object : LoginContract.Input {
            override val name: Observable<String>
                get() = RxTextView.textChanges(nameEditText).map { it.toString() }
            override val favoriteAnimal: Observable<String>
                get() = RxTextView.textChanges(favoriteAnimalEditText).map { it.toString() }
            override val buttonEvent: Observable<Unit>
                get() = RxView.clicks(submitButton).map { Unit }
        })

        connector.outputViewModel.subscribe {
            displayTextView.text = it.displayString
        }
    }
}
