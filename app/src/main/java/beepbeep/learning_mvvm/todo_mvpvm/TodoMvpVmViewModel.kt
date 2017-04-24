package beepbeep.learning_mvvm.todo_mvpvm

enum class TodoMvpVmFilterType {
    ALL_TASKS,
    ACTIVE_TASKS,
    COMPLETED_TASKS
}

data class TodoMvpVmViewModel(val filter: TodoMvpVmFilterType, val items: List<TodoMvpVmListViewModel>)