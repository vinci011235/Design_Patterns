package patterns.template;

public abstract class CadastroController {
	
	//Pattern 'Template' to class 'CadastroCursoController' and 'CadastroModuloController'
	public abstract void doInsert();
	public abstract void doUpdate();
	public abstract void doDelete();
	public abstract void doSave();
	public abstract void doCancel();
	public abstract void doFind();
	public abstract void doExit();
}
