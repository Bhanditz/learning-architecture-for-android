package beepbeep.learning_mvvm.todo_mvpvm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import beepbeep.learning_mvvm.R
import com.jakewharton.rxbinding2.view.RxMenuItem
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_todo.tasksEmptyLayout
import kotlinx.android.synthetic.main.activity_todo.tasksList

class TodoMvpVmActivity : AppCompatActivity(), TodoMvpVmContract.Input {

    override val addTodos: Observable<String> by lazy { addTodoSubject }

    private val presenter: TodoMvpVmContract.Output by lazy { TodoMvpVmPresenter(this) }

    private var addTodoSubject = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setupViews()
    }

    private fun setupViews() {
        tasksList.setHasFixedSize(true)

        tasksList.layoutManager = LinearLayoutManager(this)

        tasksList.adapter = TodoMvpVmAdapter(presenter.items) {
            Log.d("Click", it.toString())
        }

        compositeDisposable.add(presenter.showEmptyViews.map { !it }.subscribe(RxView.visibility(tasksList)))
        compositeDisposable.add(presenter.showEmptyViews.subscribe(RxView.visibility(tasksEmptyLayout, View.GONE)))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_mvpvm, menu)
        menu?.let {
            compositeDisposable.add(RxMenuItem.clicks(menu.findItem(R.id.action_add)).map { "New Todo" }.subscribe(addTodoSubject::onNext))
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}