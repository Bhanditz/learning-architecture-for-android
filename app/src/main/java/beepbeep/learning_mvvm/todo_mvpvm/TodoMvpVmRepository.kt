package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable

interface TodoMvpVmRepositoryType {
    fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>>
}

class TodoMvpVmRepository : TodoMvpVmRepositoryType {
    override fun loadInitialTodo()
            = Observable.just(listOf(TodoMvpVmListViewModel(false, "Review PRs"), TodoMvpVmListViewModel(true, "Implement Feature")))
//            = Observable.empty<List<TodoMvpVmListViewModel>>()
}