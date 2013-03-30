package loveworld.slidingmenu;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 左侧视图
        View leftViewGroup = createLeftListView();
		
        //　右侧视图
        View listView = createRightListView();
        
        final SlidingMenu mSlidingMenu = new SlidingMenu(this);
        mSlidingMenu.addLeftView(leftViewGroup);
        mSlidingMenu.addRightView(listView);
        
        setContentView(mSlidingMenu);
    }

	
	private View createLeftListView() {
		LinearLayout linearLayout = new LinearLayout(getBaseContext());
		linearLayout.setLayoutParams(new LayoutParams(300, LayoutParams.FILL_PARENT));
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		
		TextView textViewOne = createTextView("俺是首页", Color.BLUE);
		TextView textViewTwo = createTextView("。。。。。。", Color.GRAY);
		TextView textViewThree = createTextView("俺是导航", Color.BLUE);
		TextView textViewFour = createTextView("俺有点丑", Color.GRAY);
		TextView textViewFive = createTextView("俺是设置", Color.BLUE);
		linearLayout.addView(textViewOne);
		linearLayout.addView(textViewTwo);
		linearLayout.addView(textViewThree);
		linearLayout.addView(textViewFour);
		linearLayout.addView(textViewFive);
		
		return linearLayout;
	}


	private TextView createTextView(final String text, final int color) {
		TextView textView = new TextView(getBaseContext());
		textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		textView.setText( text );
		textView.setPadding(0, 50, 0, 50);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(color);
		textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getBaseContext(), "click  " + text, Toast.LENGTH_SHORT).show();
			}
		});
		return textView;
	}
    
	private View createRightListView() {
		ListView listView = new ListView(this);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
			arrayList.add("World " + i);
		}
        CustomBaseAdapter customBaseAdapter = new CustomBaseAdapter(getBaseContext(), arrayList);
        listView.setAdapter(customBaseAdapter);
        listView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        listView.setDivider( new ColorDrawable(Color.BLACK) );
        listView.setDividerHeight(1);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Toast.makeText(getBaseContext(), "World position = " + position, Toast.LENGTH_SHORT).show();
			}
		});
		return listView;
	}
	
    // ===========================================================
    // Methods
    // ===========================================================
    
    
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================
	

}