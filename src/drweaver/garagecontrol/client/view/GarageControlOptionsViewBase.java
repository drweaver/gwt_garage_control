package drweaver.garagecontrol.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;

public class GarageControlOptionsViewBase extends Composite implements GarageControlOptionsView {

	@UiField
	public Button buttonRefresh;

	@UiField
	public Button buttonLogout;

	Presenter presenter = null;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("buttonRefresh")
	public void onClick(ClickEvent e) {
		if( presenter != null ) {
			presenter.refresh();
		}
	}

	@UiHandler("buttonLogout")
	public void onLogoutClick(ClickEvent e) {
		if( presenter != null ) {
			presenter.logout();
		}
	}

}
