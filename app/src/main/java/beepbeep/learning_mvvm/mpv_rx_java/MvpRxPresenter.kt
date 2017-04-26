package beepbeep.learning_mvvm.mpv_rx_java

import beepbeep.learning_mvvm.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction

class MvpRxPresenter {
    private var disposableBag = CompositeDisposable()

    fun attachView(view: MvpRxView) {
        view.setActionBarTitle(R.string.menu_mvp_rx_java)

        Observable.combineLatest(view.nameInputs(), view.animalNameInputs(),
                BiFunction<String, String, String> {
                    name: String, favoriteAnimal: String -> "$name likes $favoriteAnimal"
                })
                .doOnNext { view.onDisplayStringUpdate(it) }
                .subscribe()
                .addTo(disposableBag)

        view.tearDown()
                .subscribe {
                    disposableBag.clear()
                }.addTo(disposableBag)
    }

    fun Disposable.addTo(composite: CompositeDisposable) = composite.add(this)
}