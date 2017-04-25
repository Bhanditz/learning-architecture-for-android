package beepbeep.learning_mvvm.mpv_rx_java

import android.support.annotation.StringRes
import io.reactivex.Observable

interface MvpRxView {
    fun nameInputs(): Observable<String>
    fun animalNameInputs(): Observable<String>
    fun onDisplayStringUpdate(displayString: String)
    fun setActionBarTitle(@StringRes title: Int)
    fun tearDown(): Observable<Unit>
}