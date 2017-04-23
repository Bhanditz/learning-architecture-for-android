package beepbeep.learning_mvvm.mvvm_databinding

import android.databinding.ObservableField

class MvvmViewModel(
        val displayString: ObservableField<String>
) {
    var name: String = ""
    var animalName: String = ""

    fun onSubmitButtonClick() {
        displayString.set(name + " likes " + animalName)
    }

    fun onNameInput(s: CharSequence?) {
        name = s.toString()
    }

    fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
    }
}