package com.example.daily;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class DailyActivity extends Activity {
	private PackageManager pm;
	public TextView done;
	public EditText list;
	public Button btntest;
	int result = 0;
	private Vibrator mVibrator;
	PackageManager packageManager = null; 
    ApplicationInfo applicationInfo = null; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("hutao", "start");
        setContentView(R.layout.activity_daily);
        //done=(TextView)findViewById(R.id.done);
        list=(EditText)findViewById(R.id.list);
        btntest=(Button)findViewById(R.id.button1);
        pm = this.getPackageManager();
        String s =110 + "°" + 23 + "'" + 45 + "\"";
		//list.setText(s);
        checkPermission1();
        btntest.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				/**try {
					ApplicationInfo ai = pm.getApplicationInfo("android.process.media", 0);
					list.setText(Integer.toString(ai.uid));
					} catch (NameNotFoundException e) {
					e.printStackTrace();
					}**/
				
				/**Intent intent = new Intent(Intent.ACTION_PICK);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("vnd.android.cursor.dir/audio");
				intent.setData(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
				startActivity(intent);**/
			 
			             String name = null;
			             /*try {
			                 name = pm.getApplicationLabel(
			                         pm.getApplicationInfo("com.google.android.apps.photos",
			                                 PackageManager.GET_META_DATA)).toString();
			                 list.setText(name);
			             } catch (NameNotFoundException e) {
			                 e.printStackTrace();
			             }
			             */
			             name =Settings.Secure.getString(getContentResolver(), "android_id");
			             list.setText(name);
			             

			}
			
			
        });
        //dosomething();
        
		

    }
    
    private void checkPermission1(){
   		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
   		int hasWriteStoragePermission1 = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
   		if (hasWriteStoragePermission1 != PackageManager.PERMISSION_GRANTED) {
   			requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},
   					1);
   			}

    }

  
    @Override
    protected void onResume(){
    	super.onResume();
    	Log.e("tom","OnResume");//解锁也会调用
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	Log.e("tom","OnPause");//power key 锁屏也会调用
    }
    
	private void dosomething() {
		// TODO Auto-generated method stub
		mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        list.setText("0");
        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
		alphaAnim.setDuration(2000);
		done.startAnimation(alphaAnim);
		list.startAnimation(alphaAnim);
		alphaAnim.setAnimationListener(new AnimationListener()
		{

			@Override
			public void onAnimationEnd(Animation arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "Animation end", Toast.LENGTH_SHORT).show();
				mVibrator.vibrate(200);
		        final Handler myHandler = new Handler()
		        {
		        	@Override
					public void handleMessage(Message msg)
		        	{
		        		if (msg.what==0X111)
		        		{
		        			int count = (int) (Math.random()*10000);
		        			result += count;
		        			String str = Integer.toString((int) result);
		        			list.setText(str);

		        		}
		        	}
		        };
		        
		        new Timer().schedule(new TimerTask()
		        {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						myHandler.sendEmptyMessage(0X111);
					}
		        	
		        }, 0, 2000);
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animation arg0) {
				Toast.makeText(getBaseContext(), "Animation start", Toast.LENGTH_SHORT).show();
				
			}
			
		});
	}
    
    /*public void getapplist()
    {
    	ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        
        List<RunningTaskInfo> list1=am.getRunningTasks(100);
        list.setText(list1.toString());
   
        ComponentName cn1 = list1.get(0).topActivity;
        ComponentName cn2 = list1.get(2).topActivity;
        packageManager = getApplicationContext().getPackageManager(); 
        try {
			applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String name =  
                (String) packageManager.getApplicationLabel(applicationInfo); 
        done.setText(cn1.getPackageName()+cn2.getPackageName());
    }

    */
}

