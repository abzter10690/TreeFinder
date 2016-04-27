package ncsu.vi.treefinder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//import android.R;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import ncsu.vi.treefinder.model.TreeItem;

public class TreeItemPullParser {
	
	private static final String LOGTAG = "TREEFINDER";
	
	private static final String TREEITEM_ID= "treeItemId";
	private static final String TREEITEM_IMAGEURL= "imageUrl" ;
	private static final String TREEITEM_ITEMTYPE= "itemType";
	private static final String TREEITEM_DESCRIPTION= "treeDesc";
	private static final String TREEITEM_QUESTION= "treeQues";
	
	private TreeItem currentTreeItem = null ;
	private String currentTag = null ;
	List<TreeItem> treeItems = new ArrayList<TreeItem>();

	public List<TreeItem> parseXML(Context context){
		
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			int rid = context.getResources().getIdentifier("ncsu.vi.treefinder:raw/treeitems", null, null);
			//Log.d(LOGTAG, Integer.toString(rid));
			InputStream stream = context.getResources().openRawResource(rid);
			xpp.setInput(stream, null);
			
			int eventType = xpp.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				if(eventType == XmlPullParser.START_TAG){
					handleStartTag(xpp.getName());					
				}else if (eventType == XmlPullParser.END_TAG){
					currentTag = null;
				}else if(eventType == XmlPullParser.TEXT){
					handleText(xpp.getText());
				}
				eventType = xpp.next();
			}
			
			
		}
		catch(NotFoundException e){
			Log.d(LOGTAG, e.getMessage());
		}
		catch(XmlPullParserException e){
			Log.d(LOGTAG, e.getMessage());
		}
		catch(IOException e){
			Log.d(LOGTAG, e.getMessage());
		}
		
		return treeItems;
	}
	
	private void handleText(String text){
		String xmlText = text;
		if(currentTreeItem!= null && currentTag!= null){
			if(currentTag.equals(TREEITEM_ID)){
				Integer id =  Integer.parseInt(xmlText);
				currentTreeItem.setId(id);
			}		
			else if (currentTag.equals(TREEITEM_IMAGEURL)){
				currentTreeItem.setImageUrl(xmlText);
			}
			else if(currentTag.equals(TREEITEM_ITEMTYPE)) {
				currentTreeItem.setItemType(xmlText);
			}	
			else if(currentTag.equals(TREEITEM_DESCRIPTION)) {
				currentTreeItem.setDescription(xmlText);
			}
			else if(currentTag.equals(TREEITEM_QUESTION)) {
				currentTreeItem.setQuestion(xmlText);
			}
		}
		
		
	}
	
	private void handleStartTag(String name){
		if(name.equals("treeItem")){
			currentTreeItem = new TreeItem();
			treeItems.add(currentTreeItem);
		}
		else{
			currentTag = name;
		}
			
	}

}
