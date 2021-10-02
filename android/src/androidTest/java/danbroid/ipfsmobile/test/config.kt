package danbroid.ipfsmobile.test

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

val context: Context = InstrumentationRegistry.getInstrumentation().context

val log = danbroid.logging.configure("TEST",coloured = true)