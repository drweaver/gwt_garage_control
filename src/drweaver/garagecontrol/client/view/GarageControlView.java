package drweaver.garagecontrol.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface GarageControlView extends IsWidget {

	public void setState(String state);

	public void setPresenter(Presenter presenter);

	public void refresh();

	void setProgressState(boolean inProgress);

	boolean isInProgress();

	interface Presenter {
		void open();
		void stop();
		void close();
		void refresh();
	}




}
