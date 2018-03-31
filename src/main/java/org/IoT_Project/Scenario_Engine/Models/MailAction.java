package org.IoT_Project.Scenario_Engine.Models;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailAction extends Action{


	static ActionEventProto mailActionProto = new ActionEventProto((short)0,
																  "Email",
																  "Sending mail with subjected Scenario/Action/Event details",
																  new LinkedList<String>(Arrays.asList("STRING", "STRING")),
																  null,
																  new LinkedList<String>(Arrays.asList("Email-Address", "Message")),
																  null,
																  null,
																  null,
																  (short)-1,
																  null,
																  false);
	public MailAction()
	{
		super();
	}
	
	public MailAction(short Action_id,
				  short Action_deviceSerialNum,
				  List<String> Action_parameters,
				  ActionEventProto Action_descriptor)
	{
		super(Action_id, Action_deviceSerialNum, Action_parameters, MailAction.mailActionProto);
	}
	
	public MailAction(Action i_action) throws Exception
	{
		super(i_action);
	}
	
	/**
	  * SimpleAuthenticator is used to do simple authentication
	  * when the SMTP server requires it.
	  */

	class SMTPAuthenticator extends Authenticator {

	    private static final String SMTP_AUTH_USER = "iotp364@gmail.com";
	    private static final String SMTP_AUTH_PASSWORD = "Project12345";

	    public PasswordAuthentication getPasswordAuthentication () {
	        String username = SMTP_AUTH_USER;
	        String password = SMTP_AUTH_PASSWORD;

	        return new PasswordAuthentication( username,  password );
	    }
	}
	
	@Override
	public int toggleAction() throws Exception {
		
		String from = "iotp364@gmail.com";
		String to = this.getParameters().get(0);
		String messageToDeliver = this.getParameters().get(1) + System.lineSeparator() + "please dont respond to this mail.";
		
		Authenticator authenticator = new SMTPAuthenticator ();
		
		String host = "smtp.gmail.com";
		
		Properties props = System.getProperties();
		props.put ( "mail.smtp.host", host );
        //props.put ( "mail.smtp.starttls.enable", "true" );
        //props.put ( "mail.smtp.port", "587" );
        props.put ( "mail.smtp.auth", "true" );
		//props.setProperty("mail.smtp.host", host);
		 Session session = Session.getDefaultInstance(props, authenticator);
		 try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Scenario Engine invoked");

	         // Now set the actual message
	         message.setText(messageToDeliver);

	         // Send message
	         Transport.send(message);
	      } catch (MessagingException mex) {
	         throw mex;
	      }
		 return 200;
	}
}
