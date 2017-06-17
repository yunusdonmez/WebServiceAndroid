package com.genesis.yazlab;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

	ListView liste;
	   Timer timer;
	   public static List<Ev> evList=new ArrayList<Ev>();
	   private Intent intent;
	   
	   
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        liste=(ListView)findViewById(R.id.listView1);
	        //resim=(ImageView)findViewById(R.id.imageView1);
	        intent=new Intent(this,BroadcastService.class);
				// TODO Auto-generated method stub
	        	
			liste.setOnItemClickListener(new OnItemClickListener() {
		
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub

				
			        
					Intent i = new Intent(MainActivity.this, Bilgiler.class);
					i.putExtra("evId",evList.get(position).getEvId());
					i.putExtra("id",position);
					startActivity(i);
				}
				
			});
	    }
	    
	    
	    @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			startService(intent);
			registerReceiver(broadcastReceiver, new IntentFilter(BroadcastService.BROADCAST_ACTION));
		}
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			unregisterReceiver(broadcastReceiver);
			stopService(intent); 
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
			
	 	  liste.setAdapter(null);
	    	liste.setAdapter(new OzelAdapter(MainActivity.this,evList));
	    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
