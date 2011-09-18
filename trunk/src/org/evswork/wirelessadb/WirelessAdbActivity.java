package org.evswork.wirelessadb;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WirelessAdbActivity extends Activity implements OnClickListener {
	
	Button refresh;
	Button roll;
	Button enable;
	Button disable;
	Button close;
	
	EditText port;
	TextView status;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bindView();
		onRefreshStatus();
		onClick(roll);
	}
	
	private void bindView() {
		refresh = (Button)findViewById(R.id.refresh);
		roll = (Button)findViewById(R.id.roll);
		enable = (Button)findViewById(R.id.enable);
		disable = (Button)findViewById(R.id.disable);
		close = (Button)findViewById(R.id.close);
		
		refresh.setOnClickListener(this);
		roll.setOnClickListener(this);
		enable.setOnClickListener(this);
		disable.setOnClickListener(this);
		close.setOnClickListener(this);
		
		port = (EditText)findViewById(R.id.port);
		status = (TextView)findViewById(R.id.status);
		
	}

	private void onRefreshStatus() {
		int port = new PortGetter().get();
		status.setText(
			String.valueOf(port) + 
			( port == -1 ? "(disabled)" : "(enabled)")
		);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.refresh:
			onRefreshStatus();
			break;
		case R.id.roll:
			port.setText(String.valueOf(		
				new Random().nextInt(65536-1024)+1024 /* 1024-65535 */
			));
			break;
		case R.id.enable:
			new PortSetter(Integer.valueOf(port.getText().toString())).set();
			onRefreshStatus();
			break;
		case R.id.disable:
			new PortSetter(-1).set();
			onRefreshStatus();
			break;
		case R.id.close:
			finish();
			break;
		}
	}
}