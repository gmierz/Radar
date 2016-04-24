package ubishops.hackaton.ca.cejirt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.util.AttributeSet;
import android.content.Context;

import static android.graphics.Bitmap.createScaledBitmap;

/**
 * Created by Jean-Simon on 23/04/2016.
 * This class is meant to punch a hole in a black rectangle so we just see the map
 */
public class RoundMapView extends RelativeLayout {


    public RoundMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas); // Call super first (this draws the map) we then draw on top of it

        Paint eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        Canvas holeCanvas = new Canvas(bitmap);

        holeCanvas.drawColor(Color.rgb(145, 175, 59));

        /********Set circle to right size and position***********/
        float cx = getX() + getWidth() / 2;
        float cy = getY() + getHeight() / 2;
        float radius;
        if (getWidth() < getHeight()) { //Set radius according which is larger between width and height
            radius = getWidth() / 2;
        } else {
            radius = getHeight() / 2;
        }

        holeCanvas.drawCircle(cx, cy, radius, eraser);
        canvas.drawBitmap(bitmap, 0, 0, null);

        //Select the switchToHome button
        int i;
        for (i = 0; i < getChildCount(); i++) {
            if (getChildAt(i).getId() == R.id.switchToHome) {
                break;
            }
        }
        getChildAt(i).setX(getWidth() / 2 - getChildAt(i).getWidth() / 2); //Set it to a visible spot
        getChildAt(i).setY(getHeight() / 2 + radius - getChildAt(i).getHeight());

    }
}
