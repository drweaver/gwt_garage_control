package drweaver.garagecontrol.client.view.mobile;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;

import drweaver.garagecontrol.client.view.GarageControlOptionsViewBase;

public class GarageControlOptionsViewMobile extends GarageControlOptionsViewBase {

	private static GarageControlOptionsViewMobileUiBinder uiBinder = GWT.create(GarageControlOptionsViewMobileUiBinder.class);

	interface GarageControlOptionsViewMobileUiBinder extends	UiBinder<Widget, GarageControlOptionsViewMobile> {}

	public GarageControlOptionsViewMobile() {
		initWidget(uiBinder.createAndBindUi(this));

	}


}
