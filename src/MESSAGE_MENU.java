
public class MESSAGE_MENU {
	public MESSAGE_MENU(){}

	/**
	      Respond to the user's selection from message menu.
	      @param key the phone key pressed by the user
	 * @param connection TODO
	   */
	   void messageMenu(String key, Connection connection)
	   {
	      if (key.equals("1"))
	      {
	         String output = "";
	         Message m = connection.currentMailbox.getCurrentMessage();
	         if (m == null) output += "No messages." + "\n";
	         else output += m.getText() + "\n";
	         output += Connection.MESSAGE_MENU_TEXT;
	//         phone.speak(output);
	         connection.speakToAllUIs(output);
	      }
	      else if (key.equals("2"))
	      {
	         connection.currentMailbox.saveCurrentMessage();
	//         phone.speak(MESSAGE_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MESSAGE_MENU_TEXT);
	      }
	      else if (key.equals("3"))
	      {
	         connection.currentMailbox.removeCurrentMessage();
	//         phone.speak(MESSAGE_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MESSAGE_MENU_TEXT);
	      }
	      else if (key.equals("4"))
	      {
	         connection.state = Connection.MAILBOX_MENU;
	//         phone.speak(MAILBOX_MENU_TEXT);
	         connection.speakToAllUIs(Connection.MAILBOX_MENU_TEXT);
	      }
	   }
}
