package beepbeep.learning_mvvm.todo_mvpvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import beepbeep.learning_mvvm.R
import io.reactivex.Observable

class TodoMvpVmActivity : AppCompatActivity(), TodoMvpVmContract.Input {

    override val addTodos: Observable<String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_mvpvm, menu)
        return super.onCreateOptionsMenu(menu)
    }
}