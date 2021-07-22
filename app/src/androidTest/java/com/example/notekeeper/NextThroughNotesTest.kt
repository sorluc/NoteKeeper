package com.example.notekeeper

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import androidx.test.runner.AndroidJUnit4
import androidx.test.espresso.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.assertion.ViewAssertions.*


import org.junit.Rule
import org.hamcrest.Matchers.*


@RunWith(AndroidJUnit4::class)
class NextThroughNotesTest {
    /*
    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun nextThroughNotes(){

        onData(allOf(instanceOf(NoteInfo::class.java), equalTo(DataManager.notes[0]))).perform(click())

        for (index in 0..DataManager.notes.lastIndex){
            val note = DataManager.notes[index]

            onView(withId(R.id.spinnerCourses)).check(matches(withSpinnerText(note.course?.title)))

            onView(withId(R.id.textNoteTitle)).check(matches(withText(note.title)))
            onView(withId(R.id.textNoteText)).check(matches(withText(note.text)))

            if (index != DataManager.notes.lastIndex){
                onView(allOf(withId(R.id.action_next), isEnabled())).perform(click())
            }
        }
        onView(withId(R.id.action_next)).check(matches(isEnabled()))
    }
     */
}