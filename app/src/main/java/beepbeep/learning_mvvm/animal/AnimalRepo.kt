package beepbeep.learning_mvvm.animal

import io.reactivex.Observable

class AnimalRepo : AnimalContract.Repo {
    override fun getAnimals(): Observable<String> {
        return Observable.just<String>("(^≗ω≗^)\n miao~♥")
    }
}