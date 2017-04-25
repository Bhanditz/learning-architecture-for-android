package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable

class TodoMvpVmPresenter(view: TodoMvpVmContract.Input, repository: TodoMvpVmRepositoryType) : TodoMvpVmContract.Output {
    lateinit override var viewModels: Observable<TodoMvpVmViewModel>

    override val items: Observable<List<TodoMvpVmListViewModel>> by lazy { viewModels.map { it.items } }

    init {
        val initialCommands = repository.loadInitialTodo().map { SetTodo(it) }
        val addCommands = view.addTodos.map { AddTodo(it) }

        viewModels = Observable.mergeArray(initialCommands, addCommands)
                .scan(TodoMvpVmViewModel()) { vm, command ->
                    vm.executeCommand(command)
                }
    }
}