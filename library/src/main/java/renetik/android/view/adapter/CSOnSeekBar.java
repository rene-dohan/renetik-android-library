package renetik.android.view.adapter;

import renetik.android.viewbase.CSView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CSOnSeekBar implements OnSeekBarChangeListener {

	public CSOnSeekBar(CSView<?> view, int... viewId) {
		for (int id : viewId)
			((SeekBar) view.findView(id)).setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
	}

}