package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TodoMvpVmTest {
    @Test
    fun `when initially see the screen, user sees preloaded todos`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(TodoMvpVmListViewModel(true, "test1")))
            }
        }

        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = Observable.empty()
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
            override val deleteTodoAtIndexes: Observable<Int> = Observable.empty()
            override val filterTodos: Observable<TodoMvpVmFilterType> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.map { it.visibleItems }.subscribeWith(test)

        val items = test.values()
        val last = items.last()
        assertThat(last, hasItem(TodoMvpVmListViewModel(true, "test1")))
    }

    @Test
    fun `when add new todo, user sees new task added at the end of the list`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.empty()
            }
        }

        val addTodoSubject = PublishSubject.create<String>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = addTodoSubject
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
            override val deleteTodoAtIndexes: Observable<Int> = Observable.empty()
            override val filterTodos: Observable<TodoMvpVmFilterType> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.map { it.visibleItems }.subscribeWith(test)

        addTodoSubject.onNext("New TITLE")

        val items = test.values()
        val oldSize = items[0].size
        val newSize = items[1].size
        assertThat(oldSize, equalTo(0))
        assertThat(newSize, equalTo(1))
    }

    @Test
    fun `when there is no todos, user sees emptyView instead of list`() {
        val test = TestObserver<Boolean>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.empty()
            }
        }

        val addTodoSubject = PublishSubject.create<String>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = addTodoSubject
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
            override val deleteTodoAtIndexes: Observable<Int> = Observable.empty()
            override val filterTodos: Observable<TodoMvpVmFilterType> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.showEmptyViews.subscribeWith(test)

        addTodoSubject.onNext("New TITLE 2")

        val items = test.values()

        val oldShowEmptyView = items[0]
        val newShowEmptyView = items[1]
        assertThat(oldShowEmptyView, equalTo(true))
        assertThat(newShowEmptyView, equalTo(false))
    }

    @Test
    fun `when toggle a todo, user sees it as an opposite state`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(TodoMvpVmListViewModel(true, "111"), TodoMvpVmListViewModel(false, "222")))
            }
        }

        val toggleSubject = PublishSubject.create<Int>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = Observable.empty()
            override val toggleTodoAtIndexes: Observable<Int> = toggleSubject
            override val deleteTodoAtIndexes: Observable<Int> = Observable.empty()
            override val filterTodos: Observable<TodoMvpVmFilterType> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.map { it.visibleItems }.subscribeWith(test)

        val items = test.values()

        var toggledIndex = 0
        toggleSubject.onNext(toggledIndex)
        val oldItems1 = items[1]
        val newItems1 = items[2]
        assertThat(oldItems1[toggledIndex].completed, equalTo(true))
        assertThat(newItems1[toggledIndex].completed, equalTo(false))
        assertThat(newItems1[toggledIndex].title, equalTo("111"))

        toggledIndex = 1
        toggleSubject.onNext(toggledIndex)
        val oldItems2 = items[2]
        val newItems2 = items[3]
        assertThat(oldItems2[toggledIndex].completed, equalTo(false))
        assertThat(newItems2[toggledIndex].completed, equalTo(true))
        assertThat(newItems2[toggledIndex].title, equalTo("222"))
    }

    @Test
    fun `when delete a todo, user sees it being deleted from the list`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(TodoMvpVmListViewModel(true, "111"), TodoMvpVmListViewModel(false, "222")))
            }
        }

        val deleteSubject = PublishSubject.create<Int>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = Observable.empty()
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
            override val deleteTodoAtIndexes: Observable<Int> = deleteSubject
            override val filterTodos: Observable<TodoMvpVmFilterType> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.map { it.visibleItems }.subscribeWith(test)

        val items = test.values()

        deleteSubject.onNext(1)
        val itemAfterDeleted1 = items[2]
        assertThat(itemAfterDeleted1, not(hasItem(TodoMvpVmListViewModel(false, "222"))))
        assertThat(itemAfterDeleted1.size, equalTo(1))

        deleteSubject.onNext(0)
        val itemAfterDeleted2 = items[3]
        assertThat(itemAfterDeleted2, not(hasItem(TodoMvpVmListViewModel(true, "111"))))
        assertThat(itemAfterDeleted2.size, equalTo(0))
    }

    @Test
    fun `when filter todo, user sees only todos that match with selected filter`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(
                        TodoMvpVmListViewModel(true, "111"),
                        TodoMvpVmListViewModel(false, "222"),
                        TodoMvpVmListViewModel(false, "333"),
                        TodoMvpVmListViewModel(false, "444"),
                        TodoMvpVmListViewModel(true, "555")))
            }
        }

        val filterSubject = PublishSubject.create<TodoMvpVmFilterType>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = Observable.empty()
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
            override val deleteTodoAtIndexes: Observable<Int> = Observable.empty()
            override val filterTodos: Observable<TodoMvpVmFilterType> = filterSubject
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.map { it.visibleItems }.subscribeWith(test)

        val items = test.values()

        filterSubject.onNext(TodoMvpVmFilterType.Active)
        val itemsForActive = items[2]
        assertThat(itemsForActive.size, equalTo(3))

        filterSubject.onNext(TodoMvpVmFilterType.Completed)
        val itemsForCompleted = items[3]
        assertThat(itemsForCompleted.size, equalTo(2))

        filterSubject.onNext(TodoMvpVmFilterType.All)
        val itemsForAll = items[4]
        assertThat(itemsForAll.size, equalTo(5))
    }
}