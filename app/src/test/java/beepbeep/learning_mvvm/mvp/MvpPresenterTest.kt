package beepbeep.learning_mvvm.mvp

import org.junit.Assert.assertEquals
import org.junit.Test

class MvpPresenterTest {
    @Test
    fun displayCorrectly() {
        var outputString = ""
        val inputView: MvpContract.View = object : MvpContract.View {
            override fun onDisplayStringUpdate(displayString: String) {
                outputString = displayString
            }
        }

        val presenter = MvpPresenter(inputView)

        presenter.onNameInput("cat")
        presenter.onAnimalNameInput("dog")
        assertEquals(outputString, "cat likes dog")

        presenter.onNameInput("monkey")
        assertEquals(outputString, "monkey likes dog")

        presenter.onAnimalNameInput("snake")
        assertEquals(outputString, "monkey likes snake")
    }
}