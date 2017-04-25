package beepbeep.learning_mvvm.todo_mvpvm

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class TodoMvpVmTest {
    @Test
    fun `when user initially see the screen, user see preloaded todos`() {
        val test = TestObserver<List<TodoMvpVmListViewModel>>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(TodoMvpVmListViewModel(true, "test1")))
            }
        }

        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = Observable.empty()
            override val toggleTodoAtIndexes: Observable<Int> = Observable.empty()
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.items.subscribeWith(test)

        val nexts = test.values()
        val items = nexts.last()
        assertThat(items, hasItem(TodoMvpVmListViewModel(true, "test1")))
    }

    @Test
    fun `when user add new todo, user must see new task at the end of the list`() {
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
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.items.subscribeWith(test)

        addTodoSubject.onNext("New TITLE")

        val items = test.values()
        val oldSize = items[0].size
        val newSize = items[1].size
        assertThat(oldSize, equalTo(0))
        assertThat(newSize, equalTo(1))
    }

    @Test
    fun `when there is no todos, user must see emptyView instead of list`() {
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
    fun `when toggle a todo, user must see it as an opposite state`() {
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
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.items.subscribeWith(test)

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
}