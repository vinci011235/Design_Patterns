package patterns.singleton;

import br.edu.ifpr.treinamento.modelo.service.JpaService;

//Singleton que retorna uma instancia para JpaService()
//Utilizado na classe 'PrincipalController_GridImage'

public class JpaConfig {
	private static JpaService instance = null;
	
	public JpaConfig() {}
	
	public static JpaService getInstance() {
		if(instance == null) {
			instance = new JpaService();
		}
		return instance;
	}
}
