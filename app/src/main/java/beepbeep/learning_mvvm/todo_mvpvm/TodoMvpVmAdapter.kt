package beepbeep.learning_mvvm.todo_mvpvm

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.learning_mvvm.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.list_item_todo.view.listTasksComplete
import kotlinx.android.synthetic.main.list_item_todo.view.listTasksTitle

typealias TodoActionOnIndexHandler = (View, Int) -> Unit

class TodoMvpVmViewHolder(view: View,
                          val itemClickHandler: TodoActionOnIndexHandler,
                          val todoCheckToggleHandler: TodoActionOnIndexHandler,
                          val itemLongClickHandler: TodoActionOnIndexHandler) : RecyclerView.ViewHolder(view) {
    init {
        view.setOnClickListener {
            itemClickHandler.invoke(it, adapterPosition)
        }
        view.setOnLongClickListener {
            itemLongClickHandler.invoke(it, adapterPosition)
            true
        }
    }
}

class TodoMvpVmAdapter(items: Observable<List<TodoMvpVmListViewModel>>,
                       val itemClickHandler: TodoActionOnIndexHandler,
                       val todoCheckToggleHandler: TodoActionOnIndexHandler,
                       val itemLongClickHandler: TodoActionOnIndexHandler) : RecyclerView.Adapter<TodoMvpVmViewHolder>() {

    private var itemsSubject = BehaviorSubject.create<List<TodoMvpVmListViewModel>>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(items.subscribe {
            itemsSubject.onNext(it)
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoMvpVmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, parent, false)
        return TodoMvpVmViewHolder(view, itemClickHandler, todoCheckToggleHandler, itemLongClickHandler)
    }

    override fun onBindViewHolder(holder: TodoMvpVmViewHolder, position: Int) {
        with(itemsSubject.value[position]) {
            holder.itemView.listTasksComplete.isChecked = completed
            holder.itemView.listTasksTitle.text = title
            val paintFlags = holder.itemView.listTasksTitle.paintFlags
            holder.itemView.listTasksTitle.paintFlags = if (completed) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun getItemCount() = itemsSubject.value.size

    fun dispose() {
        compositeDisposable.dispose()
    }
}