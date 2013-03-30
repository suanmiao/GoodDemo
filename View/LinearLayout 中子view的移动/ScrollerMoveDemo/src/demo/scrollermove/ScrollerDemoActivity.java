package demo.scrollermove;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import demo.scrollermove.CustomLinearLayout.OnLogListener;

public class ScrollerDemoActivity extends Activity {

	private static final String INFO = "绿色背景CustomLinearLayout，包含一个红色TextView \n 以下显示的是CustomLinearLayout的参数";
	
	private TextView mOutput;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		mOutput = (TextView) findViewById(R.id.output);
		mOutput.setText(INFO
				+ "\n x = "
				+ "\n y = "
				+ "\n scroll X = "
				+ "\n scroll Y = ");
		
		final CustomLinearLayout customLayout = (CustomLinearLayout) findViewById(R.id.custom_linearlayout);
		customLayout.setOnLogListener(new CustomLogListener());
		
		
	}
	
	
	private class CustomLogListener implements OnLogListener {

		@Override
		public void setOutput(String pOutput) {
			mOutput.setText(INFO + pOutput);
		}
		
	}
	
	
}
