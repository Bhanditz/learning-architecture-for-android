package beepbeep.learning_mvvm.mvp

class MvpContract {
    interface Presenter {
        fun onNameInput(s: CharSequence?)
        fun onAnimalNameInput(s: CharSequence?)
    }

    interface View {
        fun onDisplayStringUpdate(displayString: String)
    }
}