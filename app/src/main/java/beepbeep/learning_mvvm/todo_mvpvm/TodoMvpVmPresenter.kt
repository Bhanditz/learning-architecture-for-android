package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable

class TodoMvpVmPresenter(view: TodoMvpVmContract.Input, repository: TodoMvpVmRepositoryType = TodoMvpVmRepository()) : TodoMvpVmContract.Output {
    lateinit override var viewModels: Observable<TodoMvpVmViewModel>

    override val items: Observable<List<TodoMvpVmListViewModel>> by lazy { viewModels.map { it.items } }

    override val showEmptyViews: Observable<Boolean> by lazy { viewModels.map { it.items.isEmpty() } }

    init {
        val initialCommands = repository.loadInitialTodo().map { SetTodo(it) }
        val addCommands = view.addTodos.map { AddTodo(it) }
        val toggleAtIndexCommands = view.toggleTodoAtIndexes.map { ToggleTodo(it) }
        val deleteCommands = view.deleteTodoAtIndexes.map { DeleteTodo(it) }

        viewModels = Observable.mergeArray(initialCommands, addCommands, toggleAtIndexCommands, deleteCommands)
                .scan(TodoMvpVmViewModel()) { vm, command ->
                    vm.executeCommand(command)
                }
    }
}