package firstmob.firstbank.com.firstagent;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.OkHttpClient;
import rest.ApiClient;
import rest.ApiInterface;
import rest.ApiSecurityClient;
import retrofit2.Call;
import retrofit2.Callback;
import security.EncryptTransactionPin;
import security.SecurityLayer;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInActivity extends ActionBarActivity implements View.OnClickListener {
	Button signinn,bio;
	EditText us;
	EditText pin;
	String city = "Lagos,NG";
	EditText et4;
	TextView gm,hiagent,prvpin,opacc,succt,fpin,opacchd,succthead;
	String finlnom;
	boolean chkcard = false;
	SessionManagement session;
	ProgressDialog prgDialog,prgDialog2;
	static Hashtable<String, String> data1;
	public static final String SHAREDPREFFILE = "temp";
	public static final String USERIDPREF = "uid";
	public static final String TOKENPREF = "tkn";
	public static final String AGMOB = "agmobno";


	String finlussd,finlpin,finlimei,finip,finmac;
	boolean chkbio = false;
	boolean chkbiolog = false;
	boolean isFeatureEnabled = false;
	boolean isBioLogEnabled = false;
	private boolean onReadyIdentify = false;
	private boolean onReadyEnroll = false;
	String fingindex;
    private Toolbar mToolbar;
    Typeface mTf;
	private ShakeDetectionListener mShaker;

	private TextView temp,gethelp,registeruser;
	LinearLayout linearLayoutMine;

	private static final String TAG = MainActivity.class.getSimpleName();


	/** Alias for our key in the Android Key Store */
	private static final String KEY_NAME = "my_key";


	private static final String DIALOG_FRAGMENT_TAG = "myFragment";
	private static final String SECRET_MESSAGE = "Very secret message";
	private static final String KEY_NAME_NOT_INVALIDATED = "key_not_invalidated";
	static final String DEFAULT_KEY_NAME = "default_key";
	 KeyguardManager mKeyguardManager;
	 FingerprintManager mFingerprintManager;
	 FingerprintAuthenticationDialogFragment mFragment  = new FingerprintAuthenticationDialogFragment();
     KeyStore mKeyStore;
	ProgressDialog pro ;
	 KeyGenerator mKeyGenerator;
	 Cipher mCipher;
	InputMethodManager mInputMethodManager;
	 SharedPreferences mSharedPreferences;
	private FingerprintManager.CryptoObject cryptoObject;
	private PinLockView mPinLockView;
	private IndicatorDots mIndicatorDots;
	SweetAlertDialog pDialog;
String finpin;
	private PinLockListener mPinLockListener = new PinLockListener() {
		@Override
		public void onComplete(String pin) {

			Log.d(TAG, "Pin complete: " + pin);
			finpin = pin;
		}

		@Override
		public void onEmpty() {
			Log.d(TAG, "Pin empty");
		}

		@Override
		public void onPinChange(int pinLength, String intermediatePin) {
			Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signinnew);
pro = new ProgressDialog(this);
		pro.setMessage("Loading...");
		pro.setTitle("");
		pro.setCancelable(false);

		temp = (TextView) findViewById(R.id.txt9);
        gm = (TextView) findViewById(R.id.txt);
        hiagent = (TextView) findViewById(R.id.txt2);
        prvpin = (TextView) findViewById(R.id.txt5);
        opacc = (TextView) findViewById(R.id.numbers);
        succt = (TextView) findViewById(R.id.succtrans);
		registeruser = (TextView) findViewById(R.id.text17);
		registeruser.setOnClickListener(this);
		session = new SessionManagement(getApplicationContext());
	//	datetime = (TextView) findViewById(R.id.txt10);

		String dt = getDateTimeStamp();
		//datetime.setText(dt);
        mTf = Typeface.createFromAsset(getAssets(), "RobotoSlab-Regular.ttf");
		prgDialog2 = new ProgressDialog(this);
		prgDialog2.setMessage("Loading....");
		prgDialog2.setCancelable(false);
		mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
		mPinLockView.setPinLockListener(mPinLockListener);
		mIndicatorDots = (IndicatorDots) findViewById(R.id.indicator_dots);

		mPinLockView.attachIndicatorDots(mIndicatorDots);


		mPinLockView.setPinLength(5);
		mPinLockView.setTextColor(getResources().getColor(R.color.white));
		gethelp = (TextView) findViewById(R.id.text13);
		gethelp.setOnClickListener(this);
		signinn = (Button) findViewById(R.id.signinn);
		signinn.setOnClickListener(this);


		String key = "97206B46CE46376894703ECE161F31F2";
		String password = "43211";
		String encrypted = "9B7D106A26E2884F";
		try {
			System.out.println("Encrypt Pin "+EncryptTransactionPin.encrypt(key, password, 'F'));
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {

			System.out.println("Decrypt  Pin "+EncryptTransactionPin.decrypt(key, encrypted));
		} catch (Exception e) {
			e.printStackTrace();
		}

		final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);


       this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



		pDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
		pDialog.setTitleText("Loading");
		pDialog.setCancelable(false);
		//testResp();
		//invokeAds();
/*
		try {
			mClient = new MobileServiceClient("https://auth21.azurewebsites.net", getApplicationContext());

			//Log.d(Constant.LOG_TAG, "tuko poa: ");
		} catch (MalformedURLException m) {
			m.printStackTrace();
			//Log.d(Constant.LOG_TAG, "maneno: " + m.toString());
		}
		authenticate();*/

	}







	@Override
	protected void onResume() {

		super.onResume();

	}


	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.signinn) {
			if (Utility.checkInternetConnection(getApplicationContext())) {
				if (!(finpin == null)) {


					//	pDialog.show();
					//invokeFX();
					/*if(finpin.equals("99999")){
						showDialog();
					}else {*/
						loginRetrofit();
				//	}

				}
				else{
					Toast.makeText(getApplicationContext(), "Please enter a valid pin", Toast.LENGTH_LONG).show();

				}
			}
		//	checkInternetConnection();


		}
		if (v.getId() == R.id.text17) {
			//	checkInternetConnection();

			startActivity(new Intent(getApplicationContext(),ActivateAgent.class));
		}
		if (v.getId() == R.id.text13) {
			//	checkInternetConnection();

			startActivity(new Intent(getApplicationContext(),GetHelpActivity.class));
		}
	

	}
	private void showDialog() {
		final CharSequence[] items = { "Super Agent", "Agent",
				"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);
		builder.setTitle("");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {


				if (items[item].equals("Super Agent")) {
					finish();
					startActivity(new Intent(getApplicationContext(), SupAgentActivity.class));
				} else if (items[item].equals("Agent")) {
					loginRetrofit();
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
	public void showDialog2(){
		AlertDialog.Builder builderSingle = new AlertDialog.Builder(SignInActivity.this);
		final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				getApplicationContext(),
				android.R.layout.select_dialog_singlechoice);
		arrayAdapter.add("Superagent");
		arrayAdapter.add("Agent");


		builderSingle.setNegativeButton(
				"cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builderSingle.setAdapter(
				arrayAdapter,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							finish();
							startActivity(new Intent(getApplicationContext(), SupAgentActivity.class));
						} else if (which == 1) {
							loginRetrofit();
						}
					}
				});
		builderSingle.show();
	}
	public void invokeFX( ) {
		// Show Progress Dialog


		ApiInterface apiService =
				ApiClient.getClient().create(ApiInterface.class);
		// reg/devReg.action/{channel}/{userId}/{merchantId}/{mobileNumber}/{pin}/{secans1}/{secans2}/{secans3}/{macAddr}/{deviceIp}/{imeiNo}/{serialNo}/{version}/{deviceType}/{gcmId}
		// /agencyapi/app/reg/devReg.action/1/CEVA/JANE00000000019493818389/67E13557CCC8F7DA/secans1/secans2/secans3/123.123.324234.123.123./123.123.123/3213213123123129493818389000/4.3.2/mobile/88932983298kldfjkdf93290e3kjdsfkjds90we
		String key = "97206B46CE46376894703ECE161F31F2";

		String encrypted = null;
		try {

			encrypted = EncryptTransactionPin.encrypt(key, finpin, 'F');
			System.out.println("Encrypt Pin " + EncryptTransactionPin.encrypt(key, finpin, 'F'));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.v("Dev Reg", "1" + "/CEVA/" + encrypted + "2347777777777/");
		String usid = Utility.gettUtilUserId(getApplicationContext());

		// Make RESTful webservice call using AsyncHttpClient object
		final AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(35000);
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.setSSLSocketFactory(sf);
		} catch (Exception e) {
		}

		String url = ApplicationConstants.NET_URL + "1/" + usid + "/" + encrypted + "/" + "0000";
		Log.v("Log url",url);
		//   String url =newurl+"/natmobileapi/rest/customer/loadfx";
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client.setSSLSocketFactory(sf);
		} catch (Exception e) {
		}
		client.post(url, new AsyncHttpResponseHandler() {
			// When the response returned by REST has Http response code '200'
			@Override
			public void onSuccess(String response) {
				// Hide Progress Dialog

				try {
					// JSON Object
					Log.v("response..:", response);
					JSONObject obj = new JSONObject(response);

					if (obj.optString("responseCode").equals("00")) {
						boolean checknewast = session.checkAst();
						if (checknewast == false) {
							//Toast.makeText(getApplicationContext(), "You have successfully logged in", Toast.LENGTH_LONG).show();
							finish();
							startActivity(new Intent(getApplicationContext(), AdActivity.class));
						} else {
						finish();
							startActivity(new Intent(getApplicationContext(), FMobActivity.class));
						}
					}
					// Else display error message
					else {
						Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), " The device has not successfully connected to server. Please check your internet settings", Toast.LENGTH_LONG).show();
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
				}
				pDialog.dismiss();
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
								  String content) {
			//	Log.v("response..:", content);

				Toast.makeText(getApplicationContext(), "There was an error on your request", Toast.LENGTH_LONG).show();
pDialog.dismiss();
			}
		});
	}

	public static String getDateTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM yyy ");
		Date convertedCurrentDate = new Date();



		String strDate = sdf2.format(convertedCurrentDate);

		return strDate;
	}

	public String setMobFormat(String mobno){
		String vb = mobno.substring(Math.max(0, mobno.length() - 9));
		Log.v("Logged Number is",vb);
		if(vb.length() == 9 && (vb.substring(0, Math.min(mobno.length(), 1)).equals("7"))){
			return "254"+vb;
		}else{
			return  "N";
		}
	}


	public void SetDialog(String msg,String title){
		new MaterialDialog.Builder(this)
				.title(title)
				.content(msg)

				.negativeText("Close")
				.show();
	}


	/**
	 * Creates a symmetric key in the Android Key Store which can only be used after the user has
	 * authenticated with fingerprint.
	 */

	private void showConfirmation(byte[] encrypted) {

		if (encrypted != null) {
			Toast.makeText(this,
					Base64.encodeToString(encrypted, 0 /* flags */),
					Toast.LENGTH_LONG).show();
			//datetime.setText();
		}
	}
	public void onPurchased(boolean withFingerprint) {
		if (withFingerprint) {
			// If the user has authenticated with fingerprint, verify that using cryptography and
			// then show the confirmation message.
			tryEncrypt();
		} else {
			// Authentication happened with backup password. Just show the confirmation message.
			showConfirmation(null);
		}
	}

	private void tryEncrypt() {
		try {
			byte[] encrypted = mCipher.doFinal(SECRET_MESSAGE.getBytes());
			showConfirmation(encrypted);
		} catch (BadPaddingException | IllegalBlockSizeException e) {
			Toast.makeText(this, "Failed to encrypt the data with the generated key. "
					+ "Retry the purchase", Toast.LENGTH_LONG).show();
			Log.e(TAG, "Failed to encrypt the data with the generated key." + e.getMessage());
		}
	}
	@Override
	public void onPause()
	{
		//mShaker.pause();
		super.onPause();
	}


