package beepbeep.learning_mvvm.todo_mvpvm

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Toast
import beepbeep.learning_mvvm.R
import com.jakewharton.rxbinding2.view.RxMenuItem
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_todo.tasksEmptyLayout
import kotlinx.android.synthetic.main.activity_todo.tasksFilteringText
import kotlinx.android.synthetic.main.activity_todo.tasksList
import kotlinx.android.synthetic.main.dialog_add_todo.view.dialogAddTodo

class TodoMvpVmActivity : AppCompatActivity(), TodoMvpVmContract.Input {
    private val presenter: TodoMvpVmContract.Output by lazy { TodoMvpVmPresenter(this) }

    override val addTodos: Observable<String> by lazy { addTodoSubject }
    override val toggleTodoAtIndexes: Observable<Int> by lazy { toggleTodoAtIndexSubject }
    override val deleteTodoAtIndexes: Observable<Int> by lazy { deleteTodoAtIndexSubject }
    override val filterTodos: Observable<TodoMvpVmFilterType> by lazy { filterTodoSubject }

    private val addTodoSubject = PublishSubject.create<String>()
    private val toggleTodoAtIndexSubject = PublishSubject.create<Int>()
    private val deleteTodoAtIndexSubject = PublishSubject.create<Int>()
    private val filterTodoSubject = PublishSubject.create<TodoMvpVmFilterType>()

    private val todoAdapter: TodoMvpVmAdapter by lazy {
        TodoMvpVmAdapter(presenter.items, itemClickHandler = { _, position ->
            toggleTodoAtIndexSubject.onNext(position)
        }, todoCheckToggleHandler = { _, position ->
            toggleTodoAtIndexSubject.onNext(position)
        }, itemLongClickHandler = { view, position ->
            val popup = PopupMenu(this, view).apply {
                menuInflater.inflate(R.menu.todo_option, menu)
                setOnMenuItemClickListener { item ->
                    if (item.itemId == R.id.option_delete) {
                        deleteTodoAtIndexSubject.onNext(position)
                    } else {
                        Toast.makeText(this@TodoMvpVmActivity, "Unsupported operation", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
            }
            popup.show()
        })
    }

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        setupViews()
    }

    private fun setupViews() {
        tasksList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@TodoMvpVmActivity)
            adapter = todoAdapter
        }

        compositeDisposable.add(presenter.showEmptyViews.map { !it }.subscribe(RxView.visibility(tasksList)))
        compositeDisposable.add(presenter.showEmptyViews.subscribe(RxView.visibility(tasksEmptyLayout, View.GONE)))
        compositeDisposable.add(presenter.filterTexts.subscribe(RxTextView.text(tasksFilteringText)))

        compositeDisposable.add(RxView.clicks(tasksFilteringText).subscribe {
            val popup = PopupMenu(this, tasksFilteringText).apply {
                menuInflater.inflate(R.menu.todo_filter, menu)
                setOnMenuItemClickListener { item ->
                    val filterType = when (item.itemId) {
                        R.id.filter_all -> TodoMvpVmFilterType.All
                        R.id.filter_active -> TodoMvpVmFilterType.Active
                        R.id.filter_completed -> TodoMvpVmFilterType.Completed
                        else -> error("Error, you should not get here")
                    }
                    filterTodoSubject.onNext(filterType)
                    true
                }
            }
            popup.show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.todo_main, menu)
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
        todoAdapter.dispose()
    }
}