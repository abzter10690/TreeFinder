package ncsu.vi.treefinder;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import ncsu.vi.treefinder.db.TreeFinderDataSource;
import ncsu.vi.treefinder.model.TreeItem;
import ncsu.vi.treefinder.model.TreeRelation;
import ncsu.vi.treefinder.xml.TreeItemPullParser;
import ncsu.vi.treefinder.xml.TreeRelationPullParser;

public class MainActivity extends Activity {
	
	//private static final String LOGTAG = "TREEFINDER";
	
	TreeFinderDataSource datasource ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);			
		
		datasource = new TreeFinderDataSource(this);
		datasource.open();
		
		List<TreeItem> treeitems = datasource.findAll();
		//To avoid loading from XML to SQLIte db again
		if(treeitems.size()==0){
			createData();
			treeitems = datasource.findAll();
		}
		
		TextView textview = (TextView) findViewById(R.id.text1);
		Typeface tf = Typeface.createFromAsset(getAssets(),"typogarden.ttf");  
		textview.setTypeface(tf);

		
	}
	
	// This method gets invoked when you touch anywhere on the "What leaf do you have screen " .
	//Check in activity_detail_2.xml for declaration.
	public void goToActivity(View v){
		Intent intent = new Intent(MainActivity.this , DetailActivity.class);
    	intent.putExtra("parentid", "1");
    	intent.putExtra("question", "What kind of leaf do you have ?");
		startActivity(intent);
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
		if (id == R.id.item2) {			
			return true;
		}
		return super.onOptionsItemSelected(item);		
	}

	
	@Override
	protected void onResume(){
		super.onResume();
		datasource.open();
	}
	
	
	@Override
	protected void onPause() {		 
		super.onPause();
		datasource.close();
	}
	
	public void createData(){
		TreeItemPullParser parser = new TreeItemPullParser();
		List<TreeItem> treeItems = parser.parseXML(this);
		
		for (TreeItem treeItem : treeItems) {
			datasource.createTreeItem(treeItem);
		}
		
		//Log.d(LOGTAG, "Imported TreeItem Data into database");
		TreeRelationPullParser parser2 = new TreeRelationPullParser();
		List<TreeRelation> treeRelations = parser2.parseXML(this);
		
		for (TreeRelation treeRelation : treeRelations) {
			datasource.createTreeRelation(treeRelation);
		}
		//Log.d(LOGTAG, "Imported TreeRelations Data into database");
	}
}
