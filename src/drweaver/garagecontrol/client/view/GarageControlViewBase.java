package drweaver.garagecontrol.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

import drweaver.garagecontrol.client.res.Resources;

public class GarageControlViewBase extends Composite implements GarageControlView {

	public interface Style extends CssResource {
		String hidden();
		String inError();
	}

	@UiField
	public Button buttonOpen;

	@UiField
	public Button buttonStop;

	@UiField
	public Button buttonClose;

	@UiField
	public Label labelState;

	@UiField
	public Image imageSpinner;

	@UiField
	public Resources res;

	@UiField
	public Style style;

	private Presenter presenter = null;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setProgressState(boolean inProgress) {
		if( inProgress ) {
			imageSpinner.removeStyleName(style.hidden());
		} else {
			imageSpinner.addStyleName(style.hidden());
		}
	}

	@Override
	public boolean isInProgress() {
		return !imageSpinner.getStyleName().contains(style.hidden());
	}

	@Override
	private void setButtonsEnabled(boolean enabled) {
		buttonOpen.setEnabled(enabled);
		buttonStop.setEnabled(enabled);
		buttonClose.setEnabled(enabled);
	}

	@UiHandler("buttonOpen")
	public void onClickOpen(ClickEvent e) {
		open();
	}

	public void open() {
		setProgressState(true);
		setButtonsEnabled(false);
		if( presenter != null ) {
			presenter.open();
		}
	}

	@UiHandler("buttonStop")
	public void onClickStop(ClickEvent e) {
		stop();
	}

	public void stop() {
		setProgressState(true);
		setButtonsEnabled(false);
		presenter.stop();
	}

	@UiHandler("buttonClose")
	public void onClickClose(ClickEvent e) {
		close();
	}

	public void close() {
		setProgressState(true);
		setButtonsEnabled(false);
		if( presenter != null ) {
			presenter.close();
		}
	}

	@Override
	public void refresh() {
		if( !isInProgress() ) {
			setProgressState(true);
			if( presenter != null ) {
				presenter.refresh();
			}
		}
	}

	@Override
	public void setState(String state) {
		setProgressState(false);
		setButtonsEnabled(true);
		labelState.setText(state);
	}
	
	@Override
	public void setErrorState(boolean errorState) {		
		if( errorState ) {
			labelState.addStyleName(style.inError());
		} else {
			labelState.removeStyleName(style.inError());
		}
	}
}
