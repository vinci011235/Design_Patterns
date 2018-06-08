package patterns.abstractfactory;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import patterns.factory.CadastroFactory;
import patterns.strategy.DataEntryState;

public class CursoAbstractFactory implements CadastroFactory {

	@Override
	public void onMenuButtonBarAction(ActionEvent ev) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initState(ScreenManager sceneManager, JpaService jpaService) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initBindings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doInsert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doExit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeState(DataEntryState newState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateInsert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateDelete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateCancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeStateFind() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean showAlert(AlertType alertType, String title, String content) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean showAlert(AlertType alertType, String title, String header, String content) {
		// TODO Auto-generated method stub
		return false;
	}

}
