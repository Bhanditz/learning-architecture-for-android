package beepbeep.learning_mvvm.login

import android.databinding.DataBindingUtil
import android.databinding.ObservableField
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import beepbeep.learning_mvvm.R
import beepbeep.learning_mvvm.databinding.ActivityMvvmStandardBinding
import beepbeep.learning_mvvm.mvvm_databinding.ViewModel


class MvvmDataBindingActivity : AppCompatActivity() {

    lateinit var binding: ActivityMvvmStandardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMvvmStandardBinding>(this, R.layout.activity_mvvm_standard)
        binding.model = ViewModel(ObservableField("asdf"))

        binding.nameEditText.addTextChangedListener(TextWatcherImp { s, start, before, count ->
            binding.model.inputName = s.toString()
        })

        binding.favoriteAnimalEditText.addTextChangedListener(TextWatcherImp { s, start, before, count ->
            binding.model.inputAnimalName = s.toString()
        })

        binding.submitButton.setOnClickListener { v ->
            binding.model.onSubmitButtonClick()
        }
    }

}

class TextWatcherImp(val textChange: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChange.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}
