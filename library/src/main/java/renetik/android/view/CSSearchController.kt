package renetik.android.view

import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import renetik.android.extensions.NO
import renetik.android.extensions.YES
import renetik.android.viewbase.CSLayoutId
import renetik.android.viewbase.CSViewController
import renetik.android.viewbase.menu.CSMenuItem

class CSSearchController : CSViewController<SearchView> {

    private var query = ""
    private var queryListener: ((String) -> Unit)? = null
    fun queryListener(function: (String) -> Unit) = apply { queryListener = function }
    private var searchOpened = false
    private var expanded = false
    fun expanded(value: Boolean) = apply { expanded = value }
    private var searchMenuItem: CSMenuItem? = null

    constructor(parent: CSViewController<*>, searchMenuId: Int) : super(parent) {
        searchMenuItem = menu(searchMenuId)
    }

    constructor(parent: CSViewController<*>, layoutId: CSLayoutId) : super(parent, layoutId) {
        initializeSearch()
    }

    override fun onViewShowingFirstTime() {
        super.onViewShowingFirstTime()
        searchMenuItem?.actionView?.let {
            view = it as SearchView
            initializeSearch()
        }
    }

    private fun initializeSearch() {
        view.setQuery(query, NO)
        view.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String) = NO

            override fun onQueryTextChange(value: String): Boolean {
                query = value
                queryListener?.invoke(value)
                return false
            }
        })
        view.setOnSearchClickListener { searchOpened = YES }
        view.setOnCloseListener {
            searchOpened = NO
            false
        }
        view.setIconifiedByDefault(!expanded)
        view.isIconified = !expanded
        view.requestFocusFromTouch()
    }

    fun clear() {
        val tmpListener = queryListener
        queryListener = null
        view.setQuery("", NO)
        view.clearFocus()
        view.isIconified = true
        queryListener = tmpListener
    }
}