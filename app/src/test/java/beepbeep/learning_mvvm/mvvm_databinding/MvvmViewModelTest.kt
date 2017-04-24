package beepbeep.learning_mvvm.mvvm_databinding

import android.databinding.ObservableField
import org.junit.Assert
import org.junit.Test

class MvvmViewModelTest {

    @Test
    fun updateDisplayString() {
        val outputObservable = ObservableField<String>()
        val viewModel = MvvmViewModel(outputObservable)
        viewModel.onNameInput("JR")
        viewModel.onAnimalNameInput("corgi")
        Assert.assertEquals(outputObservable.get(), "JR likes corgi")

        viewModel.onNameInput("kitti")
        Assert.assertEquals(outputObservable.get(), "kitti likes corgi")

        viewModel.onAnimalNameInput("pineapple")
        Assert.assertEquals(outputObservable.get(), "kitti likes pineapple")
    }
}