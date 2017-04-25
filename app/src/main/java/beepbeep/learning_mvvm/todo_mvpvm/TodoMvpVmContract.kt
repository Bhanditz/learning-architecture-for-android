package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable

interface TodoMvpVmContract {
    interface Input {
        val addTodos: Observable<String>
    }

    interface Output {
        val viewModels: Observable<TodoMvpVmViewModel>

        val items: Observable<List<TodoMvpVmListViewModel>>

        val showEmptyViews: Observable<Boolean>
    }
}