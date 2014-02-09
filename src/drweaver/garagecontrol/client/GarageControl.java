package drweaver.garagecontrol.client;

import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Geolocation.PositionOptions;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import drweaver.garagecontrol.client.view.GarageControlOptionsView;
import drweaver.garagecontrol.client.view.GarageControlView;
import drweaver.garagecontrol.client.view.ViewFactory;



/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GarageControl implements EntryPoint {

	Logger logger = Logger.getLogger(GarageControl.class.getName());

	private final ViewFactory viewFactory = GWT.create(ViewFactory.class);

	private final GarageControlView controlView = viewFactory.getGarageControlView();
	private final GarageControlOptionsView optionsView = viewFactory.getGarageControlOptionsView();

	private final GarageControlAction action = new GarageControlAction(controlView, optionsView);

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {

		action.refresh();

		startLocationWatcher();

		VerticalPanel rootPanel = new VerticalPanel();
		rootPanel.setWidth("100%");
		RootPanel.get().add(rootPanel);

		rootPanel.add(optionsView);

		rootPanel.add(controlView);

	}

	private void startLocationWatcher() {
		if( Storage.isLocalStorageSupported() ) {
			Storage.getLocalStorageIfSupported().removeItem("location");
		}

		Geolocation geo = Geolocation.getIfSupported();
		if( geo != null ) {
			PositionOptions opts = new PositionOptions();
			opts.setHighAccuracyEnabled(false);
			opts.setMaximumAge(300000);
			opts.setTimeout(2000);
			geo.watchPosition(new Callback<Position, PositionError>() {
				@Override
				public void onSuccess(Position result) {
					Storage storage = Storage.getLocalStorageIfSupported();
					if( storage != null ) {
						Coordinates coords = result.getCoordinates();
						storage.setItem("location", coords.getLatitude()+","+coords.getLongitude());
					}
				}

				@Override
				public void onFailure(PositionError reason) {
					logger.info("Location error: " + reason);
				}
			}, opts);
		}
	}

}
