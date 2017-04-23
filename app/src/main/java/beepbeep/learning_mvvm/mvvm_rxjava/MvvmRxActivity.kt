package beepbeep.learning_mvvm.mvvm_rxjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import beepbeep.learning_mvvm.mvvm_databinding.MvvmRxViewModel
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_shared.*

class MvvmRxActivity : AppCompatActivity() {

    val rxViewModel = MvvmRxViewModel();
    val disposables = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)
        supportActionBar?.title = getString(R.string.menu_mvvm_rxjava)

        disposables.add(RxTextView.textChanges(nameEditText).doOnNext { rxViewModel.onNameInput(it.toString()) }.subscribe())
        disposables.add(RxTextView.textChanges(favoriteAnimalEditText).doOnNext { rxViewModel.onAnimalNameInput(it.toString()) }.subscribe())
        disposables.add(RxView.clicks(submitButton).doOnNext {
            rxViewModel.onSubmitButtonClick()
        }.subscribe())

        disposables.add(rxViewModel.outputPublisher.subscribe {
            displayTextView.text = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}