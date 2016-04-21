import static org.junit.Assert.*;

import org.junit.Test;


import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class _ConnectionNewTest {

    Mailbox currentMailbox;
    MailSystem mailSystem;
    Telephone phone;
    Connection connection;

    private static String MAILBOX_MENU_TEXT = "Enter 1 to listen to your messages\n"
                    + "Enter 2 to change your passcode\n"
                    + "Enter 3 to change your greeting";
    private static String MESSAGE_MENU_TEXT = "Enter 1 to listen to the current message\n"
                    + "Enter 2 to save the current message\n"
                    + "Enter 3 to delete the current message\n"
                    + "Enter 4 to return to the main menu";

    @Before
    public void setup() {
        currentMailbox = mock(Mailbox.class);
        mailSystem = mock(MailSystem.class);
        phone = mock(Telephone.class);
        
        List<UserInterface> lista = new ArrayList<UserInterface>();
        lista.add(phone);
        
        connection = new Connection(mailSystem, lista);
    }

    @Test
    public void newConnectionShouldShowInitialPromotAndSetStateToConnected() {
        verify(phone).speak("Enter mailbox number followed by #");
        assert (connection.isConnected());
    }

    @Test
    public void asConnectedDial1shouldGetMailBoxSpeakGreetingAndSetStateToRecording() {
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        connection.dial("1");
        connection.dial("#");
        verify(phone).speak(currentMailbox.getGreeting());
        assert (connection.isRecording());
    }

    @Test
    public void asConnectedDial10shouldGetNullSpeakErrorMsjAndSetStateToRecording() {
        when(mailSystem.findMailbox("10")).thenReturn(null);
        connection.dial("1");
        connection.dial("#");
        verify(phone).speak("Incorrect mailbox number. Try again!");
    }

    @Test
    public void afterRecordingHangoutShouldSaveAMessageAndResetConnection(){
        String msgText = "This is a new message.";
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        when(currentMailbox.checkPasscode("1")).thenReturn(true);
        when(currentMailbox.getCurrentMessage()).thenReturn(new Message(msgText));

        connection.dial("1");
        connection.dial("#");
        connection.dial(msgText);
        connection.hangup();
        connection.dial("1");
        connection.dial("#");
        connection.dial("1");
        connection.dial("#");
        connection.dial("1");
        connection.dial("1");
        verify(phone).speak(msgText+"\n"+MESSAGE_MENU_TEXT);
    }
}
