package com.genesis.yazlab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

public class CustomSwipeAdapter extends PagerAdapter{

	private List<String> image_resources;
	private Context ctx;
	private LayoutInflater layoutInflater;
	
	
	public CustomSwipeAdapter(Context ctx,int position){
		this.ctx=ctx;
		image_resources=new ArrayList<String>();
		image_resources=MainActivity.evList.get(position).getResim();
		//image_resources.add("http://10.40.242.15:8080/WebService/img/ev1.jpg");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return image_resources.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0==(LinearLayout)arg1);
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		// TODO Auto-generated method stub
		layoutInflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View item_view=layoutInflater.inflate(R.layout.swipe_layout, container,false);
		final ImageView imageview=(ImageView)item_view.findViewById(R.id.image_view);
		TextView textview=(TextView)item_view.findViewById(R.id.image_count);
		//imageview.setImageResource(image_resources[position]);
		UrlImageViewHelper.setUrlDrawable(imageview, image_resources.get(position));
		
		
		textview.setText("Resim :"+(position+1));
		container.addView(item_view);
		return item_view;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((LinearLayout)object);
	}
	
}