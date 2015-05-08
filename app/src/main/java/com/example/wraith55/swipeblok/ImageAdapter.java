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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(175, 175));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public void update(String[] outputArr){
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

    public Integer[] picArray = {
            R.drawable.emptyicon, R.drawable.staricon,
            R.drawable.circleicon, R.drawable.cloudicon,
            R.drawable.triangleicon, R.drawable.squareicon,
            R.drawable.xicon, R.drawable.bombicon
    };
}