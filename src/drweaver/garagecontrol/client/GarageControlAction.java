package drweaver.garagecontrol.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import drweaver.garagecontrol.client.service.GarageService;
import drweaver.garagecontrol.client.service.GarageService.StateChangedHandler;
import drweaver.garagecontrol.client.view.GarageControlOptionsView;
import drweaver.garagecontrol.client.view.GarageControlView;

public class GarageControlAction implements StateChangedHandler, GarageControlOptionsView.Presenter, GarageControlView.Presenter {


	final GarageService service = GWT.create(GarageService.class);


	private final GarageControlView controlView;
	private final GarageControlOptionsView optionsView;

	private final Timer refreshTimer = new Timer() {@Override public void run() {refreshIfNotInProgress();}};
	private boolean refreshTimerEnabled = false;
	private int errorCount = 0;

	public GarageControlAction(GarageControlView controlView, GarageControlOptionsView optionsView) {
		this.controlView = controlView;
		this.optionsView = optionsView;
		controlView.setPresenter(this);
		optionsView.setPresenter(this);
		service.addStateChangedHandler(this);
	}

	@Override
	public void logout() {
		service.logout();
		stopRefresh();
	}

	@Override
	public void refresh() {
		refreshIfNotInProgress();
		startRefresh();
	}

	private void refreshIfNotInProgress() {
		if( !controlView.isInProgress() ) {
			controlView.setProgressState(true);
			service.refresh();
		}
	}

	@Override
	public void onStateChanged(GarageState state) {
		errorCount = 0;
		if( state != null ) {
			if( state.isMoving() ) {
				startRefresh();
			} else {
				stopRefresh();
			}
			controlView.setErrorState(false);
			controlView.setState(state.toString());
		} else {
			stopRefresh();
		}
	}

	@Override
	public void open() {
		service.open();
	}

	@Override
	public void stop() {
		service.stop();

	}

	@Override
	public void close() {
		service.close();
	}

	@Override
	public void onError(String error) {
		errorCount++;
		if( errorCount % 10 == 0 ) {
			// don't refresh forever
			stopRefresh();
		}
		controlView.setErrorState(true);
		controlView.setProgressState(false);
		controlView.setButtonsEnabled(true);
		if( errorCount % 3 == 0 &&  error.equals("0") ) {
			// likely the auth is old and needs refreshing
			Window.Location.reload();
		}
	}

	private void stopRefresh() {
		if( refreshTimerEnabled ) {
			refreshTimer.cancel();
			refreshTimerEnabled = false;
		}
	}

	private void startRefresh() {
		if( !refreshTimerEnabled ) {
			refreshTimer.scheduleRepeating(3000);
			refreshTimerEnabled = true;
		}
	}


}

