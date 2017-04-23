package beepbeep.learning_mvvm.mvpvm

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import beepbeep.learning_mvvm.databinding.ActivityMvvmStandardBinding
import beepbeep.learning_mvvm.mvvm_databinding.MvvmViewModel
import beepbeep.learning_mvvm.util.TextWatcherImp

class MvvmDataBindingActivity : AppCompatActivity() {

    lateinit var binding: ActivityMvvmStandardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.menu_mvvm_data_binding)

        binding = DataBindingUtil.setContentView<ActivityMvvmStandardBinding>(this, R.layout.activity_mvvm_standard)
        binding.model = MvvmViewModel(ObservableField(""))

        binding.nameEditText.addTextChangedListener(TextWatcherImp { s, _, _, _ ->
            binding.model.inputName = s.toString()
        })

        binding.favoriteAnimalEditText.addTextChangedListener(TextWatcherImp { s, _, _, _ ->
            binding.model.inputAnimalName = s.toString()
        })

        binding.submitButton.setOnClickListener { v ->
            binding.model.onSubmitButtonClick()
        }
    }

}