public void ClearPin(){
	mPinLockView.resetPinLockView();
}

/*

	private void authenticate() {
		// We first try to load a token cache if one exists.

		// If we failed to load a token cache, login and create a token cache
		if (loadUserTokenCache(mClient))
		{
			Toast.makeText(getApplicationContext(), "Already logged in", Toast.LENGTH_LONG).show();
		}

		// Login using the Google provider.
		ListenableFuture<MobileServiceUser> mLogin = mClient.login(MobileServiceAuthenticationProvider.Google);

		Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
			@Override
			public void onFailure(Throwable exc) {
				Toast.makeText(getApplicationContext(), "You must log in. Login Required", Toast.LENGTH_LONG).show();


			}
			@Override
			public void onSuccess(MobileServiceUser user) {
				Toast.makeText(getApplicationContext(),String.format(
						"You are now logged in - %1$2s",
						user.getUserId()), Toast.LENGTH_LONG).show();

				cacheUserToken(mClient.getCurrentUser());
				//createTable();
			}
		});
	}


	private void cacheUserToken(MobileServiceUser user)
	{
		SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(USERIDPREF, user.getUserId());
		editor.putString(TOKENPREF, user.getAuthenticationToken());
		editor.commit();
	}
	private boolean loadUserTokenCache(MobileServiceClient client)
	{
		SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
		String userId = prefs.getString(USERIDPREF, null);
		if (userId == null)
			return false;
		String token = prefs.getString(TOKENPREF, null);
		if (token == null)
			return false;

		MobileServiceUser user = new MobileServiceUser(userId);
		user.setAuthenticationToken(token);
		client.setCurrentUser(user);

		return true;
	}
*/




