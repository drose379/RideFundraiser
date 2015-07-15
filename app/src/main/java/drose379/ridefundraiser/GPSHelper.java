package drose379.ridefundraiser;

import android.content.Context;

import android.os.Bundle;

import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;

/**
 * Created by dylanrose60 on 7/13/15.
 */
public class GPSHelper {

	/**
	  * Use this class to request location updates
	  * Use the data to compute distance traveled since updates started
	  * Give callback to UI thread with updated distance every interval
	  * Look into functionality for maps
	  */

	public interface LocationCallback {
		public void distanceUpdate(double distance);
	}

	private LocationManager locationManager;

	private LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {

		}
		@Override
		public void onProviderDisabled(String provider) {

		}
		@Override
		public void onProviderEnabled(String provider) {

		}
		@Override
		public void onStatusChanged(String provider,int status,Bundle extras) {

		}
	};

	private GPSHelper sharedInstance = null;

	public static GPSHelper getInstance(Context context) {
		if (sharedInstance == null) {
			sharedInstance = new GPSHelper(context);
		} 
		return sharedInstance;
	}

	public GPSHelper(Context context) {
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	public void startListening() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,500,0,locationListener);
	}


}
