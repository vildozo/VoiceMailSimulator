import java.util.ArrayList;
import java.util.List;

/**
   Connects a phone to the mail system. The purpose of this
   class is to keep track of the state of a connection, since
   the phone itself is just a source of individual key presses.
*/
public class Connection
{
	
	  
	   MailSystem system;
	   Mailbox currentMailbox;
	   private String currentRecording;
	   String accumulatedKeys;
	   private UserInterface phone;
	   private UserInterface phone2;   
	   int state;

	   private static final int DISCONNECTED = 0;
	   private static final int CONNECTED = 1;
	   static final int RECORDING = 2;
	   static final int MAILBOX_MENU = 3;
	   private static final int MESSAGE_MENU = 4;
	   private static final int CHANGE_PASSCODE = 5;
	   private static final int CHANGE_GREETING = 6;

	   private static final String INITIAL_PROMPT = 
	         "Enter mailbox number followed by #";      
	   static final String MAILBOX_MENU_TEXT = 
	         "Enter 1 to listen to your messages\n"
	         + "Enter 2 to change your passcode\n"
	         + "Enter 3 to change your greeting";
	   private static final String MESSAGE_MENU_TEXT = 
	         "Enter 1 to listen to the current message\n"
	         + "Enter 2 to save the current message\n"
	         + "Enter 3 to delete the current message\n"
	         + "Enter 4 to return to the main menu";
	   
   /**
      Construct a Connection object.
      @param s a MailSystem object
      @param p a Telephone object
   */
	List<UserInterface> uis;
	
	
   public Connection(MailSystem s, List uis)
   {
	  this.uis = uis;
      system = s;
      resetConnection();
   }
   
   public void addUI(UserInterface ui){
	   uis.add(ui);
   }

   /**
      Respond to the user's pressing a key on the phone touchpad
      @param key the phone key pressed by the user
   */
   public void dial(String key)
   {
      if (state == CONNECTED)
         new CONNECTED().connect(key, this);
      else if (state == RECORDING)
         new RecordingState().login(key, this);
      else if (state == CHANGE_PASSCODE)
         changePasscode(key);
      else if (state == CHANGE_GREETING)
         changeGreeting(key);
      else if (state == MAILBOX_MENU)
         mailboxMenu(key);
      else if (state == MESSAGE_MENU)
         messageMenu(key);
   }

   /**
      Record voice.
      @param voice voice spoken by the user
   */
   public void record(String voice)
   {
      if (state == RECORDING || state == CHANGE_GREETING)
         currentRecording += voice;
   }

   /**
      The user hangs up the phone.
   */
   public void hangup()
   {
      if (state == RECORDING)
         currentMailbox.addMessage(new Message(currentRecording));
      resetConnection();
   }

   /**
      Reset the connection to the initial state and prompt
      for mailbox number
   */
   private void resetConnection()
   {
      currentRecording = "";
      accumulatedKeys = "";
      state = CONNECTED;
      
	  speakToAllUIs(INITIAL_PROMPT);
		
   }

	void speakToAllUIs(String output) {
		for(UserInterface ui : uis) 
			ui.speak(output);
	}

   /**
      Change passcode.
      @param key the phone key pressed by the user
   */
   private void changePasscode(String key)
   {
      if (key.equals("#"))
      {
         currentMailbox.setPasscode(accumulatedKeys);
         state = MAILBOX_MENU;
//         phone.speak(MAILBOX_MENU_TEXT);
         speakToAllUIs(MAILBOX_MENU_TEXT);
         accumulatedKeys = "";
      }
      else
         accumulatedKeys += key;
   }

   /**
      Change greeting.
      @param key the phone key pressed by the user
   */
   private void changeGreeting(String key)
   {
      if (key.equals("#"))
      {
         currentMailbox.setGreeting(currentRecording);
         currentRecording = "";
         state = MAILBOX_MENU;
//         phone.speak(MAILBOX_MENU_TEXT);
         speakToAllUIs(MAILBOX_MENU_TEXT);
      }
   }

   /**
      Respond to the user's selection from mailbox menu.
      @param key the phone key pressed by the user
   */
   private void mailboxMenu(String key)
   {
      if (key.equals("1"))
      {
         state = MESSAGE_MENU;
//         phone.speak(MESSAGE_MENU_TEXT);
         speakToAllUIs(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("2"))
      {
         state = CHANGE_PASSCODE;
//         phone.speak("Enter new passcode followed by the # key");
         speakToAllUIs("Enter new passcode followed by the # key");
      }
      else if (key.equals("3"))
      {
         state = CHANGE_GREETING;
//         phone.speak("Record your greeting, then press the # key");
         speakToAllUIs("Record your greeting, then press the # key");
      }
   }

   /**
      Respond to the user's selection from message menu.
      @param key the phone key pressed by the user
   */
   private void messageMenu(String key)
   {
      if (key.equals("1"))
      {
         String output = "";
         Message m = currentMailbox.getCurrentMessage();
         if (m == null) output += "No messages." + "\n";
         else output += m.getText() + "\n";
         output += MESSAGE_MENU_TEXT;
//         phone.speak(output);
         speakToAllUIs(output);
      }
      else if (key.equals("2"))
      {
         currentMailbox.saveCurrentMessage();
//         phone.speak(MESSAGE_MENU_TEXT);
         speakToAllUIs(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("3"))
      {
         currentMailbox.removeCurrentMessage();
//         phone.speak(MESSAGE_MENU_TEXT);
         speakToAllUIs(MESSAGE_MENU_TEXT);
      }
      else if (key.equals("4"))
      {
         state = MAILBOX_MENU;
//         phone.speak(MAILBOX_MENU_TEXT);
         speakToAllUIs(MAILBOX_MENU_TEXT);
      }
   }
   
 

public boolean isConnected() {
    return state == CONNECTED;
 }

 public boolean isRecording() {
    return state == RECORDING;
 }

 public boolean isInMailBoxMenu() {
    return state == MAILBOX_MENU;
 }

 public boolean isInMessageMenu() {
    return state == MESSAGE_MENU;
 }

 public boolean isInChangePassword() {
    return state == CHANGE_PASSCODE;
 }

 public boolean isInChangeGreeting() {
    return state == CHANGE_GREETING;
 }

}











