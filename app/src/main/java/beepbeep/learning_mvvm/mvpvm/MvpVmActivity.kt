package beepbeep.learning_mvvm.mvpvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_shared.*

class MvpVmActivity : AppCompatActivity() {

    lateinit var connector: MvpVmPresenter
    val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)
        supportActionBar?.title = getString(R.string.menu_mvpvm_rxjava)

        connector = MvpVmPresenter(object : MvpVmContract.Input {
            override val name: Observable<String>
                get() = RxTextView.textChanges(nameEditText).map { it.toString() }
            override val favoriteAnimal: Observable<String>
                get() = RxTextView.textChanges(favoriteAnimalEditText).map { it.toString() }
            override val buttonEvent: Observable<Unit>
                get() = RxView.clicks(submitButton).map { Unit }
        })

        disposables.add(connector.outputViewModel.subscribe {
            displayTextView.text = it.displayString
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
