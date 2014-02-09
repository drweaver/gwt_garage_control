package drweaver.garagecontrol.client.view.mobile;

import drweaver.garagecontrol.client.view.GarageControlOptionsView;
import drweaver.garagecontrol.client.view.GarageControlView;
import drweaver.garagecontrol.client.view.ViewFactory;

public class ViewFactoryMobile implements ViewFactory {

	@Override
	public GarageControlView getGarageControlView() {
		return new GarageControlViewMobile();
	}

	@Override
	public GarageControlOptionsView getGarageControlOptionsView() {
		return new GarageControlOptionsViewMobile();
	}

}
