/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package com.snipme.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class ImageDownloader extends CordovaPlugin {

	private static final String TAG = ImageDownloader.class.getName();
	
	int count;

	public static final String DOWNLOAD_IMAGE = "downloadImage";

	private static final String FILE_NAME_KEY = "name";
	private static final String FILE_FULLPATH = "fullPath";
	private static final String FILE_LOCAL_URL = "localURL";
	private static final String FILE_TYPE = "type";
	private static final String FILE_LAST_MODIFIED_DATE = "lastModifiedDate";
	private static final String FILE_SIZE = "size";
	private static final String RESULT_CODE = "code";
	private static final String RESULT_MESSAGE = "message";

	private CallbackContext callbackContext; // The callback context from which
												// we were invoked.
	private JSONArray results; // The array of results to be returned to the
								// user

	@Override
	public boolean execute(String action, JSONArray args,
			CallbackContext callbackContext) throws JSONException {
		Log.i(TAG, "execute method  " + action);
		this.callbackContext = callbackContext;
		this.results = new JSONArray();
		try {
			if (DOWNLOAD_IMAGE.equals(action)) {
				Log.i(TAG, "entered");
				for(int i=0;i<5;i++){
					if(i==0){
						new Downloader(
								"http://snipmetv.com/dev/assets/v/campaign/6524f58e64b819e3aa9f56a26ac250d1/cutin_54819ccaecbc6.png",
								"cutin_54819ccaecbc6").execute();
					}
					if(i==1){
						new Downloader(
								"http://snipmetv.com/dev/assets/v/campaign/6524f58e64b819e3aa9f56a26ac250d1/topbar.png",
								"topbar").execute();
					}
					if(i==2){
						new Downloader(
								"http://snipmetv.com/dev/assets/v/campaign/6524f58e64b819e3aa9f56a26ac250d1/bottombar_1.png",
								"bottombar_1").execute();
					}
					if(i==3){
						new Downloader(
								"http://snipmetv.com/dev/assets/v/campaign/6524f58e64b819e3aa9f56a26ac250d1/splash.png",
								"splash").execute();
					}
					if(i==4){
						new Downloader(
								"http://snipmetv.com/dev/assets/v/campaign/6524f58e64b819e3aa9f56a26ac250d1/end.png",
								"end").execute();
					}
					
					
				}
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			callbackContext.error(e.getMessage());
			return false;
		}
		return true;
	}

	private class Downloader extends AsyncTask<Object, Object, Object> {
		private String requestUrl, imagename;

		private Bitmap bitmap;

		private Downloader(String requestUrl, String imagename) {
			this.requestUrl = requestUrl;
			this.imagename = imagename;
		}

		@Override
		protected Object doInBackground(Object... params) {
			/*try {
				URL url = new URL(requestUrl);
				URLConnection conn = url.openConnection();
				bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			} catch (Exception ex) {
			}*/
			try{

                URL url = new URL(requestUrl);//"http://192.xx.xx.xx/mypath/img1.jpg
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.connect();
                int responseCode = con.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK)
                {
                    //download 
                	InputStream in = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    Log.i("DownloadImg", "Bitmap: "+bitmap);
                    in.close();
                    //iv.setImageBitmap(bmp);
                    return bitmap;
                }

           }
           catch(Exception ex){
               Log.e("Exception",ex.toString());
               return null;
           }
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			++count;
			Log.i("ImageDownloader", "image download is sucess- "+count+" "+bitmap);
			ImageStorage.saveToSdCard(bitmap, imagename);
			super.onPostExecute(result);
		}
	}

}

class ImageStorage {

	public static String saveToSdCard(Bitmap bitmap, String filename) {

		String stored = null;
		if (!Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return  null;
        }
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(),"SnipMe");
        
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
        	Log.i("DownloadImage", "Create Folder Snipme");
            if (! mediaStorageDir.mkdirs()) {
                Log.d("DownloadImage", "failed to create directory SnipMe");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(mediaStorageDir.getPath() + File.separator +
                    filename+"_"+ timeStamp + ".png");
		
		

		//File sdcard = Environment.getExternalStorageDirectory();

		//File folder = new File(sdcard.getAbsoluteFile(), "SnipMe");// the
																		// dot
																		// makes
																		// this
																		// directory
																		// hidden
																		// to
																		// the
																		// user
		//folder.mkdir();
		//File file = new File(folder.getAbsoluteFile(), filename + ".png");
		if (file.exists())
			return stored;

		try {
			FileOutputStream out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			stored = "success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stored;
	}

	public static File getImage(String imagename) {

		File mediaImage = null;
		try {
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root);
			if (!myDir.exists())
				return null;

			mediaImage = new File(myDir.getPath()
					+ "/.your_specific_directory/" + imagename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mediaImage;
	}

	public static boolean checkifImageExists(String imagename) {
		Bitmap b = null;
		File file = ImageStorage.getImage("/" + imagename + ".jpg");
		String path = file.getAbsolutePath();

		if (path != null)
			b = BitmapFactory.decodeFile(path);

		if (b == null || b.equals("")) {
			return false;
		}
		return true;
	}

}
