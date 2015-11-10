package com.chanpinzazhi.refresh;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		if (Integer.parseInt(Build.VERSION.SDK) >= 9) {
			this.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}
}
