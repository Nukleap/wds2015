package com.ida.wds2015;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils.TruncateAt;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.ida.wds2015.classes.Constants;

public class MainActivityCommon extends AppCompatActivity {
	Toolbar toolbar;
	ShareActionProvider mShareActionProvider;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	protected void setMaterialDesign() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar()
				.setIcon(
						getResources().getDrawable(
								R.drawable.ic_launcher_splash_white));

	}

	protected void gotoRegister() {
		Intent intent = new Intent(this, ActivityRegistrationWizard1.class);
		startActivity(intent);
	}

	protected void showMsg(String text) {
		Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
	}

	protected void gotoFeeSelection() {
		if (Globals.userfees != null) {
			Intent intent = new Intent(this, ActivityRegistrationWizard2.class);
			startActivity(intent);
		}
	}

	protected void setMaterialTrans() {
		toolbar.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		toolbar.bringToFront();
	}

	protected void gotoNews() {
		if (Globals.news != null) {
			if (Globals.news.size() > 0) {
				Intent intent = new Intent(this, ActivityNewsList.class);
				startActivity(intent);
			}
		}
	}

	protected void setMarqueeTitle(String title) {
		TextView tv = ((TextView) findViewById(R.id.textview_marquee));
		tv.setText(title);
		tv.setSelected(true);
		tv.setEllipsize(TruncateAt.MARQUEE);
		tv.setSingleLine(true);
	}

	protected void setMarquee(TextView tv) {
		tv.setSelected(true);
		tv.setEllipsize(TruncateAt.MARQUEE);
		tv.setSingleLine(true);
	}

	protected void setBackTrans() {
		toolbar.setBackgroundColor(getResources().getColor(R.color.Trans));
	}

	protected void setMaterialTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	protected void setBackAsClose() {
		toolbar.setNavigationIcon(R.drawable.ic_action_cancel);
	}

	protected Integer getAverageColor(Bitmap image) {

		if (null == image)
			return Color.GRAY;
		else {

			int pixelCount = image.getWidth() * image.getHeight();
			int red, green, blue;
			red = green = blue = 0;

			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight(); j++) {
					int pixel = image.getPixel(i, j);

					red += Color.red(pixel);
					green += Color.green(pixel);
					blue += Color.blue(pixel);
				}
			}
			red /= pixelCount;
			green /= pixelCount;
			blue /= pixelCount;
			int c = (red + green + blue) / 3;
			// return Color.rgb(255-red, 255-green, 255-blue);
			return Color.rgb(255 - c, 255 - c, 255 - c);
		}
	}

	protected void showBack() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			setResult(RESULT_CANCELED);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// Get the menu item.
		MenuItem shareItem = menu.findItem(R.id.action_share);
		if (shareItem != null) {
			mShareActionProvider = (ShareActionProvider) MenuItemCompat
					.getActionProvider(shareItem);
			mShareActionProvider.setShareIntent(getDefaultIntent());
		}
		return super.onCreateOptionsMenu(menu);
	}

	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("image/*");
		intent.putExtra(
				Intent.EXTRA_TEXT,
				"Welcome to WDS \n 16-18 Oct 2015,Mumbai\nAvailable on Google Play Store\nhttps://play.google.com/store/apps/details?id=com.ida.wds2015&hl=en");
		intent.setType("text/plain");
		return intent;
	}

	protected String makeLower(String text) {
		return text.toLowerCase(Locale.getDefault());
	}

	protected void gotoProgrammeList(String name) {
		Intent pintent = new Intent(this, ActivityProgrammeList.class);
		pintent.putExtra("title", "" + name);
		startActivity(pintent);
	}

	protected void gotoSpeaker(String name) {
		Intent pintent = new Intent(this, ActivitySpeakerList.class);
		pintent.putExtra("title", "" + name);
		startActivity(pintent);
	}

	protected void gotoSpeakerDetais() {
		Intent pintent = new Intent(this, ActivitySpeakerDetails.class);
		startActivity(pintent);
	}

	public void gotoVoteDetails() {
		Intent intent = new Intent(this, ActivityPosterAbstractDetails.class);
		startActivity(intent);
	}

	public void gotoMakeVote() {
		Intent intent = new Intent(this, ActivityVoteFillDetails.class);
		startActivity(intent);
	}

	protected String isNull(Object obj) {
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}

	protected String makeMarquee(String text) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			sb.append(text);
			sb.append("       ");
		}
		return sb.toString();
	}

	protected void gotoCall(String number) {
		try {
			if (number.length() <= 0) {
				return;
			}
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + number));
			callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(callIntent);
		} catch (Exception e) {

		}
	}

	protected void gotoEmail(String email) {
		try {
			if (email.length() <= 0) {
				return;
			}
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("plain/text");
			intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
			intent.putExtra(Intent.EXTRA_SUBJECT, "Need Help?");
			intent.putExtra(Intent.EXTRA_TEXT, "Any Relavant Information?");
			startActivity(Intent.createChooser(intent, ""));
		} catch (Exception e) {
		}
	}

	public void animate(View v, Techniques tech) {
		YoYo.with(tech).duration(Constants.INTERVAL).playOn(v);
	}

	public static void makeAnimate(View v, Techniques tech) {
		YoYo.with(tech).duration(Constants.INTERVAL).playOn(v);
	}

	protected boolean isValidMail(String email) {
		// boolean check;
		// Pattern p;
		// Matcher m;
		// String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		// + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		// p = Pattern.compile(EMAIL_STRING);
		// m = p.matcher(email2);
		// check = m.matches();
		// return check;
		if (email.length() <= 0) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
		}

	}

	protected boolean isValidMobile(String phone) {
		// boolean check;
		// if(phone2.length() < 6 || phone2.length() > 13)
		// {
		// check = false;
		// }
		// else
		// {
		// check = true;
		// }
		// return check;
		if(phone.length()<=0){
			return false;
		}else{
			return android.util.Patterns.PHONE.matcher(phone).matches();
		}
	}

}
