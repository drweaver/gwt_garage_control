package drweaver.garagecontrol.client.service;

import drweaver.garagecontrol.client.GarageState;


public interface GarageService {

	void open();
	void close();
	void stop();
	void refresh();
	void logout();

	void addStateChangedHandler(StateChangedHandler handler);

	interface StateChangedHandler {
		void onStateChanged(GarageState state);
		void onError(String error);
	}

}
