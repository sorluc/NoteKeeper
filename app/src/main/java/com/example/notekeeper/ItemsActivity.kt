package com.example.notekeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.notekeeper.databinding.ActivityItemsBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

class ItemsActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    NoteRecyclerAdapter.OnNoteSelectedListener {

    private val tag = this::class.simpleName

    private lateinit var binding: ActivityItemsBinding

    private val noteLayoutManager by lazy{
        LinearLayoutManager(this)
    }

    private val noteRecyclerAdapter by lazy{
        val adapter = NoteRecyclerAdapter(this, DataManager.notes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val courseLayoutManager by lazy{
        GridLayoutManager(this,resources.getInteger(R.integer.course_grid_span))
    }

    private val courseRecyclerAdapter by lazy{
        CourseRecyclerAdapter(this,DataManager.courses.values.toList())
    }

    private val recentlyViewedNoteRecyclerAdapter by lazy {
        val adapter = NoteRecyclerAdapter(this, viewModel.recentlyViewedNotes)
        adapter.setOnSelectedListener(this)
        adapter
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this)[ItemsActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_items)

        setSupportActionBar(binding.appBarItems.toolbar)

        binding.appBarItems.fab.setOnClickListener { view ->
            val activityIntent = Intent(this, NoteActivity::class.java)
            startActivity(activityIntent)
        }

        if (viewModel.isNewlyCreated && savedInstanceState !=null)
            viewModel.restoreState(savedInstanceState)
        viewModel.isNewlyCreated = false

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, binding.appBarItems.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(tag,"onSaveInstanceState: " + outState)
        if (outState!= null)
            viewModel.saveState(outState)
    }

    private fun displayNotes() {
        findViewById<RecyclerView>(R.id.listItems).layoutManager = noteLayoutManager
        findViewById<RecyclerView>(R.id.listItems).adapter = noteRecyclerAdapter

        Log.d(tag,"displayNotes")

        binding.navView.menu.findItem(R.id.nav_notes).isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun displayCourses() {
        findViewById<RecyclerView>(R.id.listItems).layoutManager = courseLayoutManager
        findViewById<RecyclerView>(R.id.listItems).adapter = courseRecyclerAdapter

        Log.d(tag,"displayCourses")

        binding.navView.menu.findItem(R.id.nav_courses).isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun displayRecentlyViewedNotes() {
        findViewById<RecyclerView>(R.id.listItems).layoutManager = noteLayoutManager
        findViewById<RecyclerView>(R.id.listItems).adapter = recentlyViewedNoteRecyclerAdapter

        Log.d(tag,"displayRecentlyViewedNotes")

        binding.navView.menu.findItem(R.id.nav_recently).isChecked = true
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem):Boolean {
        when (item.itemId){
            R.id.nav_notes,
            R.id.nav_courses,
            R.id.nav_recently,
            R.id.nav_how_many-> {
                handleDisplaySelection(item.itemId)
                viewModel.navDrawerDisplaySelection = item.itemId
            }
        }
        return true
    }

    private fun handleDisplaySelection(itemId: Int){
        when(itemId){
            R.id.nav_notes ->{
                handleSelection(R.string.nav_notes_message)
                displayNotes()
            }
            R.id.nav_courses -> {
                handleSelection(R.string.nav_courses_message)
                displayCourses()
            }

            R.id.nav_recently -> {
                handleSelection(R.string.nav_recently_message)
                displayRecentlyViewedNotes()
            }

            R.id.nav_how_many ->{
                val message = getString(R.string.nav_how_many_message_format,
                    DataManager.notes.size, DataManager.courses.size
                )
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                Snackbar.make(findViewById<RecyclerView>(R.id.listItems),message,Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onNoteSelected (note: NoteInfo){
        Log.d(tag,"onNoteSelected")
        viewModel.addToRecentlyViewedNotes(note)
    }

    private fun handleSelection(stringId: Int) {
        Snackbar.make(findViewById<RecyclerView>(R.id.listItems), stringId, Snackbar.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        findViewById<RecyclerView>(R.id.listItems).adapter?.notifyDataSetChanged()
    }

}