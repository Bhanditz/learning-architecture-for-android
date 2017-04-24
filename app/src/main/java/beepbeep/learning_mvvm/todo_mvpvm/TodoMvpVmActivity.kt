package beepbeep.learning_mvvm.todo_mvpvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import beepbeep.learning_mvvm.R

class TodoMvpVmActivity : AppCompatActivity(), TodoMvpVmContract.Input {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_mvpvm)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_mvpvm, menu)
        return super.onCreateOptionsMenu(menu)
    }
}