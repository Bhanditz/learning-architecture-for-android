package beepbeep.learning_mvvm.todo_mvpvm

data class TodoMvpVmListViewModel(val completed: Boolean, val title: String) {
    fun isVisibleWithFilter(filterType: TodoMvpVmFilterType) = when (filterType) {
        TodoMvpVmFilterType.All -> true
        TodoMvpVmFilterType.Active -> !completed
        TodoMvpVmFilterType.Completed -> completed
    }
}