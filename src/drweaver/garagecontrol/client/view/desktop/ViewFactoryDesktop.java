package drweaver.garagecontrol.client.view.desktop;

import drweaver.garagecontrol.client.view.GarageControlOptionsView;
import drweaver.garagecontrol.client.view.GarageControlView;
import drweaver.garagecontrol.client.view.ViewFactory;

public class ViewFactoryDesktop implements ViewFactory {

	@Override
	public GarageControlView getGarageControlView() {
		return new GarageControlViewDesktop();
	}

	@Override
	public GarageControlOptionsView getGarageControlOptionsView() {
		return new GarageControlOptionsViewDesktop();
	}

}
