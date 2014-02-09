package drweaver.garagecontrol.client.service;

import java.util.logging.Logger;

import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

import drweaver.garagecontrol.client.GarageState;

public class MockGarageService implements GarageService {

	Logger logger = Logger.getLogger(MockGarageService.class.getName());
	private StateChangedHandler handler;

	@Override
	public void open() {
		logger.info("Execute Garage Opening");
		Timer timer = new Timer() {
			@Override
			public void run() {
				handler.onStateChanged(GarageState.OPENING);
			}
		};
		timer.schedule(500);
	}

	@Override
	public void stop() {
		logger.info("Execute Garage Stopped");
		Timer timer = new Timer() {
			@Override
			public void run() {
				handler.onStateChanged(GarageState.STOPPED_ON_OPENING);

			}
		};
		timer.schedule(500);
	}

	@Override
	public void close() {
		logger.info("Execute Garage Closing");
		Timer timer = new Timer() {
			@Override
			public void run() {
				handler.onError("Can't Close");
			}
		};
		timer.schedule(500);
	}

	@Override
	public void refresh() {
		logger.info("Execute Garage state ");
		Timer timer = new Timer() {
			@Override
			public void run() {
				handler.onStateChanged(GarageState.values()[Random.nextInt(GarageState.values().length)]);
			}
		};
		timer.schedule(200);
	}

	@Override
	public void addStateChangedHandler(StateChangedHandler handler) {
		this.handler = handler;

	}

	@Override
	public void logout() {
		logger.info("Logged out");
	}




}
