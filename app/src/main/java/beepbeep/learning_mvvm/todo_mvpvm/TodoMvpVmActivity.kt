package beepbeep.learning_mvvm.todo_mvpvm

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
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
import kotlinx.android.synthetic.main.dialog_add_todo.view.dialogAddTodo

class TodoMvpVmActivity : AppCompatActivity(), TodoMvpVmContract.Input {
    private val presenter: TodoMvpVmContract.Output by lazy { TodoMvpVmPresenter(this) }

    override val addTodos: Observable<String> by lazy { addTodoSubject }
    override val toggleTodoAtIndexes: Observable<Int> by lazy { toggleTodoAtIndexSubject }

    private var addTodoSubject = PublishSubject.create<String>()
    private var toggleTodoAtIndexSubject = PublishSubject.create<Int>()

    private val adapter: TodoMvpVmAdapter by lazy {
        TodoMvpVmAdapter(presenter.items, {
            toggleTodoAtIndexSubject.onNext(it)
        }, {
            toggleTodoAtIndexSubject.onNext(it)
        })
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setupViews()
    }

    private fun setupViews() {
        tasksList.setHasFixedSize(true)

        tasksList.layoutManager = LinearLayoutManager(this)

        tasksList.adapter = adapter
        compositeDisposable.add(presenter.showEmptyViews.map { !it }.subscribe(RxView.visibility(tasksList)))
        compositeDisposable.add(presenter.showEmptyViews.subscribe(RxView.visibility(tasksEmptyLayout, View.GONE)))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_mvpvm, menu)
        menu?.let {
            compositeDisposable.add(RxMenuItem.clicks(menu.findItem(R.id.action_add)).subscribe {
                val builder = AlertDialog.Builder(this).apply {
                    val view = LayoutInflater.from(this@TodoMvpVmActivity).inflate(R.layout.dialog_add_todo, null)
                    setTitle("Add New Todo")
                    setView(view)
                    setCancelable(true)
                    setPositiveButton("OK") { _, _ ->
                        val text = view.dialogAddTodo.text
                        if (text.isNotEmpty()) {
                            addTodoSubject.onNext(view.dialogAddTodo.text.toString())
                        }
                    }
                    setNegativeButton("Cancel") { _, _ -> }
                }
                val dialog = builder.create()
                dialog.show()
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        adapter.dispose()
    }
}