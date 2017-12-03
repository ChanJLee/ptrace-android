package com.chan.fuckdex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by chan on 2017/12/3.
 */

public class Main {
	public static void main(String[] args) {
		System.out.println("start...");
		try {
			Process process = Runtime.getRuntime().exec("./data/local/tmp/drizzleDumper com.chan.fuckdex 5");
//			Thread thread = new Thread(new FOO(process.getInputStream()));
//			thread.start();
			InputStreamReader reader = new InputStreamReader(process.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			System.out.println("read io");
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class FOO implements Runnable {

		private InputStream mInputStream;

		public FOO(InputStream inputStream) {
			mInputStream = inputStream;
		}

		@Override
		public void run() {
			System.out.println("run io");
			try {
				InputStreamReader reader = new InputStreamReader(mInputStream);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = null;
				System.out.println("read io");
				while ((line = bufferedReader.readLine()) != null) {
					System.out.println(line);
				}
				bufferedReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
