package ncsu.vi.treefinder;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ncsu.vi.treefinder.model.TreeItem;

public class TreeItemAdapter extends ArrayAdapter<TreeItem> {
	Context context;
	List<TreeItem> treeitems;
	private static final String LOGTAG = "TREEFINDER";
	
	public TreeItemAdapter(Context context, List<TreeItem> treeItems) {
		super(context, android.R.id.content, treeItems);
		this.context = context;
		this.treeitems = treeItems;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); 
        View view = vi.inflate(R.layout.tree_item, null);
        ViewHolder holder = new ViewHolder();
        view.setTag(holder);
        
        TreeItem treeitem = treeitems.get(position);
        
        holder.text = (TextView) view.findViewById(R.id.descText);
        holder.text.setText(treeitem.getDescription());
        
        holder.image = (ImageView) view.findViewById(R.id.imageView1);
        downloadImage(holder.image, treeitem.getImageUrl());
        holder.image.setAdjustViewBounds(true);		
		holder.image.setOnClickListener(new OnClickListener() {
		    public void onClick(View v)
		    {
		    	Intent intent = new Intent(context , DetailListActivity.class);
		    	//intent.putExtra("parentid", treeitem.getId() );
				context.startActivity(intent);				
		    } 
		});
		Log.i(LOGTAG, "Created view of listview");
        
        return view;
	}
	
	public void downloadImage(View view , String imgurl){
		new DownLoadImageTask((ImageView) view)
		.execute(imgurl);
	}
	
	static class ViewHolder {
		  TextView text;		  
		  ImageView image;
		  
		}
}