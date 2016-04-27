package ncsu.vi.treefinder.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ncsu.vi.treefinder.model.TreeItem;
import ncsu.vi.treefinder.model.TreeRelation;


public class TreeFinderDataSource {
	
	//private static final String LOGTAG = "TREEFINDER";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
			TreeFinderDBOpenHelper.COLUMN_TREEITEM_ID ,
			TreeFinderDBOpenHelper.COLUMN_IMAGEURL ,
			TreeFinderDBOpenHelper.COLUMN_ITEM_TYPE	,
			TreeFinderDBOpenHelper.COLUMN_DESCRIPTION	,
			TreeFinderDBOpenHelper.COLUMN_QUESTION
	};

	public TreeFinderDataSource(Context context){
		
		dbhelper = new TreeFinderDBOpenHelper(context);
		database = dbhelper.getWritableDatabase();		
	}
	
	public TreeItem createTreeItem(TreeItem treeitem){
		ContentValues values= new ContentValues();
		//Log.i(LOGTAG, Long.toString(treeitem.getId()));
		values.put(TreeFinderDBOpenHelper.COLUMN_TREEITEM_ID, treeitem.getId());
		values.put(TreeFinderDBOpenHelper.COLUMN_IMAGEURL, treeitem.getImageUrl());
		values.put(TreeFinderDBOpenHelper.COLUMN_ITEM_TYPE, treeitem.getItemType());
		values.put(TreeFinderDBOpenHelper.COLUMN_DESCRIPTION, treeitem.getDescription());
		values.put(TreeFinderDBOpenHelper.COLUMN_QUESTION, treeitem.getQuestion());		
		database.insert(TreeFinderDBOpenHelper.TABLE_TREEITEM , null ,values);				
		return treeitem ;
		
	}
	
	public TreeRelation createTreeRelation(TreeRelation treerelation){
		ContentValues values= new ContentValues();
		values.put(TreeFinderDBOpenHelper.COLUMN_PARENT_ID, treerelation.getParentId());
		values.put(TreeFinderDBOpenHelper.COLUMN_CHILD_ID, treerelation.getChildId());
		long insertid = database.insert(TreeFinderDBOpenHelper.TABLE_RELATIONS , null ,values);
		treerelation.setId(insertid);
		return treerelation ;
		
	}
	
	public List<TreeItem> findChildren(String parentid){
		List<TreeItem> titems = new ArrayList<TreeItem>();	
		//Log.i(LOGTAG, "before creating sursor");
		//Log.i(LOGTAG, "Parent ID : " + parentid);
		
		Cursor cursor = database.rawQuery(
				"SELECT " +
				TreeFinderDBOpenHelper.COLUMN_TREEITEM_ID + " , " +
				TreeFinderDBOpenHelper.COLUMN_IMAGEURL + " , " +
				TreeFinderDBOpenHelper.COLUMN_ITEM_TYPE + " , " +
				TreeFinderDBOpenHelper.COLUMN_DESCRIPTION + " , " +
				TreeFinderDBOpenHelper.COLUMN_QUESTION + " " +
				" FROM  treeItem  WHERE treeItemId IN (SELECT childId FROM treeRelations WHERE parentId = ?)" ,				
				new String[] {parentid});
		//Log.i(LOGTAG,"Number of children : " +  Integer.toString(cursor.getCount()));
		//Log.i(LOGTAG, "Cursor successfully created");
		
		if (cursor.getCount() > 0) {
		while (cursor.moveToNext()) {
			TreeItem t = new TreeItem();
			t.setId(cursor.getLong(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_TREEITEM_ID)));
			t.setImageUrl(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_IMAGEURL)));
			t.setItemType(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_ITEM_TYPE)));
			t.setDescription(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_DESCRIPTION)));
			t.setQuestion(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_QUESTION)));
			//Log.d(LOGTAG, Long.toString(t.getId()));
			//Log.d(LOGTAG, t.getImageUrl());
			//Log.d(LOGTAG, t.getItemType());	
			//Log.d(LOGTAG, t.getDescription());	
			//Log.d(LOGTAG, t.getQuestion());	
			titems.add(t);
			}
		}
		//Log.i(LOGTAG, "Size of iitems list " + Integer.toString(titems.size()));
		
		return titems;
	}
	
	
	public List<TreeItem> findAll() {
		List<TreeItem> titems = new ArrayList<TreeItem>();
		
		Cursor cursor = database.query(TreeFinderDBOpenHelper.TABLE_TREEITEM, allColumns, 
				null, null, null, null, null);
				
		//Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				TreeItem t = new TreeItem();
				t.setId(cursor.getLong(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_TREEITEM_ID)));
				t.setImageUrl(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_IMAGEURL)));
				t.setItemType(cursor.getString(cursor.getColumnIndex(TreeFinderDBOpenHelper.COLUMN_ITEM_TYPE)));				
				titems.add(t);
			}
		}
		return titems;
	}
	
	public void open(){
		//Log.i(LOGTAG, "Database Open");
		database = dbhelper.getWritableDatabase();	
	}

	public void close(){
		//Log.i(LOGTAG, "Database Closed");
		dbhelper.close();
	}
}
