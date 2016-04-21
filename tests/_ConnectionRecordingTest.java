import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class _ConnectionRecordingTest {

    Mailbox currentMailbox;
    MailSystem mailSystem;
    Telephone phone;
    Connection connection;

    private static String MAILBOX_MENU_TEXT = "Enter 1 to listen to your messages\n"
            + "Enter 2 to change your passcode\n"
            + "Enter 3 to change your greeting";

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
    public void inLogItShouldVerifiPassShowMessageAndSetStateToMailBoxMenu() {

        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        when(currentMailbox.checkPasscode("2")).thenReturn(true);

        connection.dial("1");
        connection.dial("#");
        connection.dial("2");
        connection.dial("#");
        verify(phone).speak(MAILBOX_MENU_TEXT);
        assert (connection.isInMailBoxMenu());
    }

    @Test
    public void inLogItShouldVerifiPassReturnFalseAndShowErrorMessage() {
        when(mailSystem.findMailbox("1")).thenReturn(currentMailbox);
        when(currentMailbox.checkPasscode("2")).thenReturn(false);

        connection.dial("1");
        connection.dial("#");
        connection.dial("2");
        connection.dial("#");
        verify(phone).speak("Incorrect passcode. Try again!");
    }
}