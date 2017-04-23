package beepbeep.learning_mvvm.util

import android.text.Editable
import android.text.TextWatcher

class TextWatcherImp(val textChange: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textChange.invoke(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}
