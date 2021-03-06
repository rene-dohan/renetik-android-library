package renetik.android.controller.common

import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import renetik.android.base.CSApplicationObject.application
import renetik.android.base.layout
import renetik.android.controller.R
import renetik.android.controller.base.CSViewController
import renetik.android.controller.extensions.*
import renetik.android.view.extensions.onClick
import renetik.android.view.extensions.text

const val sendLogMailKey = "send_log_mail"

class CSLogDisplayController(val navigation: CSNavigationController,
                             val title: String = "Application Log") :
    CSViewController<View>(navigation, layout(R.layout.cs_log_panel)), CSNavigationItem {

    private val logText = textView(R.id.CSLog_LogText)

    init {
        menuItem("Send to developer").onClick { onSendLogClick() }.alwaysAsAction()
        menuItem("Scroll to bottom")
            .onClick { scrollView(R.id.CSLog_TextScroll).scrollToBottom() }.neverAsAction()
    }

    override fun onCreate() {
        super.onCreate()
        textView(R.id.CSLog_Title).text(title)
        floatingButton(R.id.CSLog_Reload).onClick { loadText() }
        loadText()
    }

    private fun loadText() = logText.text(application.logger.logString())

    private fun onSendLogClick() {
        dialog("Send application log", "Enter target email")
            .showInput("Target email", application.store.getString(sendLogMailKey, "")) { dialog ->
                application.store.save(sendLogMailKey, dialog.inputText)
                sendMail(dialog.inputText,
                    application.name +
                            " This is log from application sent as email attachment for application developer",
                    logText.text())
            }
    }
}

private fun TextView.scrollToBottom() {
    post { scrollTo(0, bottom) }
}

private fun ScrollView.scrollToBottom() {
    post { fullScroll(View.FOCUS_DOWN) }
}
