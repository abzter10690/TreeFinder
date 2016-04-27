package ncsu.vi.treefinder.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TreeFinderDBOpenHelper extends SQLiteOpenHelper {
	
	
	//private static final String LOGTAG = "TREEFINDER";
	
	private static final String DATABASE_NAME = "treefinder.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_TREEITEM = "treeItem";
	public static final String COLUMN_TREEITEM_ID = "treeItemId";
	public static final String COLUMN_IMAGEURL = "imageUrl";
	public static final String COLUMN_ITEM_TYPE = "itemType";
	public static final String COLUMN_DESCRIPTION = "treeDesc";
	public static final String COLUMN_QUESTION = "treeQues";
	
	public static final String TABLE_RELATIONS = "treeRelations";
	public static final String COLUMN_RELATION_ID = "relationId";
	public static final String COLUMN_PARENT_ID = "parentID";
	public static final String COLUMN_CHILD_ID = "childID";
	
	private static final String TABLE_CREATE_TREEITEM =
			"CREATE TABLE " + TABLE_TREEITEM + " ( " + 
			COLUMN_TREEITEM_ID +" INTEGER PRIMARY KEY , " +
			COLUMN_IMAGEURL + " TEXT, " +
			COLUMN_ITEM_TYPE + " TEXT , " +
			COLUMN_DESCRIPTION + " TEXT, " +
			COLUMN_QUESTION + " TEXT " +
			" ) " ;
	
	private static final String TABLE_CREATE_RELATIONS =
			"CREATE TABLE " + TABLE_RELATIONS + " ( " + 
			COLUMN_RELATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_PARENT_ID+ " TEXT, " +
			COLUMN_CHILD_ID+ " TEXT " +
			")" ;
	
	
	public TreeFinderDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null , DATABASE_VERSION );
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_CREATE_TREEITEM);
		//Log.i(LOGTAG, "Table TreeItem created");
		db.execSQL(TABLE_CREATE_RELATIONS);
		//Log.i(LOGTAG, "Table TreeRelations created");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_CREATE_RELATIONS);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_CREATE_TREEITEM);
		onCreate(db);

	}

}
