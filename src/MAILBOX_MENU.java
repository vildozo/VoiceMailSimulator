
public class MAILBOX_MENU  implements ConnectedState{{
	public MAILBOX_MENU() {}

	/**
	      Respond to the user's selection from mailbox menu.
	      @param key the phone key pressed by the user
	 * @param connection TODO
	   */
	   public void dial(String key, Connection connection)
	   {
	      if (key.equals("1"))
	      {
	         connection.state = Connection.MESSAGE_MENU;
	//         phone.speak(MESSAGE_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MESSAGE_MENU_TEXT);
	      }
	      else if (key.equals("2"))
	      {
	         connection.state = Connection.CHANGE_PASSCODE;
	//         phone.speak("Enter new passcode followed by the # key");
	         connection.speakToAllUIs("Enter new passcode followed by the # key");
	      }
	      else if (key.equals("3"))
	      {
	         connection.state = Connection.CHANGE_GREETING;
	//         phone.speak("Record your greeting, then press the # key");
	         connection.speakToAllUIs("Record your greeting, then press the # key");
	      }
	   }

}
