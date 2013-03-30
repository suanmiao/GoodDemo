package loveworld.slidingmenu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * getView第一个参数position从0开始
 * 
 * date: 2011-11-10
 * 
 */
public class CustomBaseAdapter extends BaseAdapter {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private Context mContext;
	private ArrayList<String> mArrayList;
	private LayoutInflater mLayoutInflater;
	
	// ===========================================================
	// Constructors
	// ===========================================================

	public CustomBaseAdapter(Context context, ArrayList<String> pData) {
		mContext = context;
		mArrayList = pData;
		
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	// ===========================================================
	// Public Methods
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// 常见的优化ViewHolder
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.contentIcon = (ImageView) convertView.findViewById(R.id.content_icon);
            
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 获取数据
        viewHolder.content.setText( mArrayList.get(position) );
        viewHolder.contentIcon.setImageResource(R.drawable.ic_launcher);

        return convertView;
	}
	
	
	@Override
	public int getCount() {
		if (null != mArrayList) {
			return mArrayList.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		if (null != mArrayList && position < mArrayList.size()) {
			return mArrayList.get(position); 
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	// ===========================================================
	// Private Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
	
	
	private class ViewHolder {
        TextView content;
        ImageView contentIcon;
    }
	
	
	
}
