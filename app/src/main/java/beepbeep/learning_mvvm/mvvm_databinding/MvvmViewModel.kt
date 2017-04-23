package beepbeep.learning_mvvm.mvvm_databinding

import android.databinding.ObservableField

class MvvmViewModel(
        val displayString: ObservableField<String>
) {
    var inputName: String = ""
    var inputAnimalName: String = ""

    fun onSubmitButtonClick() {
        displayString.set(inputName + " likes " + inputAnimalName)
    }
//    companion object {
//        @JvmStatic
//        @BindingAdapter("app:binding")
//        fun bindEditText(editText: EditText, model: MvvmViewModel) {
//            val watcher = object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//                }
//
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                    Log.d("ddw", "LALALLA: " + s.toString())
////                    model.displayString
//                }
//
//                override fun afterTextChanged(s: Editable) {
//
//                }
//            }
//            editText.addTextChangedListener(watcher)
//        }
//    }
}