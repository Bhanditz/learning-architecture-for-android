package beepbeep.learning_mvvm.mvp

class MvvmDataBindingContract {
    interface Input {
        fun onNameInput(s: CharSequence?)
        fun onAnimalNameInput(s: CharSequence?)
    }
}