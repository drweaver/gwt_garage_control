package drweaver.garagecontrol.client;

public enum GarageState {

	OPENED("opened", false),
	CLOSED("closed", false),
	OPENING("opening", true),
	CLOSING("closing", true),
	STOPPED_ON_OPENING("stopped on opening", false),
	STOPPED_ON_CLOSING("stopped on closing", false),
	;

	private final String text;
	private final boolean moving;

	private GarageState(String text, boolean moving) {
		this.text = text;
		this.moving = moving;
	}

	/**
	 * 
	 * @param text
	 * @return the corresponding GarageState or null
	 */
	public static GarageState fromText(String text) {
		for( GarageState state : GarageState.values() ) {
			if( text.equals(state.text) ) {
				return state;
			}
		}
		return null;
	}

	public boolean isMoving() {
		return moving;
	}

	@Override
	public String toString() {
		return text;
	}

}
