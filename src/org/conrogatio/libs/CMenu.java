package org.conrogatio.libs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class CMenu extends Activity {
	public TabHost th;
	public boolean dialogOpen = false;
	
	public CMenu(int tabs, TabHost tabHost, int tab) {
		th = tabHost;
		th.setup();
		for (int i = 0; i < tabs; i++) {
			TabSpec menuSpecs = th.newTabSpec("tag" + i);
			menuSpecs.setContent(tab);
			menuSpecs.setIndicator("Tab " + i);
			th.addTab(menuSpecs);
		}
	}
	
	// Exit dialog
	public Builder exitDialog(String title, String message, String cancel,
			String quit) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(cancel, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		builder.setNegativeButton(quit, new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				CMenu.super.onBackPressed();
				System.exit(0);
			}
		});
		return builder;
	}
	
	public Builder insertDialog(String title, String message, String confirm,
			String cancel, String defaultText) {
		// String reg = "^[a-zA-Z0-9]*$";
		dialogOpen = true;
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title);
		alert.setMessage(message);
		final EditText input = new EditText(this);
		input.setText(defaultText);
		alert.setView(input);
		alert.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
				String givenText = input.getText().toString();
				if (givenText.matches("^[a-zA-Z0-9]*$") && !givenText.isEmpty()
						&& givenText.length() <= 12) {
					Log.i("ALERT", "Store username -" + givenText);
				} else {
					Toast.makeText(CMenu.this,
							"The name was not valid and was not changed",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		alert.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		});
		return alert;
	}
	
	// Other
	public void showTab(int tabIndex) {
		th.setCurrentTab(tabIndex);
	}
	
	// Welcome dialog
	public Builder welcomeDialog(String message) {
		Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		builder.setTitle("Welcome to Spaceshooter Zero!");
		builder.setMessage(message);
		builder.setNegativeButton("Thank you!", new OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		return builder;
	}
}