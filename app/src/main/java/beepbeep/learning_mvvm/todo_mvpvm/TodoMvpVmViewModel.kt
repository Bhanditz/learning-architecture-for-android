package beepbeep.learning_mvvm.todo_mvpvm

enum class TodoMvpVmFilterType {
    All,
    Active,
    Completed,
}

sealed class TodoMvpVmViewModelCommand

class SetTodo(val items: List<TodoMvpVmListViewModel>) : TodoMvpVmViewModelCommand()
class AddTodo(val title: String) : TodoMvpVmViewModelCommand()
class ToggleTodo(val index: Int) : TodoMvpVmViewModelCommand()
class DeleteTodo(val index: Int) : TodoMvpVmViewModelCommand()
class FilterTodo(val type: TodoMvpVmFilterType) : TodoMvpVmViewModelCommand()

data class TodoMvpVmViewModel(val filter: TodoMvpVmFilterType = TodoMvpVmFilterType.All,
                              val items: List<TodoMvpVmListViewModel> = listOf(),
                              val visibleItems: List<TodoMvpVmListViewModel> = listOf()) {
    fun executeCommand(command: TodoMvpVmViewModelCommand): TodoMvpVmViewModel {
        when (command) {
            is SetTodo -> {
                return TodoMvpVmViewModel(filter, command.items, command.items.filter { it.isVisibleWithFilter(filter) })
            }

            is AddTodo -> {
                val newTitle = command.title
                val _items = items.toMutableList().apply {
                    add(TodoMvpVmListViewModel(false, newTitle))
                }
                return TodoMvpVmViewModel(filter, _items, _items.filter { it.isVisibleWithFilter(filter) })
            }

            is ToggleTodo -> {
                val _items = items.toMutableList().apply {
                    val selectedItem = this[command.index]
                    this[command.index] = TodoMvpVmListViewModel(!selectedItem.completed, selectedItem.title)
                }
                return TodoMvpVmViewModel(filter, _items, _items.filter { it.isVisibleWithFilter(filter) })
            }

            is DeleteTodo -> {
                val _items = items.toMutableList().apply {
                    removeAt(command.index)
                }
                return TodoMvpVmViewModel(filter, _items, _items.filter { it.isVisibleWithFilter(filter) })
            }

            is FilterTodo -> {
                val filteredItems = items.filter { it.isVisibleWithFilter(command.type) }
                return TodoMvpVmViewModel(command.type, items, filteredItems)
            }
        }
    }
}
