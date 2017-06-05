package http.userfunctions;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserFunctions {

	//public static String rest_url = "http://115.115.191.226:1120/GAInsurance/rest/jsonServices/"; // Indian Server
	public static String rest_url = "http://125.62.193.58:8080/GAInsurance/rest/jsonServices/"; // Siva's Machine
	//public static String rest_url = "http://125.62.193.60:1120/GAInsurance/rest/jsonServices/"; // Siva's Machine
	private static String URL = "http://192.168.0.74:8080/gameditag/index.php";
	//private static String URL = "http://nearfieldltd.com/cms/mobile/index.php";



	public void generic_array_SharedPreference(String[] key, String[] value, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		int i = 0;
		for (int j = 0; j < key.length; j++) {
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(key[i], value[j]);
			editor.commit();
			i++;
		}
	}
}