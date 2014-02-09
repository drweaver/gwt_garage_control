package drweaver.garagecontrol.client.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import com.google.gwt.core.client.Callback;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Geolocation.PositionOptions;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;

import drweaver.garagecontrol.client.GarageState;

public class GarageServiceRequest implements GarageService {

	private final Set<StateChangedHandler> stateChangedHandlers = new HashSet<StateChangedHandler>();

	private final static String baseUrl = "garagedoor/";

	Logger logger = Logger.getLogger(GarageServiceRequest.class.getName());

	private Request request = null;

	protected void actionRequest(final String path, final HashMap<String,String> params) {

		try {
			request = buildRequest(path, params).sendRequest(null, new RequestCallback() {
				@Override
				public void onError(Request request, Throwable exception) {
					invokeStateChangeHandlersOfError("Connection Issue");
				}

				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						String responseText = response.getText();
						logger.info("Response text: " + responseText);

						GarageDoorStateResponseJso stateResponse = parseStateResponse(responseText);
						if( stateResponse.getAuth().equals("OK") ) {
							String state = stateResponse.getState();
							logger.info("State = " + state);
							invokeStateChangedHandlers(state);
						} else {
							invokeStateChangeHandlersOfError(stateResponse.getAuth());
						}
					} else {
						invokeStateChangeHandlersOfError(Integer.toString(response.getStatusCode()));
					}
				}


			});
		} catch (RequestException e) {
			logger.info("Error connecting to garage rest service: " + e.getMessage());
			invokeStateChangeHandlersOfError("Connection Issue");
		}

	}

	@Override
	public void open() {
		Geolocation geo = Geolocation.getIfSupported();
		if( geo == null ) {
			Window.alert("Your browser must support Location Access to use this feature");
			invokeStateChangeHandlersOfError("BROWSER UNSUPPORTED");
			return;
		}
		Storage storage = Storage.getLocalStorageIfSupported();
		if( storage != null ) {
			String loc = storage.getItem("location");
			if( loc != null ) {
				if( loc.matches("[-+]?d+\\.d+,[-+]?d+\\.d+") ) {
					String[] coords = loc.split(",");
					HashMap<String,String> params = new HashMap<String,String>();
					params.put("lat", coords[0]);
					params.put("lng", coords[1]);
					logger.info("Using coordinates from local storage: " + loc);
					actionRequest("open", params);
					return;
				} else if( loc.startsWith("ERROR:") ) {
					invokeStateChangeHandlersOfError(loc);
					return;
				}
			}
		}
		PositionOptions opts = new PositionOptions();
		opts.setHighAccuracyEnabled(false);
		opts.setMaximumAge(300000);
		opts.setTimeout(2000);
		geo.getCurrentPosition(new Callback<Position, PositionError>() {

			@Override
			public void onSuccess(Position result) {
				HashMap<String,String> params = new HashMap<String,String>();
				params.put("lat", Double.toString(result.getCoordinates().getLatitude()));
				params.put("lng", Double.toString(result.getCoordinates().getLongitude()));
				actionRequest("open", params);
			}

			@Override
			public void onFailure(PositionError reason) {
				switch (reason.getCode() ) {
				case PositionError.PERMISSION_DENIED:
					//					Window.alert("You must allow access to location to use this feature");
					invokeStateChangeHandlersOfError("PERMISSION DENIED");
					break;
				default:
					invokeStateChangeHandlersOfError("POSITION UNAVAILABLE");
					break;
				}
			}
		}, opts);
	}

	@Override
	public void stop() {
		actionRequest("stop", new HashMap<String,String>());
	}

	@Override
	public void close() {
		actionRequest("close", new HashMap<String,String>());
	}

	@Override
	public void refresh() {
		actionRequest("state", new HashMap<String,String>());
	}

	/*
	 * Takes in a trusted JSON String and evals it.
	 * @param JSON String that you trust
	 * @return JavaScriptObject that you can cast to an Overlay Type
	 */
	public static native GarageDoorStateResponseJso parseStateResponse(String jsonStr) /*-{
	  return eval('(' + jsonStr + ')');
	}-*/;

	public static native GarageDoorAuthResponseJso parseAuthResponse(String jsonStr) /*-{
	  return eval('(' + jsonStr + ')');
	}-*/;

	private String buildUrl(final String path, final HashMap<String, String> params) {
		StringBuilder sb = new StringBuilder(baseUrl).append(path);
		String sep = "?";
		for( String key : params.keySet() ) {
			sb.append(sep).append(key).append("=").append(params.get(key));
			sep = "&";
		}

		String url = sb.toString();
		logger.info("url to call: " + url );
		return url;
	}

	private RequestBuilder buildRequest(final String path,	final HashMap<String, String> params) {
		String url = buildUrl(path, params);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, URL.encode(url));
		builder.setHeader("Content-Type", "application/json");
		return builder;
	}


	@Override
	public void addStateChangedHandler(StateChangedHandler handler) {
		this.stateChangedHandlers.add(handler);
	}

	protected void invokeStateChangedHandlers(String stateString) {
		GarageState state = GarageState.fromText(stateString);
		if( state == null ) {
			invokeStateChangeHandlersOfError("Invalid state: " + state);
			return;
		}
		for( StateChangedHandler handler : stateChangedHandlers ) {
			handler.onStateChanged(state);
		}
	}

	void invokeStateChangeHandlersOfError(String error) {
		for( StateChangedHandler handler : stateChangedHandlers ) {
			handler.onError(error);
		}
	}

	@Override
	public void logout() {
		Window.Location.assign("/oauth2/sign_in");
	}

}
