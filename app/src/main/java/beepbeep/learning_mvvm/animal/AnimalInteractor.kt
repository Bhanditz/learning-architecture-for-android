package beepbeep.learning_mvvm.animal

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AnimalInteractor(val repo: RepoInterface) {

    private val publisher = BehaviorSubject.createDefault<AnimalViewModel>(AnimalViewModel(""))

    val viewModels: Observable<AnimalViewModel> =
            publisher.distinctUntilChanged()

    fun doStuff() {
        repo.getAnimals().subscribe({
            publisher.onNext(AnimalViewModel(it))
        })
    }
}