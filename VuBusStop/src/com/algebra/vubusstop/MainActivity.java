package com.algebra.vubusstop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// pozicioniranje kamere na vukovar

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(45.360192, 18.990560)).zoom(12).build();

		googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		// create markers

		double latitude1 = 45.35298883038511;		
		double longitude1 = 18.999393964235676;
		
		double latitude2 = 45.353577;
		double longitude2 = 18.998761;
		
		MarkerOptions marker_crveni = new MarkerOptions().position(
				new LatLng(latitude1, longitude1)).title("Crveni Marker");
		MarkerOptions marker_crni = new MarkerOptions().position(
				new LatLng(latitude2, longitude2)).title("Plavi Marker");

		// Changing marker icon
		marker_crveni.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.marker_crveni80px));
		marker_crni.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.marker_crni80px));

		// adding marker
		googleMap.addMarker(marker_crveni);
		googleMap.addMarker(marker_crni);

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

}
