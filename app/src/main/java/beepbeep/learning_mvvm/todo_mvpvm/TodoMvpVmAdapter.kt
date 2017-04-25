package beepbeep.learning_mvvm.todo_mvpvm

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.learning_mvvm.R

class TodoMvpVmViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

class TodoMvpVmAdapter(val items: List<TodoMvpVmListViewModel>) : RecyclerView.Adapter<TodoMvpVmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoMvpVmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_todo, parent, false)
        return TodoMvpVmViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoMvpVmViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount() = items.size
}