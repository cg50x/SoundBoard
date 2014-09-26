package com.panzoid.soundboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.graphics.LinearGradient;

public class SoundButton extends Button {
	
	// Background color
	private Paint bgColor;
	
	// Foreground color
	private Paint paint;
	
	// Dimensions
	private RectF canvasDim,
				  meterDim,
				  buttonDim;
	private RectF BUTTON_MARGIN;
	private LinearGradient lg;
	
	// Progress
	private float progress;

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
		paint = new Paint();
		paint.setAntiAlias(true);
		
		canvasDim = new RectF();
		meterDim = new RectF();
		buttonDim = new RectF();
		BUTTON_MARGIN = new RectF(3,3,3,3);
		progress = .5f;
		
		lg = new LinearGradient(0,0,100,100,Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);
	}
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// get entire button+meter minus the margin
		canvasDim.set(BUTTON_MARGIN.left,BUTTON_MARGIN.top,
				w - BUTTON_MARGIN.right,h - BUTTON_MARGIN.bottom);
		
		// Calculate meter dimensions
		meterDim.set(
				canvasDim.left,
				canvasDim.top,
				canvasDim.right,
				canvasDim.bottom * .1f
				);
		
		// Calculate actual button dimensions
		//buttonDim.set(src);
	}
	
	public void onDraw(Canvas canvas) {
		
		// "Clear" canvas
		canvas.drawColor(Color.TRANSPARENT);

		// Draw top meter
		drawMeter(canvas);
		
		// Draw button itself
		
		// Draw small border
		Log.i("SoundButton", "onDraw called");

	}
	
	private void drawMeter(Canvas canvas) {
		paint.reset();
		
		paint.setStrokeWidth(3);
		paint.setShader(lg);
		canvas.drawLine(meterDim.left, meterDim.top, meterDim.right * progress, meterDim.bottom, paint);
	}
	
	public int getBGColor() {
		return bgColor.getColor();
	}
	
	public int getFGColor() {
		return paint.getColor();
	}
	
	public void setBGColor(int red, int green, int blue) {
		bgColor.setColor(Color.argb(255,red, green, blue));
	}
	
	public void setBGColor(int color) {
		bgColor.setColor(color);
	}
	
	public void setFGColor(int red, int green, int blue) {
		paint.setColor(Color.argb(255,red, green, blue));
	}
	
	public void setFGColor(int color) {
		bgColor.setColor(color);
	}
	
	public float getProgress() {
		return progress;
	}
	
	public void setProgress(float percent) {
		progress = percent;
	}

}
