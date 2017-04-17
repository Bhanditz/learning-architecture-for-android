package beepbeep.learning_mvvm.animal

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Test


class AnimalInteractorTest {

    lateinit var interactor: AnimalInteractor

    @Test
    fun doStuff() {
        val repo: RepoInterface = object : RepoInterface {
            override fun getAnimals(): Observable<String> {
                return Observable.just<String>("ASDF ASDF")
            }
        }
        interactor = AnimalInteractor(repo)

        val testObserver = TestObserver<String>()

        interactor.viewModels.map { it.animalName }.subscribe(testObserver)
        testObserver.assertValue("")
        testObserver.assertValueCount(1)

        interactor.doStuff()
        testObserver.assertValues("", "ASDF ASDF")
        testObserver.assertValueCount(2)
    }
}