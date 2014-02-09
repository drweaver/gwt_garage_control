package drweaver.garagecontrol.client.service;

import com.google.gwt.core.client.JavaScriptObject;


public class GarageDoorStateResponseJso extends JavaScriptObject {

	protected GarageDoorStateResponseJso() {}

	public final native String getState() /*-{ return this.state; }-*/;
	public final native String getAuth() /*-{ return this.auth; }-*/;


}
