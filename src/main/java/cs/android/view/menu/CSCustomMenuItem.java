package cs.android.view.menu;

import cs.java.callback.CSRunWith;

import static cs.java.lang.CSLang.run;

/**
 * Created by renetik on 25/12/17.
 */

public class CSCustomMenuItem {
    private int _index;
    private CharSequence _title;
    private int _iconResource;
    private CSRunWith<CSCustomMenuItem> _onClick;
    private String _note;

    public CSCustomMenuItem(int index, CharSequence title) {
        _index = index;
        _title = title;
    }

    public CSCustomMenuItem icon(int iconResource) {
        _iconResource = iconResource;
        return this;
    }

    public int iconResource() {
        return _iconResource;
    }

    public CharSequence title() {
        return _title;
    }

    public int index() {
        return _index;
    }

    public CSCustomMenuItem onClick(CSRunWith<CSCustomMenuItem> onClick) {
        _onClick = onClick;
        return this;
    }

    public String note() {
        return _note;
    }

    public void onClick() {
        run(_onClick, this);
    }

    public CSCustomMenuItem note(String note) {
        _note = note;
        return this;
    }
}