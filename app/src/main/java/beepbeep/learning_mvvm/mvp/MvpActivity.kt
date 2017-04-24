package beepbeep.learning_mvvm.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import beepbeep.learning_mvvm.R
import beepbeep.learning_mvvm.util.TextWatcherImp
import kotlinx.android.synthetic.main.activity_mvp.*

class MvpActivity : AppCompatActivity(), MvpContract.View {
    lateinit var mvpPresenter: MvpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_shared)
        supportActionBar?.title = getString(R.string.menu_mvp)
        mvpPresenter = MvpPresenter(this)

        nameEditText.addTextChangedListener(TextWatcherImp { s, _, _, _ ->
            mvpPresenter.onNameInput(s)
        })

        favoriteAnimalEditText.addTextChangedListener(TextWatcherImp { s, _, _, _ ->
            mvpPresenter.onAnimalNameInput(s)
        })
    }

    override fun onDisplayStringUpdate(displayString: String) {
        displayTextView.text = displayString
    }
}
