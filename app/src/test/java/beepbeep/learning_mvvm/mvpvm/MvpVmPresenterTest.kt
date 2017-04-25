package beepbeep.learning_mvvm.mvpvm

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class MvpVmPresenterTest {

    @Test
    fun displayCorrectly() {
        // input
        val nameSubject = PublishSubject.create<String>()
        val favoriteAnimalSubject = PublishSubject.create<String>()

        val input = object : MvpVmContract.Input {
            override val name: PublishSubject<String>
                get() = nameSubject
            override val favoriteAnimal: PublishSubject<String>
                get() = favoriteAnimalSubject
        }

        // setup black box
        val blackBox = MvpVmPresenter(input)

        // setup tester
        val testObserver = TestObserver<MvpVmViewModel>()
        blackBox.outputViewModel.subscribe(testObserver)

        // case: setup inputName and favorite animal, but not clicking button
        nameSubject.onNext("JR")
        favoriteAnimalSubject.onNext("cat")

        testObserver.assertValueAt(1) {
            it.displayString == "JR likes cat"
        }

        nameSubject.onNext("Mr. Yama")
        testObserver.assertValueAt(2) {
            it.displayString == "Mr. Yama likes cat"
        }

        favoriteAnimalSubject.onNext("corgi")
        testObserver.assertValueAt(3) {
            it.displayString == "Mr. Yama likes corgi"
        }
    }
}