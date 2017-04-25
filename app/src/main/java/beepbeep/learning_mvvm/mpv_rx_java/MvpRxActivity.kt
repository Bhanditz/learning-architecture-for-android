package beepbeep.learning_mvvm.mpv_rx_java

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_mvp.*

class MvpRxActivity : AppCompatActivity(), MvpRxView {
    private val onDestroySignal = BehaviorSubject.create<Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)
        MvpRxPresenter().attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        onDestroySignal.onNext(Unit)
    }

    override fun onDisplayStringUpdate(displayString: String) {
        displayTextView.text = displayString
    }

    override fun setActionBarTitle(titleId: Int) {
        supportActionBar?.title = getString(R.string.menu_mvp)
    }

    override fun nameInputs(): Observable<String> = RxTextView.textChanges(nameEditText).map { it.toString() }

    override fun animalNameInputs(): Observable<String> = RxTextView.textChanges(favoriteAnimalEditText).map { it.toString() }

    override fun tearDown(): Observable<Unit> = onDestroySignal
}
