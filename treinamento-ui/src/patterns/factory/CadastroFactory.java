package patterns.factory;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import patterns.strategy.DataEntryState;

public interface CadastroFactory {
	void onMenuButtonBarAction(ActionEvent ev);
	
	void initState(ScreenManager sceneManager, JpaService jpaService);
	
	void init();
	
	void initBindings();
	
	void doInsert();
	void doUpdate();
	void doDelete();
	void doSave();
	void doCancel();
	void doFind();
	void doExit();
	
	void changeState(DataEntryState newState);
	
	void changeStateInit();
	void changeStateView();
	void changeStateInsert();
	void changeStateUpdate();
	void changeStateDelete();
	void changeStateSave();
	void changeStateCancel();
	void changeStateFind();
	
	void insertFocus();
	void viewFocus();
	void updateFocus();
	
	void showData();
	void clearData();
	
	boolean showAlert(AlertType alertType, String title, String content);
	boolean showAlert(AlertType alertType, String title, String header, String content);
}
