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
import ncsu.vi.treefinder.model.TreeRelation;

public class TreeRelationPullParser {
private static final String LOGTAG = "TREEFINDER";
	
	private static final String TREERELATION_ID= "treeRelationId";
	private static final String TREERELATION_PARENTID= "parentId" ;
	private static final String TREERELATION_CHILDID= "childId";
	
	private TreeRelation currentTreeRelation = null ;
	private String currentTag = null ;
	List<TreeRelation> treeRelations = new ArrayList<TreeRelation>();

	public List<TreeRelation> parseXML(Context context){
		
		try{
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			
			int rid = context.getResources().getIdentifier("ncsu.vi.treefinder:raw/treerelations", null, null);
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
		
		return treeRelations ;
	}
	
	private void handleText(String text){
		String xmlText = text;
		Long value ;
		if(currentTreeRelation!= null && currentTag!= null){
			if(currentTag.equals(TREERELATION_ID)){
				value =  Long.parseLong(xmlText);
				currentTreeRelation.setId(value);
			}		
			else if (currentTag.equals(TREERELATION_PARENTID)){
				value =  Long.parseLong(xmlText);				
				currentTreeRelation.setParentId(value);
			}
			else if(currentTag.equals(TREERELATION_CHILDID)) {
				value =  Long.parseLong(xmlText);
				currentTreeRelation.setChildId(value);
			}			
		}
		
		
	}
	
	private void handleStartTag(String name){
		if(name.equals("treeRelation")){
			currentTreeRelation = new TreeRelation();
			treeRelations.add(currentTreeRelation);
		}
		else{
			currentTag = name;
		}
			
	}

}
