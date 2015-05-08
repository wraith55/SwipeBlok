package com.example.wraith55.swipeblok;


 import android.content.Context;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;
 import android.widget.GridView;
 import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    /**
     * Returns the view of the image adapter
     * @param position in the array
     * @param convertView if you want a specific review converted to this context
     * @param parent
     * @return a view
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            //size of the grid
            imageView.setLayoutParams(new GridView.LayoutParams(175, 175));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //padding between cells
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        //get the thumbnail from the position
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    /**
     * updates the grid view with an array
     * @param outputArr the array from the main activity which tells the gridview which pictures to load
     */
    public void update(String[] outputArr){
        //set all pics to correct according to array
        for(int i=0;i<outputArr.length;i++){
            if(outputArr[i]==MainActivity.empty){
                mThumbIds[i] = picArray[0];
            }
            else{
                mThumbIds[i] = picArray[Integer.parseInt(outputArr[i])];
            }
        }
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,
            R.drawable.emptyicon,R.drawable.emptyicon,

    };

    //the drawables that can be in an array
    public Integer[] picArray = {
            R.drawable.emptyicon, R.drawable.staricon,
            R.drawable.circleicon, R.drawable.cloudicon,
            R.drawable.triangleicon, R.drawable.squareicon,
            R.drawable.xicon, R.drawable.bombicon
    };
}