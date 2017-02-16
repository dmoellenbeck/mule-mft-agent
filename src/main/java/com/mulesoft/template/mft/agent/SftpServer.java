package com.mulesoft.template.mft.agent;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;



import org.apache.sshd.server.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.cipher.BuiltinCiphers;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.server.scp.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.session.ServerSession;
import org.apache.sshd.server.subsystem.sftp.SftpSubsystemFactory;
import org.apache.sshd.server.shell.ProcessShellFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;


public class SftpServer {
	private int port = 3002;
	private String keyFile = "./rsakey.pem";
	private String passwd = "test";
	private SshServer sshd;
		
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
	public SftpServer() {
		init();
	}
	
	public SftpServer(int _port, String _passwd, boolean autoStart) {
		this.setPort(_port);
		this.setPasswd(_passwd);
		init();
		if (autoStart) this.start();
	}
	
	
	public void init() {
		sshd = SshServer.setUpDefaultServer();
		sshd.setPort(port);

		SimpleGeneratorHostKeyProvider kp = new SimpleGeneratorHostKeyProvider();
		kp.setFile(new File(keyFile));
		kp.setAlgorithm(KeyUtils.RSA_ALGORITHM);
		sshd.setKeyPairProvider(kp);
		sshd.setCipherFactories(Arrays.asList(BuiltinCiphers.aes256ctr, BuiltinCiphers.aes192ctr, BuiltinCiphers.aes128ctr));
		SftpSubsystemFactory factory = new SftpSubsystemFactory();
		sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>>asList(factory));
		sshd.setCommandFactory(new ScpCommandFactory());
		sshd.setShellFactory(new ProcessShellFactory(new String[] { "/bin/sh", "-i", "-l" }));
		sshd.setPasswordAuthenticator(PasswordAuthenticator());
	}
	


	public void start() {
		try {
			sshd.start();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}
 
	public void stop()  {
		try {
			sshd.stop();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private PasswordAuthenticator PasswordAuthenticator() {
		return new PasswordAuthenticator() {
			@Override
			public boolean authenticate(String arg0, String arg1, ServerSession arg2) {
				// TODO Auto-generated method stub
				if (arg1.equalsIgnoreCase(getPasswd())) return true;
				else return false;
			}};
	}
	
}