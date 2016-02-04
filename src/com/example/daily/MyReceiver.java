package com.example.daily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver
{

	//private  String frompath = "drawable";
	private  String topath = "/storage/sdcard0/DCIM";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			Toast.makeText(context, "start copy", Toast.LENGTH_LONG).show();
			copyFolder(context , topath);
		}
		
	}
	public void copyFolder(Context context, String newPath) {
		  
	    {
	        AssetManager assetManager = context.getAssets();
	        String[] files = null;
	        try {
	            files = assetManager.list("");
	        } catch (IOException e) {
	            Log.e("tag", "Failed to get asset file list.", e);
	        }
	        for(String filename : files) {
	            InputStream in = null;
	            OutputStream out = null;
	            try {
		          if (filename.endsWith("jpg")){
		        	  in = assetManager.open(filename);
	            	  File outFile = new File(newPath +"/"+filename);
	            	  if (outFile.exists())
	            		  return;
	            	  out = new FileOutputStream(outFile);
	            	  Toast.makeText(context, outFile.toString(), Toast.LENGTH_LONG).show();
	            	  byte[] buffer = new byte[8192];
	            	  int read =0;
	            	  while((read = in.read(buffer)) > 0){
	            		  out.write(buffer, 0, read);
	            	  }
	            	  Toast.makeText(context, filename + " OK", Toast.LENGTH_LONG).show();
	            	  in.close();
	            	  in = null;
	            	  out.flush();
	            	  out.close();
	            	  out = null;
	            	  //通知扫描，否则看不到
	            	  Intent intent = new Intent(android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	                  Uri uri = Uri.fromFile(new File(newPath +"/"+filename));
	                  intent.setData(uri);
	                  context.sendBroadcast(intent);
	              }
	            } catch(IOException e) {
	                Log.e("tag", "Failed to copy asset file: " + filename, e);
	            }       
	        }
	    }
	}	
}
