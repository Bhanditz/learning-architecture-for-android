package beepbeep.learning_mvvm.mvvm_databinding

import beepbeep.learning_mvvm.mvpvm.MvvmRxContract
import io.reactivex.subjects.BehaviorSubject

class MvvmRxViewModel : MvvmRxContract.Input {
    var name: String = ""
    var animalName: String = ""

    val outputPublisher = BehaviorSubject.createDefault<String>("")

    override fun onNameInput(s: CharSequence?) {
        name = s.toString()
        updateDisplayString()
    }

    override fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
        updateDisplayString()
    }

    fun updateDisplayString() {
        if (!name.equals("") && !animalName.equals("")) {
            outputPublisher.onNext(name + " likes " + animalName)
        }
    }
}