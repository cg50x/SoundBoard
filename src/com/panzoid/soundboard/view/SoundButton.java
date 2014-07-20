package com.panzoid.soundboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

public class SoundButton extends Button {
	
	// Background color
	private Paint bgColor;
	
	// Foreground color
	private Paint fgColor;
	
	// Dimensions
	private RectF buttonDim;
	
	private RectF progressCircleDim;
	
	// Progress circle
	private float sweepAngle;
	
	private boolean dimSaved;

	public SoundButton(Context context) {
		super(context);
		init();
	}
	
	public SoundButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public SoundButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		bgColor = new Paint();
		fgColor = new Paint();
		dimSaved = false;
		buttonDim = new RectF();
		progressCircleDim = new RectF();
		sweepAngle = 185f;
		bgColor.setColor(Color.argb(255, 100, 100, 100));
		fgColor.setColor(Color.argb(255,0,0,0));
		fgColor.setAntiAlias(true);
	}
	
	private void initButtonDimensions(float width, float height) {
		buttonDim.set(0,0,
					  width,
					  height);
		float diagonal = (float)Math.hypot(width / 2f, height / 2f);
		progressCircleDim.set(0,0,width, height);
		progressCircleDim.inset(-(diagonal-width/2f), -(diagonal-height/2f));
		dimSaved = true;
	}
	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (!dimSaved) {
			initButtonDimensions(canvas.getWidth(), canvas.getHeight());
		}
		
		// Direct all drawing operations to a separate buffer
		// whose dimensions are restricted.
		canvas.saveLayerAlpha(buttonDim, 100, Canvas.ALL_SAVE_FLAG);

		// Draw background
		canvas.drawColor(bgColor.getColor());
		
		// Draw wedge
		canvas.drawArc(progressCircleDim, 
					   270f, 
					   sweepAngle,
					   true, 
					   fgColor);
		
		// Draw small border
		Log.i("SoundButton", "onDraw called");
		
		canvas.restore();

	}
	
	public int getBGColor() {
		return bgColor.getColor();
	}
	
	public int getFGColor() {
		return fgColor.getColor();
	}
	
	public void setBGColor(int red, int green, int blue) {
		bgColor.setColor(Color.argb(255,red, green, blue));
	}
	
	public void setBGColor(int color) {
		bgColor.setColor(color);
	}
	
	public void setFGColor(int red, int green, int blue) {
		fgColor.setColor(Color.argb(255,red, green, blue));
	}
	
	public void setFGColor(int color) {
		bgColor.setColor(color);
	}
	
	public float getProgress() {
		return sweepAngle / 360f;
	}
	
	public void setProgress(float percent) {
		if (percent < 0)	percent = 0;
		if (percent > 1)	percent = 1;
		
		sweepAngle = percent * 360f;
	}

}
