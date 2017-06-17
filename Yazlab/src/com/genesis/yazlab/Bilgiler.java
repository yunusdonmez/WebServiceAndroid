package com.genesis.yazlab;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

public class Bilgiler extends Activity{
	ViewPager viewPager;
	CustomSwipeAdapter adapter;
	TextView txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8;
	private Intent intent;
	public static List<Ev> evList=new ArrayList<Ev>();
	protected void onCreate(Bundle savedInstanceState) {
		//startService(intent);
		//registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));

        	evList=MainActivity.evList;
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.bilgiler);
	        viewPager=(ViewPager)findViewById(R.id.view_pager);
	        int location=getIntent().getExtras().getInt("id");
	        adapter=new CustomSwipeAdapter(this,location);
	        viewPager.setAdapter(adapter);
			txt1=(TextView)findViewById(R.id.textView1);
	        txt2=(TextView)findViewById(R.id.textView2);
	        txt3=(TextView)findViewById(R.id.textView3);
	        txt4=(TextView)findViewById(R.id.textView4);
	        txt5=(TextView)findViewById(R.id.textView5);
	        txt6=(TextView)findViewById(R.id.textView6);
	        txt7=(TextView)findViewById(R.id.textView7);
	        txt8=(TextView)findViewById(R.id.textView8);
	        txt1.setText("Evin Bulunduðu Il:"+MainActivity.evList.get(location).getEvIl());
	        txt2.setText("Ev Emlak Tipi:"+MainActivity.evList.get(location).getEvEmlak());
	        txt3.setText("Ev Alaný(Metrekare)"+MainActivity.evList.get(location).getEvAlan());
	        txt4.setText("Evdeki Toplam Oda Sayýsý:"+MainActivity.evList.get(location).getOdaSayisi());
	        txt5.setText("Bina Yaþý:"+MainActivity.evList.get(location).getBinaYasi());
	        txt6.setText("Bulunduðu Kat:"+MainActivity.evList.get(location).getBulKat());
	        txt7.setText("Fiyatý:"+MainActivity.evList.get(location).getFiyat());
	        txt8.setText(""+MainActivity.evList.get(location).getEvAciklama()); 
	        intent=new Intent(this,BroadcastService.class);
				
	        
	       
	 }
	public void ata(){
		int kontrol=0,location=0;
		Bundle extras=getIntent().getExtras();
		if(evList.size()>0){
			for(int i=0;i<evList.size();i++){
				if(evList.get(i).getEvId()==extras.getInt("evId")){
					location=i;
					kontrol=1;
					adapter=new CustomSwipeAdapter(this,i);
					break;
				}
					
			}
			if(kontrol!=1){
				Intent i = new Intent(Bilgiler.this, MainActivity.class);
				startActivity(i);
			}
			
			viewPager.setAdapter(adapter);
	        txt1.setText("Evin Bulunduðu Il:"+evList.get(location).getEvIl());
	        txt2.setText("Ev Emlak Tipi:"+evList.get(location).getEvEmlak());
	        txt3.setText("Ev Alaný(Metrekare)"+evList.get(location).getEvAlan());
	        txt4.setText("Evdeki Toplam Oda Sayýsý::"+evList.get(location).getOdaSayisi());
	        txt5.setText("Bina Yaþý:"+evList.get(location).getBinaYasi());
	        txt6.setText("Bulunduðu Kat::"+evList.get(location).getBulKat());
	        txt7.setText("Fiyatý:"+evList.get(location).getFiyat());
	        txt8.setText(""+evList.get(location).getEvAciklama()); 
		}
		
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(broadcastReceiver);
		stopService(intent); 
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startService(intent);
		registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
	}
	
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	updateUI(intent);       
        }
    };  
    private void updateUI(Intent intent) {
    	evList.clear();
    
    	String dizi2;
    	dizi2=intent.getStringExtra("list");
    	try {
			JSONArray dizi=new JSONArray(dizi2);
			evList.clear();
			for(int i=0;i<dizi.length();i++){
				Ev evler=new Ev();
				
				evler.setEvId(dizi.getJSONObject(i).getInt("evId"));
				evler.setEvIl(dizi.getJSONObject(i).getString("evIl"));
				evler.setEvEmlak(dizi.getJSONObject(i).getString("evEmlak"));
				evler.setEvAlan(dizi.getJSONObject(i).getInt("evAlan"));
				evler.setOdaSayisi(dizi.getJSONObject(i).getInt("odaSayisi"));
				evler.setBinaYasi(dizi.getJSONObject(i).getInt("binaYasi"));
				evler.setBulKat(dizi.getJSONObject(i).getInt("bulKat"));
				evler.setFiyat(dizi.getJSONObject(i).getDouble("fiyat"));
                evler.setEvAciklama(dizi.getJSONObject(i).getString("evAciklama"));
                JSONArray temp=dizi.getJSONObject(i).getJSONArray("resim");
               for(int j=0;j<temp.length();j++){
            	   evler.setResim("http://192.168.43.69:8080/WebService"+temp.getString(j));
               }
                evList.add(evler);
                //Toast.makeText(getApplicationContext(),"boyut:"+"evid "+dizi.getJSONObject(i).getInt("evId") ,Toast.LENGTH_LONG).show();		
			}
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ata();
 	  
    }
}
