package beepbeep.learning_mvvm.mpv_rx_java

import com.nhaarman.mockito_kotlin.*
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test

class MvpRxPresenterTest {
    private val onDestroySignal = BehaviorSubject.create<Unit>()
    private val onNameSignal = BehaviorSubject.create<String>()
    private val onNameAnimalSignal = BehaviorSubject.create<String>()
    private val view: MvpRxView = mock()
    private val presenterUT = MvpRxPresenter()

    @Before fun before() {
        whenever(view.tearDown()).thenReturn(onDestroySignal)
        whenever(view.nameInputs()).thenReturn(onNameSignal)
        whenever(view.animalNameInputs()).thenReturn(onNameAnimalSignal)

        presenterUT.attachView(view)
    }

    @Test fun verifyAttachView() {
        verify(view, never()).onDisplayStringUpdate(any())
        verify(view, times(1))
                .setActionBarTitle(beepbeep.learning_mvvm.R.string.menu_mvp_rx_java)
    }

    @Test fun verifyInputEmissions() {
        verify(view, never()).onDisplayStringUpdate(any())

        onNameSignal.onNext("John")

        verify(view, never()).onDisplayStringUpdate(any())

        onNameAnimalSignal.onNext("Lint")

        verify(view, times(1)).onDisplayStringUpdate("John likes Lint")

        onNameSignal.onNext("Juan")

        verify(view, times(1)).onDisplayStringUpdate("Juan likes Lint")

        onNameAnimalSignal.onNext("Dog")

        verify(view, times(1)).onDisplayStringUpdate("Juan likes Dog")

        onNameAnimalSignal.onNext("")

        verify(view, times(1)).onDisplayStringUpdate("Juan likes ")

        onNameSignal.onNext("")

        verify(view, times(1)).onDisplayStringUpdate(" likes ")

        verify(view, times(5)).onDisplayStringUpdate(any())
    }

    @Test fun verifyTearDown() {
        verify(view, never()).onDisplayStringUpdate(any())
        onDestroySignal.onNext(Unit)

        onNameSignal.onNext("John")
        onNameAnimalSignal.onNext("Lint")
        onNameSignal.onNext("Juan")
        onNameAnimalSignal.onNext("Dog")

        verify(view, never()).onDisplayStringUpdate(any())
    }
}
