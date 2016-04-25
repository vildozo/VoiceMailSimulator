
public class CHANGE_GREETING implements ConnectedState{
	
	public CHANGE_GREETING(){}

	/**
	      Change greeting.
	      @param key the phone key pressed by the user
	 * @param connection TODO
	   */
	   public void dial(String key, Connection connection)
	   {
	      if (key.equals("#"))
	      {
	         connection.currentMailbox.setGreeting(connection.currentRecording);
	         connection.currentRecording = "";
	         connection.state = Connection.MAILBOX_MENU;
	//         phone.speak(MAILBOX_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MAILBOX_MENU_TEXT);
	      }
	   }

}
