package drweaver.garagecontrol.client.service;

import com.google.gwt.core.client.JavaScriptObject;

public class GarageDoorAuthResponseJso extends JavaScriptObject {

	protected GarageDoorAuthResponseJso() {}

	public final native String getAuth() /*-{ return this.auth; }-*/;

}
