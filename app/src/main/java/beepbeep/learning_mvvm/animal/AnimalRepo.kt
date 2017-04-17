package beepbeep.learning_mvvm.animal

import io.reactivex.Observable

class AnimalRepo : RepoInterface {
    override fun getAnimals(): Observable<String> {
        return Observable.just<String>("(^≗ω≗^)")
    }
}