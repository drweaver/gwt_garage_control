package drweaver.garagecontrol.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface GarageControlOptionsView extends IsWidget {

	void setPresenter(Presenter presenter);

	interface Presenter {

		void logout();
		void refresh();

	}

}
