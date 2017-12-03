package com.chan.fuckdex;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	// Used to load the 'native-lib' library on application startup.
	static {
		System.loadLibrary("native-lib");
	}

	private long mDexReference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] permissions = new String[2];
				permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
				permissions[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
				ActivityCompat.requestPermissions(MainActivity.this, permissions, 0525);
			}
		});
		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Example of a call to a native method
				TextView tv = (TextView) findViewById(R.id.sample_text);
				mDexReference = stringFromJNI();
				try {
					ApplicationInfo appInfo = MainActivity.this.getPackageManager()
							.getApplicationInfo(MainActivity.this.getPackageName(), PackageManager.GET_META_DATA);
					tv.setText("reference: " + stringFromJNI() + " " + appInfo.sourceDir);
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * A native method that is implemented by the 'native-lib' native library,
	 * which is packaged with this application.
	 */
	public native long stringFromJNI();
}
