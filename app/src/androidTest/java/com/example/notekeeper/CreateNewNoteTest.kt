package com.example.notekeeper

import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import androidx.test.runner.AndroidJUnit4

import androidx.test.espresso.*
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.hamcrest.Matchers.*


@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest {
    /*
    @Rule @JvmField
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun createNewNote(){
        val course = DataManager.courses["android_async"]
        val noteTitle = "Test note title"
        val noteText = "This is the body of my test note"

        onView(withId(R.id.fab)).perform(click())

        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())


        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTitle))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText), closeSoftKeyboard())

        pressBack()

        val newNote = DataManager.notes.last()
        assertEquals(course,newNote.course)
        assertEquals(noteTitle,newNote.title)
        assertEquals(noteText,newNote.text)

    }
    */

}