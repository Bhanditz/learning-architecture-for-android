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
        val test = TestObserver<TodoMvpVmViewModel>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.just(listOf(TodoMvpVmListViewModel(true, "test1")))
            }
        }

        val addTodoSubject = PublishSubject.create<String>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = addTodoSubject
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.subscribeWith(test)

        val items = test.values()
        val vm = items.last()
        assertThat(vm.items, hasItem(TodoMvpVmListViewModel(true, "test1")))
    }

    @Test
    fun `when user add new todo, user must see new task at the end of the list`() {
        val test = TestObserver<TodoMvpVmViewModel>()

        val repo = object : TodoMvpVmRepositoryType {
            override fun loadInitialTodo(): Observable<List<TodoMvpVmListViewModel>> {
                return Observable.empty()
            }
        }

        val addTodoSubject = PublishSubject.create<String>()
        val view = object : TodoMvpVmContract.Input {
            override val addTodos: Observable<String> = addTodoSubject
        }

        val presenter = TodoMvpVmPresenter(view, repo)

        presenter.viewModels.subscribeWith(test)

        addTodoSubject.onNext("New TITLE")

        val items = test.values()
        val oldSize = items[0].items.size
        val newSize = items[1].items.size
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

}