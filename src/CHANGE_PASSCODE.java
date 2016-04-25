
public class CHANGE_PASSCODE  implements ConnectedState{{
 
	public CHANGE_PASSCODE(){}

	/**
	      Change passcode.
	      @param key the phone key pressed by the user
	 * @param connection TODO
	   */
	   public void dial(String key, Connection connection)
	   {
	      if (key.equals("#"))
	      {
	         connection.currentMailbox.setPasscode(connection.accumulatedKeys);
	         connection.state = Connection.MAILBOX_MENU;
	//         phone.speak(MAILBOX_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MAILBOX_MENU_TEXT);
	         connection.accumulatedKeys = "";
	      }
	      else
	         connection.accumulatedKeys += key;
	   }
}
