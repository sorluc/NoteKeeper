package com.example.notekeeper

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar

class NoteActivity : AppCompatActivity() {
    private val tag = this::class.simpleName
    private var notePosition = POSITION_NOT_SET

    val noteGetTogetherHelper = NoteGetTogetherHelper(this,lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        findViewById<android.widget.Spinner>(R.id.spinnerCourses).adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET){
            displayNote()
        } else {
            createNewNode()
        }
        Log.d(tag,"onCreate")
    }

    private fun createNewNode() {
        notePosition = DataManager.addNote(NoteInfo())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        if (notePosition > DataManager.notes.lastIndex){
            val message = "Note not found"
            showMessage(message)
            Log.e(tag,"Invalid note position $notePosition, max valid position ${DataManager.notes.lastIndex}")
            return
        }

        Log.i(tag,"Displaying note for position $notePosition")
        val note = DataManager.notes[notePosition]
        findViewById<android.widget.EditText>(R.id.textNoteTitle).setText(note.title)
        findViewById<android.widget.EditText>(R.id.textNoteText).setText(note.text)

        val coursePosition = DataManager.courses.values.indexOf(note.course)
        findViewById<android.widget.Spinner>(R.id.spinnerCourses).setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                if (notePosition < DataManager.notes.lastIndex){
                    moveNext()
                } else {
                    val message = "No more notes"
                    showMessage(message)
                }
                true
            }
            R.id.action_get_togeter ->{
                noteGetTogetherHelper.sendMessage(DataManager.loadNote(notePosition))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun NoteActivity.showMessage(message: String) {
        Snackbar.make(findViewById(R.id.textNoteTitle), message, Snackbar.LENGTH_LONG).show()
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex ){
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null){
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
        Log.d(tag,"onPause")
    }

    private fun saveNote() {
        val note = DataManager.notes[notePosition]
        note.title = findViewById<android.widget.EditText>(R.id.textNoteTitle).text.toString()
        note.text = findViewById<android.widget.EditText>(R.id.textNoteText).text.toString()
        note.course = findViewById<android.widget.Spinner>(R.id.spinnerCourses).selectedItem as CourseInfo
    }
}