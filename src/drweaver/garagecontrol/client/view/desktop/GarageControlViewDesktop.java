package drweaver.garagecontrol.client.view.desktop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

import drweaver.garagecontrol.client.view.GarageControlViewBase;

public class GarageControlViewDesktop extends GarageControlViewBase {

	private static GarageControlViewDesktopUiBinder uiBinder = GWT.create(GarageControlViewDesktopUiBinder.class);

	interface GarageControlViewDesktopUiBinder extends UiBinder<Widget, GarageControlViewDesktop> {}

	public GarageControlViewDesktop() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
