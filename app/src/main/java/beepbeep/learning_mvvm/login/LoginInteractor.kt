package beepbeep.learning_mvvm.login

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject

class LoginInteractor(val input: LoginContract.Input) : LoginContract.Output {

    val publisher: BehaviorSubject<LoginViewModel> = BehaviorSubject.createDefault<LoginViewModel>(LoginViewModel("", "", ""))
    override val outputViewModel: Observable<LoginViewModel> =
            input.buttonEvent.map { publisher.value }

    init {
        Observable
                .combineLatest(input.name, input.favoriteAnimal,
                        BiFunction<String, String, LoginViewModel> {
                            name, favoriteAnimal ->
                            LoginViewModel(name, favoriteAnimal, name + " likes " + favoriteAnimal)
                        }
                )
                .subscribe(publisher)
    }
}