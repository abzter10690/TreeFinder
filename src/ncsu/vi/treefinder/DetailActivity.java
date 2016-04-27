package ncsu.vi.treefinder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ncsu.vi.treefinder.db.TreeFinderDataSource;
import ncsu.vi.treefinder.model.TreeItem;

public class DetailActivity extends Activity {

	private static final String LOGTAG = "TREEFINDER";		
	TreeFinderDataSource datasource ;
	String Question = "What Kind Of Leaf  do you have ?";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_2);
				
		String pid = getIntent().getExtras().getString("parentid");
		String ques = getIntent().getExtras().getString("question");
		
		Log.i(LOGTAG, "Parent ID : " + pid);
		Log.i(LOGTAG, "Detail Activity onCreate method");
		
		datasource = new TreeFinderDataSource(this);
		datasource.open();
		
		Log.i(LOGTAG, "before fetching children");
		
		LinearLayout linearlayout =  (LinearLayout) findViewById(R.id.linearlayout2);//change linear layout
		TextView tv = (TextView) findViewById(R.id.textview2);	
		tv.setText(ques);		
			
		
		List<TreeItem> treeitems = datasource.findChildren(pid);
		int index = 0;
		//Log.i(LOGTAG, Integer.toString(treeitems.size()));
		if(treeitems.size() > 0){
			Log.i(LOGTAG, "inside if statment");
			for (TreeItem titem : treeitems) {
				Log.i(LOGTAG, titem.getItemType());
				if(titem.getItemType().equals("I")){		
					
				final String parentid = Long.toString(titem.getId());
				final String question = titem.getQuestion();
				
				ImageView imgview =  new ImageView(this);
				TextView textview = new TextView(this);
				TextView textview2 = new TextView(this);
				
				textview2.setBackgroundColor(Color.parseColor("#00000000"));				
				
				textview.setText(titem.getDescription());
				textview.setTextIsSelectable(true);
				textview.setBackgroundColor(getResources().getColor(R.color.green));
				textview.setGravity(Gravity.CENTER);
				textview.setTextColor(Color.parseColor("#FFFFFF"));
				textview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
							
				downloadImage(imgview, titem.getImageUrl());				
				imgview.setAdjustViewBounds(true);
				imgview.setBackground(getResources().getDrawable(R.drawable.image_border));				
				imgview.setOnClickListener(new OnClickListener() {
				    public void onClick(View v)
				    {
				    	Intent intent = new Intent(DetailActivity.this , DetailActivity.class);
				    	intent.putExtra("parentid", parentid);
				    	intent.putExtra("question", question);
						startActivity(intent);				
				    } 
				});
				
				linearlayout.addView(imgview , index++);
				linearlayout.addView(textview , index++);
				linearlayout.addView(textview2 , index++);
				}
				else if(titem.getItemType().equals("L")) {
					final String imagelink = titem.getImageUrl();
					final String description = titem.getDescription();
					final String question = titem.getQuestion();
					Intent intent2 = new Intent(DetailActivity.this , FinalActivity.class);
					intent2.putExtra("imagelink", imagelink );
					intent2.putExtra("description", description);
					//The question for last node that is tree is actually the Name of the tree.
					intent2.putExtra("question", question);
					startActivity(intent2);
				}
			}
		}		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item2 || id == R.id.action_settings) {
			Intent intent = new Intent (DetailActivity.this , MainActivity.class);
			//Removes all the previous activities of the application
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//This method calls Asynchronous download image task in background without freezing the UI.
	public void downloadImage(View view , String imgurl){
		new DownLoadImageTask((ImageView) view)
		.execute(imgurl);
	}
	
}
