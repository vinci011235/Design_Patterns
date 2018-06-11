package patterns.command.factory;

import patterns.command.MenuButtonCommand;

public interface MenuButtonFactory {
	public static final String INSERT_OPTION = "Insert"; 
	public static final String UPDATE_OPTION = "Update";
	public static final String DELETE_OPTION = "Delete";
	public static final String SAVE_OPTION = "Save";
	public static final String CANCEL_OPTION = "Cancel";
	public static final String FIND_OPTION = "Find";
	public static final String EXIT_OPTION = "Exit";
	
	public MenuButtonCommand createCommandFactory(String command);
}