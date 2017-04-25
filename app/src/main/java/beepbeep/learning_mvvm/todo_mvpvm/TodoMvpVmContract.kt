package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable

interface TodoMvpVmContract {
    interface Input {
        val addTodos: Observable<String>
    }

    interface Output {
        //viewModels
        val viewModels: Observable<TodoMvpVmViewModel>

        //items
        val items: Observable<List<TodoMvpVmListViewModel>>
    }
}