package beepbeep.learning_mvvm.mvpvm

import io.reactivex.Observable

interface MvpVmContract {
    interface Input {
        val name: Observable<String>
        val favoriteAnimal: Observable<String>
        val buttonEvent: Observable<Unit>
    }

    interface Output {
        val outputViewModel: Observable<MvpVmViewModel>
    }
}