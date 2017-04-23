package beepbeep.learning_mvvm.animal


class AnimalPresenterTest {

//    lateinit var interactor: AnimalPresenter

//    @Test
//    fun doStuff() {
//        val repo: AnimalContract.Repo = object : AnimalContract.Repo {
//            override fun getAnimals(): Observable<String> {
//                return Observable.just<String>("ASDF ASDF")
//            }
//        }
//        val trigger = PublishSubject.create<Unit>()
//        val input = object : AnimalContract.Input {
//            override fun onCreate(): Observable<Unit> {
//                return trigger
//            }
//        }
//
//        interactor = AnimalPresenter(input, repo)
//
//        val testObserver = TestObserver<String>()
//
//        interactor.viewModels.map { it.animalName }.subscribe(testObserver)
//        testObserver.assertValue("")
//        testObserver.assertValueCount(1)
//
//        trigger.onNext(Unit)
//        testObserver.assertValues("", "ASDF ASDF")
//        testObserver.assertValueCount(2)
//    }
}