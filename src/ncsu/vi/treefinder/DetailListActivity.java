package ncsu.vi.treefinder;



import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ncsu.vi.treefinder.db.TreeFinderDataSource;
import ncsu.vi.treefinder.model.TreeItem;

public class DetailListActivity extends ListActivity {

	private static final String LOGTAG = "TREEFINDER";		
	TreeFinderDataSource datasource ;
	
	private List<TreeItem> treeitems;
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		//String pid = getIntent().getExtras().getString("parentid");
		String pid = "3";		
		
		Log.i(LOGTAG, "Parent ID : " + pid);
		Log.i(LOGTAG, "Detail List Activity onCreate method");
		
		datasource = new TreeFinderDataSource(this);
		datasource.open();
		
		Log.i(LOGTAG, "before fetching children");
		
		//LinearLayout linearlayout =  (LinearLayout) findViewById(R.id.linearlayout2);
		treeitems = datasource.findChildren(pid);

		refreshDisplay();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.item2 || id == R.id.action_settings) {
			Intent intent = new Intent (DetailListActivity.this , MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public void refreshDisplay() {
		//ListView myListView = (ListView) findViewById(R.id.listview);

		// get data from the table by the ListAdapter
		TreeItemAdapter adapter = new TreeItemAdapter(this,treeitems);
		//ArrayAdapter<TreeItem> adapter = new TreeItemAdapter(this, treeitems);
		setListAdapter(adapter);
		Log.i(LOGTAG, "Done setting Adapter ");
	}



}

