package org.conrogatio.libs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.LicenseChecker;
import com.google.android.vending.licensing.LicenseCheckerCallback;
import com.google.android.vending.licensing.ServerManagedPolicy;

public class LicenceHandler {
	private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
		@Override
		public void allow(int reason) {
			licence = reason;
			licensed = true;
			Toast.makeText(c, "Your licence is valid", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void applicationError(int reason) {
			licence = reason;
			licensed = true;
			Log.i("License", "Error: " + reason);
			// showDialog(0);
		}

		@Override
		public void dontAllow(int reason) {
			licence = reason;
			licensed = false;
			Toast.makeText(c, "Your licence is not valid", Toast.LENGTH_SHORT).show();
			Log.i("License", "Reason for denial: " + reason);
			// showDialog(0);
		}
	}

	public int licence;
	private Handler mHandler;
	private LicenseChecker mChecker;
	private LicenseCheckerCallback mLicenseCheckerCallback;
	private boolean licensed = false;
	private boolean checking = false;
	private Context c;

	public LicenceHandler(String BASE64_PUBLIC_KEY, byte[] SALT, Context context) {
		c = context;
		String deviceId = Secure.getString(c.getContentResolver(), Secure.ANDROID_ID);
		mHandler = new Handler();
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();
		mChecker = new LicenseChecker(c, new ServerManagedPolicy(c, new AESObfuscator(SALT, c.getPackageName(),
				deviceId)), BASE64_PUBLIC_KEY);
		startCheck();
	}

	public AlertDialog getDialog(int id) {
		return new AlertDialog.Builder(c).setTitle("Application not licensed")
				.setMessage("This application is not licensed, please install it from the Play Store to use it.")
				.setPositiveButton("Play Store listing", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri
								.parse("https://play.google.com/store/apps/details?id=" + c.getPackageName()));
						c.startActivity(marketIntent);
						// c.finish();
					}
				}).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// finish();
					}
				}).setNeutralButton("Re-Check", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startCheck();
					}
				}).setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener() {
					@Override
					public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
						Log.i("License", "Key Listener");
						// finish();
						return true;
					}
				}).create();
	}

	public int getReason() {
		return licence;
	}

	public boolean isChecking() {
		return checking;
	}

	public boolean licensed() {
		return licensed;
	}

	public void startCheck() {
		mChecker.checkAccess(mLicenseCheckerCallback);
		checking = true;
	}
}
