package beepbeep.learning_mvvm.mvvm_rxjava

import beepbeep.learning_mvvm.mvvm_databinding.MvvmRxViewModel
import io.reactivex.observers.TestObserver
import org.junit.Test

class MvvmRxViewModelTest {
    @Test
    fun updateDisplayString() {
        val viewModel = MvvmRxViewModel()
        val testObserver = TestObserver<String>()

        viewModel.outputPublisher.subscribe(testObserver)

        testObserver.assertValueAt(0) {
            it.equals("")
        }

        viewModel.onNameInput("JR")
        viewModel.onAnimalNameInput("kangaroo")

        testObserver.assertValueAt(1) {
            it.equals("JR likes kangaroo")
        }
    }

}