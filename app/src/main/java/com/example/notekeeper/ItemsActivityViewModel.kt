package com.example.notekeeper

import android.os.Bundle
import androidx.lifecycle.ViewModel
import android.util.Log

class ItemsActivityViewModel: ViewModel() {
    private val tag = this::class.simpleName

    var isNewlyCreated = true

    var navDrawerDisplaySelectionName =
        "com.example.notekeeper.ItemsActivityViewModel.navDrawerDisplaySelection"

    var recentlyViewedNoteIdsName =
        "com.example.notekeeper.ItemsActivityViewModel.recentlyViewedNoteIds"

    var navDrawerDisplaySelection = R.id.nav_notes

    private val maxRecentlyViewedNotes = 3
    val recentlyViewedNotes = ArrayList<NoteInfo>(maxRecentlyViewedNotes)

    fun addToRecentlyViewedNotes(note: NoteInfo){
        Log.d(tag,"addToRecentlyViewedNotes")
        val existingIndex = recentlyViewedNotes.indexOf(note)
        if (existingIndex == -1){
            Log.d(tag,"addToRecentlyViewedNotes - not existing")
            recentlyViewedNotes.add(0,note)
            for (index in recentlyViewedNotes.lastIndex downTo maxRecentlyViewedNotes)
                recentlyViewedNotes.removeAt(index)
        } else {
            Log.d(tag,"addToRecentlyViewedNotes - existing")
            for (index in (existingIndex - 1) downTo 0)
                recentlyViewedNotes[index+1] = recentlyViewedNotes[index]
            recentlyViewedNotes[0] = note
        }
    }

    fun saveState(outState: Bundle) {
        outState.putInt(navDrawerDisplaySelectionName,navDrawerDisplaySelection)
        val noteIds = DataManager.noteIdsAsIntArray(recentlyViewedNotes)
        outState.putIntArray(recentlyViewedNoteIdsName,noteIds)
        Log.d(tag,"saveState")
    }

    fun restoreState(savedInstanceState: Bundle) {
        navDrawerDisplaySelection = savedInstanceState.getInt(navDrawerDisplaySelectionName )
        val noteIds = savedInstanceState.getIntArray(recentlyViewedNoteIdsName)
        val noteList = noteIds?.let { DataManager.loadNotes(*it) }
        if (noteList != null)
            recentlyViewedNotes.addAll(noteList)
        Log.d(tag,"restoreState: " + noteList)
    }
}