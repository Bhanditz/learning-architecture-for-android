package beepbeep.learning_mvvm.login

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test

class LoginInteractorTest {

    @Test
    fun normalFlow() {

        // input
        val nameSubject = PublishSubject.create<String>()
        val favoriteAnimalSubject = PublishSubject.create<String>()
        val buttonEventSubject = PublishSubject.create<Unit>()

        val input = object : LoginContract.Input {
            override val name: PublishSubject<String>
                get() = nameSubject
            override val favoriteAnimal: PublishSubject<String>
                get() = favoriteAnimalSubject
            override val buttonEvent: PublishSubject<Unit>
                get() = buttonEventSubject
        }

        // setup black box
        val blackBox = LoginInteractor(input)

        // setup tester
        val testObserver = TestObserver<LoginViewModel>()
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