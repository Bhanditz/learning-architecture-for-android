package beepbeep.learning_mvvm.mvp

class MvpPresenter(val view: MvpContract.View) : MvpContract.Presenter {
    var name: String = ""
    var animalName: String = ""

    override fun onNameInput(s: CharSequence?) {
        name = s.toString()
    }

    override fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
    }

    override fun onSubmitButtonClick() {
        view.onDisplayStringUpdate(name + " likes " + animalName)
    }

}