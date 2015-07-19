package drose379.ridefundraiser;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
		public void averageSpeedUpdate(String avgSpeed);
		public void goalReachedUpdate(String percentReached);
		public void liveMapUpdate(List<LatLng> points);
		public void updateStatus(boolean status);
	}

	Context context;
	LiveMileEventHelper eventHelper;
	LocationCallback callback;


	LocationManager locationManager;
	Location lastLocation = null;
	ArrayList<Location> allLocations = new ArrayList<Location>();
	List<LatLng> polyPoints = new ArrayList<LatLng>();
	float totalDistance;

	DecimalFormat format1 = new DecimalFormat("##.##");
	DecimalFormat format2 = new DecimalFormat("##");

	boolean mapReady = false;

	private static GPSHelper sharedInstance = null;

	public static GPSHelper getInstance(Context context,LiveMileEventHelper eventHelper) {
		if (sharedInstance == null) {
			sharedInstance = new GPSHelper(context,eventHelper);
		} 
		return sharedInstance;
	}

	public GPSHelper(Context context,LiveMileEventHelper eventHelper) {
		this.context = context;
 		callback = (LocationCallback) context;
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		this.eventHelper = eventHelper;
	}

	public void setMapReady(boolean isReady) {
		mapReady = isReady;
	}

	public Location getLastLocation() {
		lastLocation = lastLocation == null ? locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) : lastLocation;
		return lastLocation;
	}

	public void startEvent() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,new CustomLocationListener());
		TimeKeeper.getInstance(context).startClock();
		callback.updateStatus(true);
	}

	/**
	  * Callback for distance measure and goal percent measure
	  */
	public void updateDistance(Location location) {
        lastLocation = lastLocation == null ? location : lastLocation;

       	totalDistance = totalDistance + lastLocation.distanceTo(location);   
       	double distanceMiles = totalDistance/1609.34;

        callback.distanceUpdate(format1.format(distanceMiles));   
       	//callback.goalReachedUpdate(format2.format(distanceMiles/Double.parseDouble(eventHelper.getGoalDistance())));

        lastLocation = location;
	}

	public void updateLiveMap(Location location) {
		/**
		  * Create LatLng object from this location
		  * Add to polyPoints
		  * Pass polyPoints through callback to LiveMileEvent activity
		  */

		LatLng current = new LatLng(location.getLatitude(),location.getLongitude());
		polyPoints.add(current);
		callback.liveMapUpdate(polyPoints);
	}
	
	public void updateAverageSpeed(Location location) {
		allLocations.add(location);
		int locationsCount = allLocations.size();
		float totalSpeed = 0;
		
		for(Location currentLocation : allLocations) {
			totalSpeed += currentLocation.getSpeed();
		}

		double averageSpeed = totalSpeed / locationsCount;
		//CURRENTLY m/s, convert to MPH
		callback.averageSpeedUpdate(format1.format(averageSpeed));
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

            if (location.getAccuracy() < 45 && location.getSpeed() > 0.55) {
            	updateDistance(location);
            	updateLiveMap(location);
            	updateAverageSpeed(location);
            }

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
