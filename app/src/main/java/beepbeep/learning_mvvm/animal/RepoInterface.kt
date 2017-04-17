package beepbeep.learning_mvvm.animal

import io.reactivex.Observable

interface RepoInterface {
    fun getAnimals(): Observable<String>
}