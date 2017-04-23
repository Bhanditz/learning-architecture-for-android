package beepbeep.learning_mvvm.mvp

class MvpPresenter(val view: MvpContract.View) {
    var name: String = ""
    var animalName: String = ""

    fun onNameInput(s: CharSequence?) {
        name = s.toString()
    }

    fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
    }

    fun onSubmitButtonClick() {
        view.onDisplayStringUpdate(name + " likes " + animalName)
    }
}