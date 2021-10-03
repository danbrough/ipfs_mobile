package danbroid.ipfsmobile.test

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.util.*

val context: Context = InstrumentationRegistry.getInstrumentation().context

val log = danbroid.logging.configure("TEST", coloured = true)

fun randomUUID() = UUID.randomUUID().toString()