package beepbeep.learning_mvvm

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import beepbeep.learning_mvvm.mpv_rx_java.MvpRxActivity
import beepbeep.learning_mvvm.mvp.MvpActivity
import beepbeep.learning_mvvm.mvpvm.MvpVmActivity
import beepbeep.learning_mvvm.mvvm_databinding.MvvmDataBindingActivity
import beepbeep.learning_mvvm.mvvm_rxjava.MvvmRxActivity
import beepbeep.learning_mvvm.no_arch.NoArchActivity
import beepbeep.learning_mvvm.todo_mvpvm.TodoMvpVmActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupNavigationView()
    }

    fun setupNavigationView() {
        drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_share) {
            startActivity(Intent(this, NoArchActivity::class.java))
        } else if (id == R.id.nav_mvp) {
            startActivity(Intent(this, MvpActivity::class.java))
        } else if (id == R.id.nav_mvvm_data_binding) {
            startActivity(Intent(this, MvvmDataBindingActivity::class.java))
        } else if (id == R.id.nav_mvvm_rxjava) {
            startActivity(Intent(this, MvvmRxActivity::class.java))
        } else if (id == R.id.nav_mvpvm_rxjava) {
            startActivity(Intent(this, MvpVmActivity::class.java))
        } else if (id == R.id.nav_mvp_rxjava) {
            startActivity(Intent(this, MvpRxActivity::class.java))
        } else if (id == R.id.nav_todo_mvpvm) {
            startActivity(Intent(this, TodoMvpVmActivity::class.java))
        }
//        else if (id == R.id.nav_send) {
//
//        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
