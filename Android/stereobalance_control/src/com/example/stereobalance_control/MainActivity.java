package com.example.stereobalance_control;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener{
	private float mXf,mYf,mZf;
	private SensorManager sm1;
	private Sensor mAcc;
	private final float NOISE=(float)2;
	float angle1,angle2;
	
	private float computeAngle(float axis1,float axis2){
    	//Compute angle from axis values
		float angle=(float)Math.atan(axis1/axis2);
    	return angle;
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm1=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAcc=sm1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm1.registerListener(this, mAcc,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	sm1.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	sm1.unregisterListener(this);
    }
    
    @Override
    public void onAccuracyChanged(Sensor s, int accuracy){
    }
    
    @Override
    public void onSensorChanged(SensorEvent ev){
    	float x=ev.values[0];
    	float y=ev.values[1];
    	float z=ev.values[2];
    	mXf=x;
    	mYf=y;
    	mZf=z;
	    angle1=this.computeAngle(mZf,mXf);
	    angle2=this.computeAngle(mZf,mYf);
	    float f1=Math.abs(angle1);
	    float f2=Math.abs(angle2);
	    if (f1<NOISE) angle1=(float)0;
	    if (f2<NOISE) angle2=(float)0;
	    TextView a1=(TextView)findViewById(R.id.angle_1);
	    TextView a2=(TextView)findViewById(R.id.angle_2);
	    a1.setText(Float.toString(angle1));
	    a2.setText(Float.toString(angle2));
    }
}