public void loginRetrofit(){
    OkHttpClient client = new OkHttpClient();
    try {
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(null, null);
        MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
        sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        client.sslSocketFactory();
    } catch (Exception e) {
    }

    ApiInterface apiService =
            ApiClient.getClient().create(ApiInterface.class);
    // reg/devReg.action/{channel}/{userId}/{merchantId}/{mobileNumber}/{pin}/{secans1}/{secans2}/{secans3}/{macAddr}/{deviceIp}/{imeiNo}/{serialNo}/{version}/{deviceType}/{gcmId}
    // /agencyapi/app/reg/devReg.action/1/CEVA/JANE00000000019493818389/67E13557CCC8F7DA/secans1/secans2/secans3/123.123.324234.123.123./123.123.123/3213213123123129493818389000/4.3.2/mobile/88932983298kldfjkdf93290e3kjdsfkjds90we
    String key = "97206B46CE46376894703ECE161F31F2";

    String encrypted = null;
    try {

        encrypted = EncryptTransactionPin.encrypt(key, finpin, 'F');
        System.out.println("Encrypt Pin "+EncryptTransactionPin.encrypt(key, finpin, 'F'));
    } catch (Exception e) {
        e.printStackTrace();
    }

    Log.v("Dev Reg", "1" + "/CEVA/" + encrypted  + "2347777777777/");
    String usid = Utility.gettUtilUserId(getApplicationContext());
	String mobnoo = Utility.gettUtilMobno(getApplicationContext());
	String params = "1" + "/"+usid+"/" + encrypted  + "/"+mobnoo;
	String getchklogin = session.getString(SessionManagement.CHKLOGIN);
	pro.show();
	if(!(getchklogin == null) && getchklogin.equals("Y")){
		Log.v("GetChkLg Params",getchklogin);
	setLogout(params);
	}else {


		invokeLoginSec(params);
	}
    /*Call<Login> call2 = apiService.getLoginResponse("1", usid, encrypted,  "0000");
    call2.enqueue(new Callback<Login>() {
        @Override
        public void onResponse(Call<Login> call, Response<Login> response) {
            if (!(response.body() == null)) {
                String responsemessage = response.body().getMessage();
                String respcode = response.body().getRespCode();


                LoginData datas = response.body().getResults();


                if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
                    Log.v("Response Message", responsemessage);

                    if (respcode.equals("00")) {




                        if (!(datas == null)) {
                            String agentid = datas.getAgentID();
                            String userid = datas.getUserId();
                            String username = datas.getUserName();
                            String email = datas.getEmaill();
                            String lastl = datas.getLastLoggedIn();
                            String mobno = datas.getMobileNo();
                            String accno = datas.getAccountNo();
                            session.SetAgentID(agentid);
                            session.SetUserID(userid);
                            session.putCustName(username);
                            session.putEmail(email);
                            session.putLastl(lastl);
                            session.putMobNo(mobno);
                            session.putAccountno(accno);
                            session.removeTimest();
                            boolean checknewast = session.checkAst();
                            if (checknewast == false) {
                                Toast.makeText(getApplicationContext(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), AdActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "You have successfully logged in", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), FMobActivity.class));
                            }

                        }
                    }
                    else {

                        Toast.makeText(
                                getApplicationContext(),
                                responsemessage,
                                Toast.LENGTH_LONG).show();


                    }

                }
                else {

                    Toast.makeText(
                            getApplicationContext(),
                            "There was an error on your request",
                            Toast.LENGTH_LONG).show();


                }
            } else {

                Toast.makeText(
                        getApplicationContext(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();


            }

            ClearPin();

            pDialog.dismiss();


        }

        @Override
        public void onFailure(Call<Login> call, Throwable t) {
            // Log error here since request failed
            Log.v("throwable error", t.toString());
            if(t.toString().equals("java.io.EOFException")) {
loginRetrofit();
            }else {

                Toast.makeText(
                        getApplicationContext(),
                        "There was an error on your request",
                        Toast.LENGTH_LONG).show();
            }

            ClearPin();
            pDialog.dismiss();
        }

    });*/
}
	public void testResp(){
		try {
			// JSON Object

			JSONObject obj = new JSONObject();
			obj.put("DHASH","fe6b8c9614eebc1fe0a1be92afa36da9965d956832925fb01c68f0ff3fed31");
			obj.put("inp","484c425254444e444e484a2b5a46695a457072687776337a5335332b416b776b2b4f354b506b6a425470684a41376b64673932754168677846776b6a51596b48576536624b416547676250330d0a527551423154663172727a48463178474647586c3869516c734f593051413870644b795a4c55436c3432445a497551437a47684b664c684e6642456f617731787a7a6f6b72747a626b7345360d0a43684b744a43743559667753495932706331584b764f70456b627668716a32416647456e70632b6e7a4d467551786e3274726a4e5a71302f774e6a5952772f2b70754b4649714e69523538510d0a4c684457427959485038625a774f774e6c375244336c3053686f663953543572545566414e783341643254646a576d7a774c57576e304e366231443547326864637536677673383d0d0a");
			obj.put("svoke","45466d6c742b6e7037675863365738634d4946793359733348674f54645a64373631416e774a73576c66673d0d0a");
			obj.put("status","S");



			//obj = Utility.onresp(obj,getActivity());
			obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
			SecurityLayer.Log("decrypted_response", obj.toString());
			String skey = session.getString(SecurityLayer.KEY_SKEY);
			String respcode = obj.optString("responseCode");
			String responsemessage = obj.optString("message");


			JSONObject datas = obj.optJSONObject("data");
			//session.setString(SecurityLayer.KEY_APP_ID,appid);
			if (respcode.equals("00")) {
				if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
					Log.v("Response Message", responsemessage);

					if (respcode.equals("00")) {
						if (!(datas == null)) {
							String agentid = datas.optString("agent");
							String userid = datas.optString("userId");
							String username = datas.optString("userName");
							String email = datas.optString("email");
							String lastl = datas.optString("lastLoggedIn");
							String mobno = datas.optString("mobileNo");
							String accno = datas.optString("acountNumber");
							session.SetAgentID(agentid);
							session.SetUserID(userid);
							session.putCustName(username);
							session.putEmail(email);
							session.putLastl(lastl);
							session.setString(AGMOB,mobno);

							session.putAccountno(accno);
							boolean checknewast = session.checkAst();
							if (checknewast == false) {

								finish();
								startActivity(new Intent(getApplicationContext(), AdActivity.class));
							} else {

								finish();
								startActivity(new Intent(getApplicationContext(), FMobActivity.class));
							}
						}
					}
					else {

						Toast.makeText(
								getApplicationContext(),
								responsemessage,
								Toast.LENGTH_LONG).show();


					}

				}
				else {

					Toast.makeText(
							getApplicationContext(),
							"There was an error on your request",
							Toast.LENGTH_LONG).show();


				}
			}

			// Else display error message
			else {
				Toast.makeText(
						getApplicationContext(),
						"There was an error on your request",
						Toast.LENGTH_LONG).show();

			}
		} catch (JSONException e) {
			SecurityLayer.Log("encryptionJSONException", e.toString());
			// TODO Auto-generated catch block
			Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
			// SecurityLayer.Log(e.toString());

		} catch (Exception e) {
			SecurityLayer.Log("encryptionJSONException", e.toString());
			// SecurityLayer.Log(e.toString());
		}
	}


	private void invokeLoginSec(final String params) {

		final AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(35000);

		String endpoint= "login/login.action/";

		String url = "";
		try {
			url = SecurityLayer.generalLogin(params,"23322",getApplicationContext(),endpoint);
			//Log.d("cbcurl",url);
			Log.v("RefURL",url);
			SecurityLayer.Log("refurl", url);
			SecurityLayer.Log("params", params);
		} catch (Exception e) {
			Log.e("encryptionerror",e.toString());
		}

		try {
			MySSLSocketFactory.SecureURL(client, getApplicationContext());
		} catch (KeyStoreException e) {
			SecurityLayer.Log(e.toString());
			SecurityLayer.Log(e.toString());
		} catch (IOException e) {
			SecurityLayer.Log(e.toString());
		} catch (NoSuchAlgorithmException e) {
			SecurityLayer.Log(e.toString());
		} catch (CertificateException e) {
			SecurityLayer.Log(e.toString());
		} catch (UnrecoverableKeyException e) {
			SecurityLayer.Log(e.toString());
		} catch (KeyManagementException e) {
			SecurityLayer.Log(e.toString());
		}

		client.post(url, new AsyncHttpResponseHandler() {
			// When the response returned by REST has Http response code '200'
			@Override
			public void onSuccess(String response) {
				// Hide Progress Dialog
				pro.dismiss();
				try {
					// JSON Object
					//Log.v("response..:", response);
					JSONObject obj = new JSONObject(response);
					//obj = Utility.onresp(obj,getActivity());
					obj = SecurityLayer.decryptGeneralLogin(obj, getApplicationContext());
					SecurityLayer.Log("decrypted_response", obj.toString());

					String respcode = obj.optString("responseCode");
					String responsemessage = obj.optString("message");


					JSONObject datas = obj.optJSONObject("data");
					//session.setString(SecurityLayer.KEY_APP_ID,appid);

						if (Utility.isNotNull(respcode) && Utility.isNotNull(responsemessage)) {
							Log.v("Response Message", responsemessage);

							if (respcode.equals("00")) {
								if (!(datas == null)) {
									session.setString(SessionManagement.CHKLOGIN,"Y");
									String agentid = datas.optString("agent");
									String userid = datas.optString("userId");
									String username = datas.optString("userName");
									String email = datas.optString("email");
									String lastl = datas.optString("lastLoggedIn");
									String mobno = datas.optString("mobileNo");
									String accno = datas.optString("acountNumber");
									session.SetAgentID(agentid);
									session.SetUserID(userid);
									session.putCustName(username);
									session.putEmail(email);
									session.putLastl(lastl);
									session.setString(AGMOB,mobno);
									session.putAccountno(accno);
									boolean checknewast = session.checkAst();
									if (checknewast == false) {

										finish();
										startActivity(new Intent(getApplicationContext(), AdActivity.class));
									} else {

										finish();
										startActivity(new Intent(getApplicationContext(), FMobActivity.class));
									}
								}
							}
							else {

								Toast.makeText(
										getApplicationContext(),
										responsemessage,
										Toast.LENGTH_LONG).show();


							}

						}
						else {

							Toast.makeText(
									getApplicationContext(),
									"There was an error on your request",
									Toast.LENGTH_LONG).show();


						}

				} catch (JSONException e) {
					SecurityLayer.Log("encryptionJSONException", e.toString());
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
					// SecurityLayer.Log(e.toString());

				} catch (Exception e) {
					SecurityLayer.Log("encryptionJSONException", e.toString());
					// SecurityLayer.Log(e.toString());
				}
			}

			@Override
			public void onFailure(int statusCode, Throwable error,
								  String content) {

				// Hide Progress Dialog
				pro.dismiss();
				SecurityLayer.Log("error:", error.toString());
				// When Http response code is '404'
				if (statusCode == 404) {
					Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
				}
				// When Http response code is '500'
				else if (statusCode == 500) {
					Toast.makeText(getApplicationContext(), "We are unable to process your request at the moment. Please try again later", Toast.LENGTH_LONG).show();
				}
				// When Http response code other than 404, 500
				else {
					Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();

				}
			}
		});
	}








	private void setLogout(final String lginparams) {


		String endpoint= "login/logout.action";


		String usid = Utility.gettUtilUserId(getApplicationContext());
		String appid = session.getString(SecurityLayer.KEY_APP_ID);
		SecurityLayer.Log("appid", appid);
		String params = "1/"+usid+"/"+appid;
		String urlparams = "";
		try {
			urlparams = SecurityLayer.genURLCBC(params,endpoint,getApplicationContext());
			//Log.d("cbcurl",url);
			Log.v("RefURL",urlparams);
			SecurityLayer.Log("refurl", urlparams);
			SecurityLayer.Log("params", params);
		} catch (Exception e) {
			Log.e("encryptionerror",e.toString());
		}





		ApiInterface apiService =
				ApiSecurityClient.getClient().create(ApiInterface.class);


		Call<String> call = apiService.setGenericRequestRaw(urlparams);

		call.enqueue(new Callback<String>() {
			@Override
			public void onResponse(Call<String> call, retrofit2.Response<String> response) {
				try {
					// JSON Object

					Log.v("response..:", response.body());
					JSONObject obj = new JSONObject(response.body());
					//obj = Utility.onresp(obj,getActivity());
					obj = SecurityLayer.decryptTransaction(obj, getApplicationContext());
					SecurityLayer.Log("decrypted_response", obj.toString());

					String respcode = obj.optString("responseCode");
					String responsemessage = obj.optString("message");



					//session.setString(SecurityLayer.KEY_APP_ID,appid);


					if(!(response.body() == null)) {
						if (respcode.equals("00")) {

							Log.v("Response Message", responsemessage);

						}else{

						}
					} else {

					}



				} catch (JSONException e) {
					SecurityLayer.Log("encryptionJSONException", e.toString());
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.conn_error), Toast.LENGTH_LONG).show();
					// SecurityLayer.Log(e.toString());

				} catch (Exception e) {
					SecurityLayer.Log("encryptionJSONException", e.toString());
					// SecurityLayer.Log(e.toString());
				}
				invokeLoginSec(lginparams);
			}

			@Override
			public void onFailure(Call<String> call, Throwable t) {
				// Log error here since request failed
				Log.v("Throwable error",t.toString());

				//   pDialog.dismiss();

				invokeLoginSec(lginparams);

			}
		});

	}
}
