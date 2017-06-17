package com.genesis.yazlab;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class OzelAdapter extends BaseAdapter {

	Context context;
	static LayoutInflater inflater=null;
	List<Ev> evler=new ArrayList<Ev>();
	
	
	public OzelAdapter(Context context,List<Ev> evList)
	{
		this.context=context;
		this.evler=evList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return evler.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItemId(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View satir=convertView;
		if(satir==null)
		{
			inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			satir=inflater.inflate(R.layout.satir,null);
			
		}
		
		TextView txtEvID=(TextView)satir.findViewById(R.id.txtEvId);
		TextView txtEvIl=(TextView)satir.findViewById(R.id.txtEvIl);
		TextView txtEvEmlakTip=(TextView)satir.findViewById(R.id.txtEvEmlakTip);
		TextView txtEvFiyat=(TextView)satir.findViewById(R.id.txtEvFiyat);
		
		
				   	
		txtEvID.setText(evler.get(position).getEvId()+" ");
		txtEvIl.setText(evler.get(position).getEvIl()+" ");
		txtEvEmlakTip.setText(evler.get(position).getEvEmlak()+" ");
		txtEvFiyat.setText(evler.get(position).getFiyat() + " ");
		/*txtEvID.setText("deneme");
		
		txtEvIl.setText("deneme");
		txtEvEmlakTip.setText("deneme");
		txtEvFiyat.setText("deneme");*/
		return satir;
	}

}