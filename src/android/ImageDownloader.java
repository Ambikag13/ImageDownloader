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
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginManager;
import org.apache.cordova.PluginResult;
import org.apache.cordova.file.FileUtils;
import org.apache.cordova.file.LocalFilesystemURL;
import org.apache.cordova.mediacapture.Capture;
import org.apache.cordova.mediacapture.FileHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class ImageDownloader extends CordovaPlugin {

    private static final String TAG = ImageDownloader.class.getName();

	public static final String DOWNLOAD_IMAGE = "downloadImage";
	
	private static final String FILE_NAME_KEY = "name";
	private static final String FILE_FULLPATH = "fullPath";
	private static final String FILE_LOCAL_URL = "localURL";
	private static final String FILE_TYPE = "type";
	private static final String FILE_LAST_MODIFIED_DATE = "lastModifiedDate";
	private static final String FILE_SIZE = "size";
	private static final String RESULT_CODE = "code";
	private static final String RESULT_MESSAGE = "message";
	
    private CallbackContext callbackContext;        // The callback context from which we were invoked.
    private JSONArray results;                      // The array of results to be returned to the user


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	Log.i(TAG, "execute method  "+action);
    	this.callbackContext = callbackContext;
        this.results = new JSONArray();
    	try{
    		if(DOWNLOAD_IMAGE.equals(action)){
    			
        	}
    	}catch(Exception e) {
    	    System.err.println("Exception: " + e.getMessage());
    	    callbackContext.error(e.getMessage());
    	    return false;
    	} 
    	return true;
    }
       

}
