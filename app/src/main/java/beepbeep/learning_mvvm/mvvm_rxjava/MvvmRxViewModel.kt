package beepbeep.learning_mvvm.mvvm_databinding

import beepbeep.learning_mvvm.mvpvm.MvvmRxContract
import io.reactivex.subjects.BehaviorSubject

class MvvmRxViewModel : MvvmRxContract.Input {
    var name: String = ""
    var animalName: String = ""

    val outputPublisher = BehaviorSubject.createDefault<String>("")

    override fun onNameInput(s: CharSequence?) {
        name = s.toString()
    }

    override fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
    }

    override fun onSubmitButtonClick() {
        outputPublisher.onNext(name + " likes " + animalName)
    }
}