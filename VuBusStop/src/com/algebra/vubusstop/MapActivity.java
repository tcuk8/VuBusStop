package com.algebra.vubusstop;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener,
		Stanice {

	private static final float DEFAULT_ZOOM = 15;

	// Google Map
	private GoogleMap googleMap;

	LocationClient mLocationClient;

	// private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		try {
			// Loading map
			initiliazeMap();
			// My Location Button
			googleMap.setMyLocationEnabled(true);
			mLocationClient = new LocationClient(this, this, this);
			mLocationClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// pozicioniranje kamere na vukovar
		CameraPosition vukovarPosition = new CameraPosition.Builder()
				.target(new LatLng(45.360192, 18.990560)).zoom(12).build();

		googleMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(vukovarPosition));

		// create markers

		String snippet_crveni = "tu æe iæi vozne lnije za ovu stanicu";
		String snippet_crni = "tu ide vozna linija za ovaj marker";
		String silos = "vozna linija";
		String priljevo_vodovod = "vozna linija";
		String kudeljara = "vozna linija";
		String zelj_stanica_borovo = "vozna linija";
		String tvornica = "vozna linija";
		String radnicki_dom_borovo = "vozna linija";

		MarkerOptions marker_crveni = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(new LatLng(45.3529888303851, 18.99939396423567))
				.title("Crveni Marker").snippet(snippet_crveni);

		MarkerOptions marker_crni = new MarkerOptions()
				.position(new LatLng(45.352733, 19.000014))
				.title("Plavi Marker")
				.snippet(snippet_crni)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_plavi_72px));
		
		
		MarkerOptions marker_radnicki_dom_borovo = new MarkerOptions()
		.icon(BitmapDescriptorFactory
				.fromResource(R.drawable.marker_crveni_48px))
		.position(RADNICKI_DOM_BOROVO).title("Radnièki dom Borovo").snippet(radnicki_dom_borovo);
		MarkerOptions marker_tvornica = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(TVORNICA).title("Tvornica").snippet(tvornica);
		MarkerOptions marker_zelj_stanica_borovo = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(ZELJ_STANICA_BOROVO)
				.title("Željeznièka stanica Borovo")
				.snippet(zelj_stanica_borovo);
		MarkerOptions marker_silos = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(SILOS).title("Silos").snippet(silos);
		MarkerOptions marker_priljevo_vodovod = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(PRILJEVO_VODOVOD).title("Priljevo vodovod")
				.snippet(priljevo_vodovod);
		MarkerOptions marker_kudeljara = new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.marker_crveni_48px))
				.position(KUDELJARA).title("Kudeljara").snippet(kudeljara);

		// adding marker
		googleMap.addMarker(marker_crveni);
		googleMap.addMarker(marker_crni);
		googleMap.addMarker(marker_silos);
		googleMap.addMarker(marker_priljevo_vodovod);
		googleMap.addMarker(marker_kudeljara);
		googleMap.addMarker(marker_zelj_stanica_borovo);
		googleMap.addMarker(marker_tvornica);
		googleMap.addMarker(marker_radnicki_dom_borovo);

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initiliazeMap() {
		if (googleMap == null) {
			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_map_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.mapTypeNormal:
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.mapTypeSatellite:
			googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.mapTypeTerrain:
			googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.mapTypeHybrid:
			googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.gotoCurrentLocation:
			gotoCurrentLocation();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MapStateManager mgr = new MapStateManager(this);
		CameraPosition position = mgr.getSavedCameraPosition();
		if (position != null) {
			CameraUpdate update = CameraUpdateFactory
					.newCameraPosition(position);
			googleMap.moveCamera(update);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		MapStateManager mgr = new MapStateManager(this);
		mgr.saveMapstate(googleMap);
	}

	protected void gotoCurrentLocation() {
		Location currentLocation = mLocationClient.getLastLocation();
		if (currentLocation == null) {
			Toast.makeText(this, "Trenutna lokacija je nedostupna",
					Toast.LENGTH_SHORT).show();
		} else {
			LatLng ll = new LatLng(currentLocation.getLatitude(),
					currentLocation.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,
					DEFAULT_ZOOM);
			googleMap.animateCamera(update);
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0) {

		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		request.setInterval(60000);
		request.setFastestInterval(20000);
		mLocationClient.requestLocationUpdates(request, this);

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

}
