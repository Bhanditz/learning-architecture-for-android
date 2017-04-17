package beepbeep.learning_mvvm.animal

import io.reactivex.Observable

interface AnimalContract {
    interface Input {
        fun onCreate(): Observable<Unit>
    }
}