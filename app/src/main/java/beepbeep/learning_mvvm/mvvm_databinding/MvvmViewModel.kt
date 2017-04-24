package beepbeep.learning_mvvm.mvvm_databinding

import android.databinding.ObservableField
import beepbeep.learning_mvvm.mvp.MvvmDataBindingContract

class MvvmViewModel(
        val displayString: ObservableField<String>
) : MvvmDataBindingContract.Input {
    var name: String = ""
    var animalName: String = ""

    override fun onSubmitButtonClick() {
        displayString.set(name + " likes " + animalName)
    }

    override fun onNameInput(s: CharSequence?) {
        name = s.toString()
    }

    override fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
    }
}