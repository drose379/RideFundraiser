package drose379.ridefundraiser;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.text.DecimalFormat;

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
		public void distanceUpdate(String distance);
		public void averageSpeedUpdate(double avgSpeed);
		public void updateStatus(boolean status);
	}

	Context context;
	LocationCallback callback;

	LocationManager locationManager;
	Location lastLocation = null;
	float totalDistance;

	DecimalFormat format = new DecimalFormat("##.##");

	private static GPSHelper sharedInstance = null;

	public static GPSHelper getInstance(Context context) {
		if (sharedInstance == null) {
			sharedInstance = new GPSHelper(context);
		} 
		return sharedInstance;
	}

	public GPSHelper(Context context) {
		this.context = context;
 		callback = (LocationCallback) context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
	}

	public void startLocationUpdates() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,new CustomLocationListener());
		callback.updateStatus(true);
	}

	public void updateDistance(Location location) {
		if (location.getAccuracy() < 45 && location.getSpeed() >= 0.75) {
            
            lastLocation = lastLocation == null ? location : lastLocation;
           	totalDistance += totalDistance + lastLocation.distanceTo(location);
           	
           	callback.distanceUpdate(format.format(totalDistance/1609.34));   
        
        } 
	}
	
	public void updateAverageSpeed(Location location) {

	}

	public void updateGoalReached(Location location) {
		
	}



	public class CustomLocationListener implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
            /**
             * Make sure isRunning boolean in LiveMileEvent is switched to true with updateStatus method
             * Only accept Location object if accuracy is less then 25 meters
             * Collect distance from last location ALL IN METERS, STORE IN METERS, switch to miles (DecimalFormatter) when giving back to UI
             * Also keep average speed with each collected Location object
             */

            updateDistance(location);
            updateAverageSpeed(location);
            updateGoalReached(location);



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
	}

}
