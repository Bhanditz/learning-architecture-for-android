package beepbeep.learning_mvvm.todo_mvpvm

enum class TodoMvpVmFilterType {
    ALL_TASKS,
    ACTIVE_TASKS,
    COMPLETED_TASKS
}

sealed class TodoMvpVmViewModelCommand

class SetTodo(val items: List<TodoMvpVmListViewModel>) : TodoMvpVmViewModelCommand()
class AddTodo(val title: String) : TodoMvpVmViewModelCommand()
class ToggleTodo(val index: Int) : TodoMvpVmViewModelCommand()
class DeleteTodo(val index: Int) : TodoMvpVmViewModelCommand()

data class TodoMvpVmViewModel(val filter: TodoMvpVmFilterType = TodoMvpVmFilterType.ALL_TASKS, val items: List<TodoMvpVmListViewModel> = listOf()) {
    fun executeCommand(command: TodoMvpVmViewModelCommand): TodoMvpVmViewModel {
        when (command) {
            is SetTodo -> {
                return TodoMvpVmViewModel(filter, command.items)
            }

            is AddTodo -> {
                val newTitle = command.title
                val _items = items.toMutableList().apply {
                    add(TodoMvpVmListViewModel(false, newTitle))
                }
                return TodoMvpVmViewModel(filter, _items)
            }

            is ToggleTodo -> {
                val _items = items.toMutableList().apply {
                    val selectedItem = this[command.index]
                    this[command.index] = TodoMvpVmListViewModel(!selectedItem.completed, selectedItem.title)
                }
                return TodoMvpVmViewModel(filter, _items)
            }

            is DeleteTodo -> {
                val _items = items.toMutableList().apply {
                    removeAt(command.index)
                }
                return TodoMvpVmViewModel(filter, _items)
            }
        }
    }
}