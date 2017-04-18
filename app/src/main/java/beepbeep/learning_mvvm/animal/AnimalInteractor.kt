package beepbeep.learning_mvvm.animal

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AnimalInteractor(input: AnimalContract.Input, val repo: AnimalContract.Repo) : AnimalContract.Output {

    private val publisher = BehaviorSubject.createDefault<AnimalViewModel>(AnimalViewModel(""))

    init {
        input.onCreate().flatMap { repo.getAnimals() }.map {
            AnimalViewModel(it)
        }.subscribeWith(publisher)
    }

    override val viewModels: Observable<AnimalViewModel> =
            publisher.distinctUntilChanged()
}