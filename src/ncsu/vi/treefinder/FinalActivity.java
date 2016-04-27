package ncsu.vi.treefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);
		
		String imagelink = getIntent().getExtras().getString("imagelink");
		String description = getIntent().getExtras().getString("description");
		String treename = getIntent().getExtras().getString("question");
		
		ImageView imgview=  (ImageView) findViewById(R.id.imageView1);																		
		downloadImage(imgview, imagelink);
		
		TextView tv1 = (TextView) findViewById(R.id.text1);
		tv1.setText(treename);
		
		TextView tv2 = (TextView) findViewById(R.id.text2);
		tv2.setText(description);		
		
		Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FinalActivity.this , DetailActivity.class);
				intent.putExtra("parentid", "1");
				intent.putExtra("question", "What kind of leaf do you have ?");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
	}
	
	public void downloadImage(View view , String imgurl){
		new DownLoadImageTask((ImageView) view)
		.execute(imgurl);
	}
}


