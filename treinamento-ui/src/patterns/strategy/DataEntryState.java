package patterns.strategy;

import br.edu.ifpr.treinamento.aplicacao.ui.jfx.CadastroCursoController;

public enum DataEntryState implements DataEntryStateInterface {
   INIT {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateInit();
	   }
   }, VIEW {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateView();
	   }
   }, 
   INSERT {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateInsert();
	   }
   }, 
   EDIT{
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateUpdate();
	   }
   }, 
   DELETE {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateDelete();
	   }
   }, 
   SAVE {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateSave();
	   }
   }, CANCEL {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateCancel();
	   }
   }, 
   SEARCH {
	   @Override
	   public void changeState(CadastroCursoController ccc) {
		   ccc.changeStateFind();
	   }
   };
}