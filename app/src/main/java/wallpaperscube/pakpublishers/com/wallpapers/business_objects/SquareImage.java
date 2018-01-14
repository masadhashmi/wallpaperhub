package wallpaperscube.pakpublishers.com.wallpapers.business_objects;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Asad on 8/21/2016.
 */
public class SquareImage extends ImageView {
        public SquareImage(Context context) {
            super(context);
        }

        public SquareImage(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public SquareImage(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
        }

}
