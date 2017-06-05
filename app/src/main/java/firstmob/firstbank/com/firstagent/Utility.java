package firstmob.firstbank.com.firstagent;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which has Utility methods
 * 
 */
public class Utility {
	private static Pattern pattern;
	private static Matcher matcher;
	//Email Pattern
	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String NUMBER_PATTERN ="[0-9]+";
	private static final String LWCASE_PATTERN ="[a-z]+";
	private static final String SPEC_CHARPATTERN ="[a-zA-Z0-9]+";
	private static SessionManagement session;
	/**
	 * Validate Email with regular expression
	 * 
	 * @param email
	 * @return true for Valid Email and false for Invalid Email
	 */
	public static boolean validate(String email) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
 
	}

	public static boolean isValidWord(String str) {
//  return str.matches(".*[a-zA-Z]+.*"); // match a number with optional
		// '-' and decimal.
		return str.matches("^[a-zA-Z0-9]*$"); // match a number with optional
	}
	public static boolean isValidWordHyphen(String str) {
//  return str.matches(".*[a-zA-Z]+.*"); // match a number with optional
		// '-' and decimal.
		return str.matches("^[a-zA-Z0-9_]*$"); // match a number with optional
	}
	public static boolean isValidWordSpace(String str) {
//  return str.matches(".*[a-zA-Z]+.*"); // match a number with optional
		// '-' and decimal.
		return str.matches("^[a-zA-Z0-9 ]*$"); // match a number with optional
	}
	public static boolean isValidWordStudId(String str) {
//  return str.matches(".*[a-zA-Z]+.*"); // match a number with optional
		// '-' and decimal.

		return str.matches("^[a-zA-Z0-9-|/\\\\_]*$"); // match a number with optional
	}
	public static boolean checknum(String num) {
		pattern = Pattern.compile(NUMBER_PATTERN);
		matcher = pattern.matcher(num);
		return matcher.matches();
 
	}
	public static boolean checklwcase(String tstcase) {
		pattern = Pattern.compile(LWCASE_PATTERN);
		matcher = pattern.matcher(tstcase);
		return matcher.matches();
 
	}
    public static boolean checkemail(String tstcase) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(tstcase);
        return matcher.matches();

    }
	/**
	 * Checks for Null String object
	 * 
	 * @param txt
	 * @return true for not null and false for null String object
	 */
	public static boolean isNotNull(String txt){
		return txt!=null && txt.trim().length()>0 ? true: false;
	}
	public static String MaskAccNo(String pan){

		char[] myNameChars = pan.toCharArray();
		myNameChars[6] = 'x';
		myNameChars[7] = 'x';
		myNameChars[8] = 'x';
		myNameChars[9] = 'x';
		myNameChars[10] = 'x';
		myNameChars[11] = 'x';
		String newpan = String.valueOf(myNameChars);
		return newpan;
	}
	public static boolean isEmulator() {
		return Build.FINGERPRINT.startsWith("generic")
				|| Build.FINGERPRINT.startsWith("unknown")
				|| Build.MODEL.contains("google_sdk")
				|| Build.MODEL.contains("Emulator")
				|| Build.MODEL.contains("Android SDK built for x86")
				|| Build.MANUFACTURER.contains("Genymotion")
				|| (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
				|| "google_sdk".equals(Build.PRODUCT);
	}
	public static int containerHeight(MainActivity ba) {
		DisplayMetrics dm = new DisplayMetrics();
		ba.getWindowManager().getDefaultDisplay().getMetrics(dm);
		//Toast.makeText(ba.getApplicationContext(), "Screen Height of " + Build.MANUFACTURER + "  is "+Integer.toString(dm.heightPixels) , Toast.LENGTH_LONG).show();

		//get predefined value
		double ratio = 15;

		//check if there is cached value; using SnappyDB


		return (int) (dm.heightPixels / ratio);
	}

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
public static String returnNumberFormat(String amount){
	String  fmamo = "0.00";
	try {
		Double amou = Double.parseDouble(amount);
		if(amou > 0) {

			DecimalFormat df = new DecimalFormat("#,###.00");

			fmamo = df.format(amou);
		}

	}catch (Exception e){
		e.printStackTrace();
	}
	return  fmamo;
}
	public static  boolean checkInternetConnection(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null
				&& cm.getActiveNetworkInfo().isAvailable()
				&& cm.getActiveNetworkInfo().isConnected()) {

			return true;
		} else {

			Toast.makeText(
					c,
					"No Internet Connection. Please check your internet settings",
					Toast.LENGTH_LONG).show();
			return false;
		}
	}

	public static void hideKeyboardFrom(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	public static String getDevImei(Context c){
		TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(c.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		return imei;
	}
	public static String getMacAddress(Context c){
		WifiManager wifiManager = (WifiManager) c.getSystemService(c.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		String mac = wInfo.getMacAddress();
		if(mac == null || mac.equals("")){
			mac = "0.0.0.0";
		}
		return convertStringFromNull(mac);
	}
	public static String getIP(Context c){
		WifiManager wifiManager = (WifiManager) c.getSystemService(c.WIFI_SERVICE);

		String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
		if(ip == null || ip.equals("")){
			ip = "0.0.0.0";
		}
		return convertStringFromNull(ip);
	}

	public static String getSerial(){
		String build = Build.SERIAL;
		return build;
	}

	public static String getDevVersion(){
		int bld = Build.VERSION.SDK_INT;
		String blid = Integer.toString(bld);
		return convertStringFromNull(blid);
	}

	public static String getDevModel(){
		String myDeviceModel = android.os.Build.MODEL;
		return convertStringFromNull(myDeviceModel);
	}


	public static String convertMobNumber(String mobno) {
		String newno = mobno.substring(mobno.length() - 10);
		return  newno;
	}

	public static String convertProperNumber(String mobno) {
		String newno = mobno.replaceAll(",","");
		return  newno;
	}
	public static String convertStringFromNull(String s){
		if(s == null){
			s ="";
		}
		return s;
	}

	public static String gettUtilAgentId(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getAgentID();
	String	defac  = defa.get(SessionManagement.AGENTID);
		return defac;
	}

	public static String gettUtilUserId(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getUserIdd();
		String	defac  = defa.get(SessionManagement.KEY_USERID);
		return defac;
	}

	public static String gettUtilMobno(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getMobNo();
		String	defac  = defa.get(SessionManagement.KEY_MOBILE);
		return defac;
	}


	public static String gettUtilEmail(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getEmail();
		String	defac  = defa.get(SessionManagement.KEY_EMAIL);
		return defac;
	}
	public static String gettUtilCustname(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getCustName();
		String	defac  = defa.get(SessionManagement.KEY_CUSTNAME);
		return defac;
	}
	public static String getLastl(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getLastl();
		String	defac  = defa.get(SessionManagement.LASTL);
		return defac;
	}
	public static String getAcountno(Context c){
		session = new SessionManagement(c);
		HashMap<String, String> defa = session.getAccountNo();
		String	defac  = defa.get(SessionManagement.KEY_ACCO);
		return defac;
	}

	public static String convertDate(String date){
		String fdate = date;
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date datee = null;
		try {
			datee = dt.parse(date);
			SimpleDateFormat dt1 = new SimpleDateFormat("dd MMMM yyyy hh:mm");

			fdate = dt1.format(datee);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// *** same for the format String below
		return fdate;
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static boolean checkCameraPermission(final Context context)
	{
		int currentAPIVersion = Build.VERSION.SDK_INT;
		if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
		{
			if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
					alertBuilder.setCancelable(true);
					alertBuilder.setTitle("Permission necessary");
					alertBuilder.setMessage("Permission to use camera is necessary");
					alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
						public void onClick(DialogInterface dialog, int which) {
							ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
						}
					});
					AlertDialog alert = alertBuilder.create();
					alert.show();
				} else {
					ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
				}
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static boolean checkPermission(final Context context)
	{
		int currentAPIVersion = Build.VERSION.SDK_INT;
		if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
		{
			if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
					AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
					alertBuilder.setCancelable(true);
					alertBuilder.setTitle("Permission necessary");
					alertBuilder.setMessage("External storage permission is necessary");
					alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
						public void onClick(DialogInterface dialog, int which) {
							ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
						}
					});
					AlertDialog alert = alertBuilder.create();
					alert.show();
				} else {
					ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
				}
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}


	public static String returnCustname(String sesn) {
		if (!(sesn == null)) {
			if (sesn.contains(" ")) {
				String fname = sesn.substring(0, sesn.indexOf(" "));
				String lname = sesn.substring(sesn.lastIndexOf(" "), sesn.length());
				Log.v(" SessFname is", fname);
				Log.v("L Sessname is", lname);

				sesn = fname;
			}
		}else{
			sesn = "";
		}
		return sesn;
	}

	public static  String convertTxnCodetoServ(String txncode){
		if (txncode.equals("FTINTRABANK")){
			txncode = "FBN Transfer";
		}else if (txncode.equals("FTINTERBANK")){
			txncode = "Other Bank";
		}else if (txncode.equals("CWDBYACT")){
			txncode = "Cash Withdrawal";
		}
		else if (txncode.equals("CASHDEP")){
			txncode = "Cash Deposit";
		}
		else if (txncode.equals("MMO")){
			txncode = "Airtime";
		}
		else if (txncode.equals("BILLPAYMENT")){
			txncode = "Pay Bills";
		}
		return txncode;
	}

	public static String nextToken(String token)
	{
		String s="000000";
		try{
			Integer tokenNo = Integer.parseInt(token);
			tokenNo=(tokenNo < 9999)?tokenNo+1:1;
			return s.substring(0, s.length()-tokenNo.toString().length())+tokenNo.toString();
		}
		catch(Exception e){
			e.printStackTrace();
			throw new NumberFormatException("Invalid Token Number");
		}
	}
	public static  String generateHashString(String data) throws UnsupportedEncodingException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] dataInBytes = data.getBytes("UTF-8");
			md.update(dataInBytes);

			byte[] mdbytes = md.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i=0;i<mdbytes.length;i++) {
				hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
			}

			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static  boolean checkUserLocked(String respcode){
		boolean chklock = false;
		if(respcode.equals("991") || respcode.equals("92") || respcode.equals("993") ){
			chklock = true;
		}
return  chklock;
	}
	public static  boolean check(String respcode){
		boolean chklock = false;
		if(respcode.equals("991") || respcode.equals("92") || respcode.equals("993") ){
			chklock = true;
		}
		return  chklock;
	}
	public static  String CheckNumberZero(String mobno){
		String firstLetter = String.valueOf(mobno.charAt(0));
		int nxtlngth = mobno.length();
		if(firstLetter.equals("0")){
         mobno = mobno.substring(1,nxtlngth);
		}
		return  mobno;
	}
	 public static String appendSlash(String... args){
		 String fslash = null;
		 int counter = 0;
		 for (String arg : args) {
			 if(counter == 0){
fslash = arg;
			 }else {
				 fslash = "/"+arg;
			 }
					 counter++;
		 }
		 return fslash;
	 }
}