package beepbeep.learning_mvvm.mvpvm

import io.reactivex.Observable

interface MvvmRxContract {
    interface Input {
        fun onNameInput(s: CharSequence?)
        fun onAnimalNameInput(s: CharSequence?)
        fun onSubmitButtonClick()
    }

    interface Output {
        val outputViewModel: Observable<MvpVmViewModel>
    }
}