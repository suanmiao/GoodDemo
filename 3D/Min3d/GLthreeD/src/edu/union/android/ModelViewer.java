package edu.union.android;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import edu.union.graphics.IntMesh;
import edu.union.graphics.MD2Loader;
import edu.union.graphics.Model;
import edu.union.graphics.ObjLoader;

public class ModelViewer extends Activity {
	InputStream is;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);	
		
//		is = getResources().openRawResource(R.drawable.tris);
//		
//		MD2Loader ld = new MD2Loader();
//		ld.setFactory(IntMesh.factory());
		is = getResources().openRawResource(R.drawable.cubee);
		ObjLoader ld = new ObjLoader();
		ld.setFactory(IntMesh.factory());
		
		
		
		try {
			Model model = ld.load(is);
				setContentView(new ModelViewInterpolated(model, this));
		} 
		catch (java.io.IOException ex) {
			setContentView(R.layout.main);
		}
	}
	
    protected boolean isFullscreenOpaque() {
        // Our main window is set to translucent, but we know that we will
        // fill it with opaque data. Tell the system that so it can perform
        // some important optimizations.
        return true;
    }	
}