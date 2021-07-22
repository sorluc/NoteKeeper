package com.example.notekeeper

object DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init {
        initializeCourses()
        initializeNotes()
    }


    fun loadNotes(vararg noteIds: Int): List<NoteInfo> {
        simulateLoadDelay()
        val noteList: List<NoteInfo>

        if(noteIds.isEmpty())
            noteList = notes
        else {
            noteList = ArrayList<NoteInfo>(noteIds.size)
            for(noteId in noteIds)
                noteList.add(notes[noteId])
        }
        return noteList
    }

    fun loadNote(noteId: Int) = notes[noteId]

    fun idOfNote(note: NoteInfo) = notes.indexOf(note)

    fun noteIdsAsIntArray(notes: List<NoteInfo>): IntArray {
        val noteIds = IntArray(notes.size)
        for(index in 0..notes.lastIndex)
            noteIds[index] = DataManager.idOfNote(notes[index])
        return noteIds
    }

    private fun simulateLoadDelay() {
        Thread.sleep(1000)
    }

    fun addNote(course: CourseInfo, noteTitle: String, noteText: String): Int {
        val note = NoteInfo(course, noteTitle, noteText)
        notes.add(note)
        return notes.lastIndex
    }

    fun addNote(note: NoteInfo): Int {
        notes.add(note)
        return notes.lastIndex
    }

    fun findNote(course: CourseInfo, noteTitle: String, noteText: String): NoteInfo? {
        for (note in notes)
            if (course == note.course &&
                noteTitle == note.title &&
                noteText == note.text)
                    return note

        return null
    }


    private fun initializeCourses() {
        var course = CourseInfo("android_intents","Android Programming with Intents")
        courses.set(course.courseId,course)

        course = CourseInfo(courseId = "android_async",title = "Android Async Programming and Services")
        courses.set(course.courseId,course)

        course = CourseInfo(title = "Java Fundamentals: The Java Language", courseId = "java_lang")
        courses.set(course.courseId,course)

        course = CourseInfo("java_core", "Java Fundamentals: The Core Platform")
        courses.set(course.courseId,course)
    }

    fun initializeNotes() {
        var note = NoteInfo(courses["android_intents"],"Android Programming with Intents", "My notes on android intents")
        notes.add(note)

        note = NoteInfo(courses["android_async"],"Android Async Programming and Services", "My notes on android async")
        notes.add(note)

        note = NoteInfo(courses["java_lang"],"Java Fundamentals: The Java Language", "My notes on Java Language")
        notes.add(note)

        note = NoteInfo(courses["java_core"], "Java Fundamentals: The Core Platform", "My notes on Java Core")
        notes.add(note)


        addNote(courses["android_intents"]!!,"Android Programming with Intents 2", "My notes on android intents")
        addNote(courses["android_async"]!!,"Android Async Programming and Services 2", "My notes on android async")
        addNote(courses["java_lang"]!!,"Java Fundamentals: The Java Language 2", "My notes on Java Language")
        addNote(courses["java_core"]!!, "Java Fundamentals: The Core Platform 2", "My notes on Java Core")
    }

}