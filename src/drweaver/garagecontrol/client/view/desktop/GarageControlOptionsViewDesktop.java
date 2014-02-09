package drweaver.garagecontrol.client.view.desktop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

import drweaver.garagecontrol.client.view.GarageControlOptionsViewBase;

public class GarageControlOptionsViewDesktop extends GarageControlOptionsViewBase {

	private static GarageControlOptionsViewDesktopUiBinder uiBinder = GWT.create(GarageControlOptionsViewDesktopUiBinder.class);

	interface GarageControlOptionsViewDesktopUiBinder extends	UiBinder<Widget, GarageControlOptionsViewDesktop> {}

	public GarageControlOptionsViewDesktop() {
		initWidget(uiBinder.createAndBindUi(this));

	}


}
