package patterns.command.factory;

import java.io.InputStream;
import java.util.Properties;

import patterns.command.Command;

public class CommandFactoryProperties implements CommandFactory {
    private final String MODULO_FILE = "/br/edu/ifpr/treinamento/patterns/command/resources/modulo.properties";

    private Properties prop = new Properties();
    
    public CommandFactoryProperties() {       
       InputStream is = getClass().getResourceAsStream(MODULO_FILE);
       try {
          prop.load(is);
          is.close();
       }
       catch (Exception ex) { ex.printStackTrace(); }
    }
    
    public Command createCommandFactory(String cmd) {
       Command command   = null;
       String  className = prop.getProperty(cmd);
       
       try {
          Class<?>  clazz = Class.forName(className);
          Object obj   = clazz.newInstance();

          command = (Command) obj;
       }
       catch (Exception ex) { ex.printStackTrace(); }
       
       return command;
    }
}
