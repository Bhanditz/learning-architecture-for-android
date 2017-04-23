package beepbeep.learning_mvvm.mvp

class MvpContract {
    interface View {
        fun onDisplayStringUpdate(displayString: String)
    }
}