package beepbeep.learning_mvvm.mvpvm

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class MvpVmPresenter(val input: MvpVmContract.Input) : MvpVmContract.Output {

    val publisher: BehaviorSubject<MvpVmViewModel> = BehaviorSubject.createDefault<MvpVmViewModel>(MvpVmViewModel("", "", ""))
    override val outputViewModel: Observable<MvpVmViewModel> =
            input.buttonEvent.map { publisher.value }

    init {
        Observable.combineLatest(input.name, input.favoriteAnimal,
                BiFunction<String, String, MvpVmViewModel> {
                    name, favoriteAnimal ->
                    MvpVmViewModel(name, favoriteAnimal, name + " likes " + favoriteAnimal)
                }
        ).subscribe(publisher)
    }
}