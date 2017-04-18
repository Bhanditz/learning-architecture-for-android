package beepbeep.learning_mvvm.animal

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.junit.Test


class AnimalInteractorTest {

    lateinit var interactor: AnimalInteractor

    @Test
    fun doStuff() {
        val repo: AnimalContract.Repo = object : AnimalContract.Repo {
            override fun getAnimals(): Observable<String> {
                return Observable.just<String>("ASDF ASDF")
            }
        }
        val trigger = PublishSubject.create<Unit>()
        val input = object : AnimalContract.Input {
            override fun onCreate(): Observable<Unit> {
                return trigger
            }
        }

        interactor = AnimalInteractor(input, repo)

        val testObserver = TestObserver<String>()

        interactor.viewModels.map { it.animalName }.subscribe(testObserver)
        testObserver.assertValue("")
        testObserver.assertValueCount(1)

        trigger.onNext(Unit)
        testObserver.assertValues("", "ASDF ASDF")
        testObserver.assertValueCount(2)
    }
}