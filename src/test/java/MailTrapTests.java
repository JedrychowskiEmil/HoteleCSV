import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;

public class MailTrapTests {
    MailTrap mailTrap;

    @Before
    public void init() {
        this.mailTrap = new MailTrap("d31b250e43ecec", "425c75277df389");
    }

    @Test
    public void addMessage_ParamSimpleString_contentCorrectlyAdded() {
        String simpleString = "SimpleString";
        String messageString = "";
        mailTrap.addMessage(simpleString);
        try {
            messageString = mailTrap.getMimeBodyParts().get(0).getContent().toString();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(simpleString, messageString);
    }

    @Test
    public void addAttachment_ParamSimpleStringInFile_contentCorrectlyAdded() {
        System.out.println("getMimeBodyParts size Before: " + mailTrap.getMimeBodyParts().size());

        String simpleString = "SimpleString";
        TmpFiles tmpFiles = new TmpFiles();
        tmpFiles.createAndAddFile(simpleString);
        File file = tmpFiles.getFiles().get(0);
        mailTrap.addAttachment(file);

        String s = "";
        try {
            s = (String) mailTrap.getMimeBodyParts().get(0).getContent();
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(simpleString, s);
        tmpFiles.clean();
        System.out.println("getMimeBodyParts size After: " + mailTrap.getMimeBodyParts().size());
    }


}
