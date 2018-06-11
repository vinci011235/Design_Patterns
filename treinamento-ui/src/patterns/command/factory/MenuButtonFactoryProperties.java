package patterns.command.factory;

import java.io.InputStream;
import java.util.Properties;

import patterns.command.MenuButtonCommand;

public class MenuButtonFactoryProperties implements MenuButtonFactory {
	private final String MENU_FILE = "/br/edu/ifpr/treinamento/command/resources/menu.properties";

    private Properties prop = new Properties();
    
    public MenuButtonFactoryProperties() {       
       InputStream is = getClass().getResourceAsStream(MENU_FILE);
       try {
          prop.load(is);
          is.close();
       }
       catch (Exception ex) { ex.printStackTrace(); }
    }
    
	@Override
	public MenuButtonCommand createCommandFactory(String cmd) {
		MenuButtonCommand command   = null;
	       String  className = prop.getProperty(cmd);
	       
	       try {
	          Class<?>  clazz = Class.forName(className);
	          Object obj   = clazz.newInstance();

	          command = (MenuButtonCommand) obj;
	       }
	       catch (Exception ex) { ex.printStackTrace(); }
	       
	       return command;
	}

}
