package drweaver.garagecontrol.client.view.mobile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

import drweaver.garagecontrol.client.view.GarageControlViewBase;

public class GarageControlViewMobile extends GarageControlViewBase {

	private static GarageControlViewMobileUiBinder uiBinder = GWT.create(GarageControlViewMobileUiBinder.class);

	interface GarageControlViewMobileUiBinder extends UiBinder<Widget, GarageControlViewMobile> {}

	public GarageControlViewMobile() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
