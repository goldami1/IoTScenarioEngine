package db;

import engine.User;

public interface IDBHandler {

	public IDBHandler getInstance();
	public boolean connectionAuthentication(User i_user);
	
}

