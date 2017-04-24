package beepbeep.learning_mvvm.mvp

open class MvpPresenter(val view: MvpContract.View) : MvpContract.Presenter {
    var name: String = ""
    var animalName: String = ""

    override fun onNameInput(s: CharSequence?) {
        name = s.toString()
        displayStringUpdate()
    }

    override fun onAnimalNameInput(s: CharSequence?) {
        animalName = s.toString()
        displayStringUpdate()
    }

    fun displayStringUpdate() {
        view.onDisplayStringUpdate(name + " likes " + animalName)
    }
}