package beepbeep.learning_mvvm.animal

import io.reactivex.Observable

interface AnimalContract {
    interface AnimalView {
        val textViewModel: Observable<String>
    }
}