package com.panzoid.soundboard.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.graphics.LinearGradient;
import android.graphics.Path;
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
	private LinearGradient glassGradient;
	private RadialGradient blurMeterGradient;
	
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
		
		lg = new LinearGradient(0,200,
								0,170,
								Color.argb(150, 0,200,68), 
								//Color.argb(200, 255,0,68),
								Color.TRANSPARENT, Shader.TileMode.CLAMP);
		glassGradient = new LinearGradient(0, 0, 0,45, Color.argb(64, 255, 255, 255), Color.TRANSPARENT, Shader.TileMode.CLAMP);
		blurMeterGradient = new RadialGradient(0,100,
											   100,
											   Color.WHITE,
											   Color.argb(128,255,255,255),
											   Shader.TileMode.CLAMP);
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
				canvasDim.bottom * .03f
				);
		
		// Calculate actual button dimensions
		buttonDim.set(
				canvasDim.left,
				canvasDim.top,
				canvasDim.right,
				canvasDim.bottom);
	}
	
	public void onDraw(Canvas canvas) {
		
		// "Clear" canvas
		canvas.drawColor(Color.BLACK);
		
		paint.reset();
		
		// Draw base button color
		canvas.clipRect(buttonDim);
		//paint.setColor(Color.BLACK);
		//paint.setColor(Color.argb(64,255,7,0));
		//paint.setColor(Color.argb(64,0,255,85));
		canvas.drawRect(buttonDim.left, 
		        buttonDim.top, 
		        buttonDim.right, 
		        buttonDim.bottom, paint);
		
		// Draw diffused lighting
		paint.setShader(lg);
		canvas.drawRect(buttonDim.left, 
		        buttonDim.top, 
		        buttonDim.right, 
		        buttonDim.bottom, paint);
		
		// Draw meter
		paint.setColor(Color.RED);
		paint.setShader(blurMeterGradient);
		canvas.drawRect(meterDim.left, meterDim.top, meterDim.right * progress, meterDim.bottom, paint);
		
		// Draw specular lighting
		paint.reset();
		paint.setShader(glassGradient);
		paint.setAntiAlias(true);
		canvas.drawCircle(buttonDim.left + 65, buttonDim.top - 425, 460, paint);

		canvas.restore();
		
		// Draw faint left/right borders
		paint.reset();
		paint.setColor(Color.argb(32, 255, 255, 255));
		canvas.drawLine(buttonDim.left, buttonDim.top, buttonDim.left, buttonDim.bottom, paint);
		canvas.drawLine(canvasDim.right, 0, canvasDim.right, canvasDim.bottom, paint);
		
		// Draw button itself
		//paint.reset();
		//drawButton(canvas);
		
		// Draw glossy glass
		//paint.reset();
		//drawGlass(canvas);
		Log.i("SoundButton", "onDraw called");
		
		// Draw top meter
		//		paint.reset();
		//		drawMeter(canvas);

	}
	
	private void drawGlass(Canvas canvas) {
		paint.setShader(glassGradient);
		canvas.drawRect(canvasDim, paint);	
	}
	
	private void drawMeter(Canvas canvas) {		
		paint.setStrokeWidth(2);
		paint.setColor(Color.RED);
		canvas.drawRect(meterDim.left, meterDim.top, meterDim.right * progress, meterDim.bottom, paint);
	}
	
	private void drawButton(Canvas canvas) {
		canvas.clipRect(buttonDim);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		canvas.drawRect(buttonDim.left, 
				        buttonDim.top, 
				        buttonDim.right, 
				        buttonDim.bottom, paint);
		paint.setShader(lg);
		canvas.drawRect(buttonDim.left, 
		        buttonDim.top, 
		        buttonDim.right, 
		        buttonDim.bottom, paint);
		paint.setShader(glassGradient);
		canvas.drawCircle(buttonDim.left, buttonDim.top - 400, 485, paint);
		canvas.restore();
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
