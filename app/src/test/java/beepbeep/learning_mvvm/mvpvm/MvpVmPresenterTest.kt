package beepbeep.learning_mvvm.mvpvm

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class MvpVmPresenterTest {

    @Test
    fun normalFlow() {
        // input
        val nameSubject = PublishSubject.create<String>()
        val favoriteAnimalSubject = PublishSubject.create<String>()
        val buttonEventSubject = PublishSubject.create<Unit>()

        val input = object : MvpVmContract.Input {
            override val name: PublishSubject<String>
                get() = nameSubject
            override val favoriteAnimal: PublishSubject<String>
                get() = favoriteAnimalSubject
            override val buttonEvent: PublishSubject<Unit>
                get() = buttonEventSubject
        }

        // setup black box
        val blackBox = MvpVmPresenter(input)

        // setup tester
        val testObserver = TestObserver<MvpVmViewModel>()
        blackBox.outputViewModel.subscribe(testObserver)

        // case: setup inputName and favorite animal, but not clicking button
        nameSubject.onNext("JR")
        favoriteAnimalSubject.onNext("cat")

        buttonEventSubject.onNext(Unit)
        testObserver.assertValue {
            it.displayString.equals("JR likes cat")
        }

        nameSubject.onNext("Mr. Yama")
        favoriteAnimalSubject.onNext("corgi")

        //value remains the same because button still not pressed
        testObserver.assertValue {
            it.displayString.equals("JR likes cat")
        }

        buttonEventSubject.onNext(Unit)
        testObserver.assertValueAt(1) {
            it.displayString.equals("Mr. Yama likes corgi")
        }
    }
}