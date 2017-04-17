package beepbeep.learning_mvvm.animal

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AnimalInteractor(input: AnimalContract.Input, val repo: RepoInterface) {

    private val publisher = BehaviorSubject.createDefault<AnimalViewModel>(AnimalViewModel(""))

    init {
        input.onCreate().flatMap { repo.getAnimals() }.map {
            AnimalViewModel(it)
        }.subscribeWith(publisher)
    }

    val viewModels: Observable<AnimalViewModel> =
            publisher.distinctUntilChanged()
}