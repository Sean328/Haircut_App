package com.haircut.haircut_alpha.view;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class Round {
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final Paint paint = new Paint();
    //保证是方形，并且从中心画
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    int w;
    int deltaX = 0;
    int deltaY = 0;
    if (width <= height) {
        w = width;
        deltaY = height - w;
    } else {
        w = height;
        deltaX = width - w;
    }
    final Rect rect = new Rect(deltaX, deltaY, w, w);
    final RectF rectF = new RectF(rect);

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    //圆形，所有只用一个
    
    int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
    canvas.drawRoundRect(rectF, radius, radius, paint);

    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);
    return output;
}
	

	public static Bitmap toRoundBitmap(Bitmap bitmap)
	   {
	       int width = bitmap.getWidth();
	       int height = bitmap.getHeight();
	       float roundPx;
	       float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
	       if (width <= height) {
	           roundPx = width / 2 -5;
	           top = 0;
	           bottom = width;
	           left = 0;
	           right = width;
	           height = width;
	           dst_left = 0;
	           dst_top = 0;
	           dst_right = width;
	           dst_bottom = width;
	       } else {
	           roundPx = height / 2 -5;
	           float clip = (width - height) / 2;
	           left = clip;
	           right = width - clip;
	           top = 0;
	           bottom = height;
	           width = height;
	           dst_left = 0;
	           dst_top = 0;
	           dst_right = height;
	           dst_bottom = height;
	       }
	  
	       Bitmap output = Bitmap.createBitmap(width,
	               height, Bitmap.Config.ARGB_8888);
	       Canvas canvas = new Canvas(output);
	  
	       final int color = 0xff424242;
	       final Paint paint = new Paint();
	       final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
	       final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
	       final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);
	  
	       paint.setAntiAlias(true);
	  
	       canvas.drawARGB(0, 0, 0, 0);
	       paint.setColor(color);
	  
	       canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
	  
	       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	       canvas.drawBitmap(bitmap, src, dst, paint);
	       return output;
	   }


}
