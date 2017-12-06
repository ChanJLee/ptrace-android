package com.chan.fuckdex;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;

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

		findViewById(R.id.request_permissions).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] permissions = new String[2];
				permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;
				permissions[1] = Manifest.permission.READ_EXTERNAL_STORAGE;
				ActivityCompat.requestPermissions(MainActivity.this, permissions, 0525);
			}
		});

		findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Process process = Runtime.getRuntime().exec("su");
					DataOutputStream os = new DataOutputStream(process.getOutputStream());
					os.write(String.format("chmod 777 %s", getPackageCodePath()).getBytes());
					os.write("exit".getBytes());
					os.flush();
					os.close();
					process.waitFor();
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.read_dex).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView tv = (TextView) findViewById(R.id.console);
				mDexReference = readDex();
				try {
					ApplicationInfo appInfo = MainActivity.this.getPackageManager()
							.getApplicationInfo(MainActivity.this.getPackageName(), PackageManager.GET_META_DATA);
					tv.setText("reference: " + mDexReference + " " + appInfo.sourceDir + " " +  getPackageCodePath());
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.ptrace).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ptrace("com.chan.fuckdex", 5);
			}
		});
	}

	private native long readDex();

	private native void ptrace(String pkg, double duration);
}
