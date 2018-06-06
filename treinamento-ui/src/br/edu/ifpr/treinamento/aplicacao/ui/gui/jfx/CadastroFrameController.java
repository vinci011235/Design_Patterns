package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.util.logging.Logger;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class CadastroFrameController {
   private static final Logger LOGGER =
                      Logger.getLogger(CadastroFrameController.class.getName());

   private enum DataEntryState {
      INIT, VIEWING, INCLUDING, EDITING, DELETING, SAVING, CANCELING, SEARCHING;
   }

   @FXML private BorderPane borderPane;

   @FXML private MenuItem   miIncluir;
   @FXML private MenuItem   miAlterar;
   @FXML private MenuItem   miExcluir;
   @FXML private MenuItem   miSalvar;
   @FXML private MenuItem   miCancelar;
   @FXML private MenuItem   miProcurar;
   @FXML private MenuItem   miSair;

   @FXML private Button     btnIncluir;
   @FXML private Button     btnAlterar;
   @FXML private Button     btnExcluir;
   @FXML private Button     btnSalvar;
   @FXML private Button     btnCancelar;
   @FXML private Button     btnProcurar;

   private ScreenManager    sceneManager;
   private JpaService       jpaService;

   private CadastroCurso    cadastro;
   private DataEntryState   state;
   private Node             lastFocused;

   @FXML
   private void onMenuButtonBarAction(ActionEvent ev) {
      Object component = ev.getSource();

      if (component == miIncluir || component == btnIncluir) {
         LOGGER.info("==> ITEM DE MENU: Incluir <==");
         changeState(DataEntryState.INCLUDING);
      }
      else
         if (component == miAlterar || component == btnAlterar) {
            LOGGER.info("==> ITEM DE MENU: Alterar <==");
            changeState(DataEntryState.EDITING);
         }
         else
            if (component == miExcluir || component == btnExcluir) {
               LOGGER.info("==> ITEM DE MENU: Excluir <==");
               doDelete();
            }
            else
               if (component == miSalvar || component == btnSalvar) {
                  LOGGER.info("==> ITEM DE MENU: Salvar <==");
                  doSave();
               }
               else
                  if (component == miCancelar || component == btnCancelar) {
                     LOGGER.info("==> ITEM DE MENU: Cancelar <==");
                     doCancel();
                  }
                  else
                     if (component == miProcurar || component == btnProcurar) {
                        LOGGER.info("==> ITEM DE MENU: Procurar <==");
                        doFind();
                     }
                     else
                        if (component == miSair) {
                           LOGGER.info("==> ITEM DE MENU: Sair <==");
                           doExit();
                        }
   }

//   @FXML
//   private void initialize() {
//      initState();
//   }

   public void initState(ScreenManager sceneManager, JpaService jpaService) {
      this.jpaService = jpaService;

      this.sceneManager = sceneManager;
      this.cadastro     = null;
      this.lastFocused  = null;

      initBindings();

      // o método a seguir DEVE ser chamado somente na inicialização
      getDataEntry();
      // deixa a UI em estado inicial
      changeState(DataEntryState.INIT);

      // DESCOMENTAR PARA TESTAR "PROCURAR"
//      Platform.runLater(new Runnable() {
//         @Override
//         public void run() {
//            miProcurar.disableProperty().set(false);
//            miProcurar.fire();
//         }
//      });
   }

   private void initBindings() {
      // barra de botões
      btnIncluir.disableProperty().bind(miIncluir.disableProperty());
      btnAlterar.disableProperty().bind(miAlterar.disableProperty());
      btnExcluir.disableProperty().bind(miExcluir.disableProperty());
      btnSalvar.disableProperty().bind(miSalvar.disableProperty());
      btnCancelar.disableProperty().bind(miCancelar.disableProperty());
      btnProcurar.disableProperty().bind(miProcurar.disableProperty());
   }

   private void getDataEntry() {
      ObjectProperty<Node> opNode = borderPane.centerProperty();
      Node                 node   = opNode.get();

      if (node != null) {
         cadastro = (CadastroCurso) node;
         cadastro.initState();
      }
   }

   private void doInsert() {
      // VALIDAR OS DADOS
      // SE DADOS VÁLIDOS
      //    ENTÃO INCLUIR
      //    SENÃO ;
      // FIM-SE
   }

   private void doUpdate() {
      // VALIDAR OS DADOS
      // SE DADOS VÁLIDOS
      //    ENTÃO ALTERAR
      //    SENÃO ;
      // FIM-SE
   }

   private void doDelete() {
      // SE DEVE EXCLUIR
      //    ENTÃO PROCESSAR EXCLUSÃO DE DADOS
      // FIM-SE
      if (showAlert(AlertType.CONFIRMATION,"Pedido de Exclusão",
                    "Deseja realmente excluir os dados???")) {
         if (cadastro != null) {
            // EXCLUIR DADOS
            cadastro.delete();
            // LIMPAR ENTRADA DE DADOS
            // ALTERAR ESTADO "INICIAL" (os dados são "limpos" aqui)
            cadastro.initState();
         }
         changeState(DataEntryState.INIT);
      }
   }

   private void doSave() {
      boolean dadosValidos = true;
      // VALIDAR ENTRADA DE DADOS
      // SE DADOS NÃO VÁLIDOS
      if (dadosValidos) {
         if (cadastro != null)
            if (state == DataEntryState.INCLUDING)
               cadastro.insert();
            else
               cadastro.update();
      }
      else {
      // SENÃO PROCESSAR DADOS
      //       SE ESTÁ INCLUINDO
      //          ENTÃO LIMPAR ENTRADA DE DADOS
      //                ALTERAR ESTADO "INICIAL"
      //          SENÃO ALTERAR ESTADO "VISUALIZAÇÃO"
      //       FIM-SE
      // FIM-SE
      }
//      if (state == DataEntryState.INSERT) {
//         changeState(DataEntryState.INIT);
//         if (cadastro != null)
//            cadastro.initState();
//      }
//      else {
         changeState(DataEntryState.VIEWING);
         if (cadastro != null)
            cadastro.viewFocus();
//      }
   }

   private void doCancel() {
      // SE CANCELAR OPERAÇÃO
      //    ENTÃO LIMPAR ENTRADA DE DADOS
      //          ALTERAR ESTADO "INICIAL"
      //    SENÃO ALTERAR ESTADO "VISUALIZAÇÃO"
      // FIM-SE
      if (showAlert(AlertType.CONFIRMATION,"Pedido de Cancelamento",
                    "Deseja realmente cancelar a operação?")) {
         if (state == DataEntryState.INCLUDING) {
            changeState(DataEntryState.INIT);
            if (cadastro != null)
               cadastro.initState();
         }
         else {
            changeState(DataEntryState.VIEWING);
            if (cadastro != null)
               cadastro.viewFocus();
         }
      }
   }

   private void doFind() {
      // ENTRAR NO MODO DE BUSCA
      // ALTERAR ESTADO "VISUALIZAÇÃO"
      if (cadastro != null) {
         cadastro.find();
         cadastro.viewFocus();
         changeState(DataEntryState.VIEWING);
      }
      // FIM-SE
//      }
   }

   private void doExit() {
      // SE HOUVER EDIÇÃO PENDENTE
      //    ENTÃO SE CANCELAR EDIÇÃO
      if (showAlert(AlertType.CONFIRMATION,"Pedido de Fechamento",
                    "Deseja realmente Sair?"))
         sceneManager.previous();
      //             ENTÃO SAIR
      //          FIM-SE
      // FIM-SE
   }

   private void changeState(DataEntryState newState) {
      state = newState;

      switch (state) {
         case INIT      :
            changeStateInit();
            break;
         case VIEWING   :
            changeStateView();
            break;
         case INCLUDING :
            changeStateInsert();
            break;
         case EDITING   :
            changeStateUpdate();
            break;
         case DELETING  :
            changeStateDelete();
            break;
         case SAVING    :
            changeStateSave();
            break;
         case CANCELING :
            changeStateCancel();
            break;
         case SEARCHING :
            changeStateFind();
            break;
      }
   }

   private void changeStateInit() {
      // menu
//      miIncluir.disableProperty().bind();
      miIncluir.disableProperty().set(false);
      miAlterar.disableProperty().set(true);
      miExcluir.disableProperty().set(true);
      miSalvar.disableProperty().set(true);
      miCancelar.disableProperty().set(true);
      miProcurar.disableProperty().set(false);
      // inicializações adicionais
      btnIncluir.requestFocus();
   }

   private void changeStateView() {
      // menu
      miIncluir.disableProperty().set(false);
      miAlterar.disableProperty().set(false);
      miExcluir.disableProperty().set(false);
      miSalvar.disableProperty().set(true);
      miCancelar.disableProperty().set(true);
      miProcurar.disableProperty().set(false);
   }

   private void changeStateInsert() {
      // menu
      miIncluir.disableProperty().set(true);
      miAlterar.disableProperty().set(true);
      miExcluir.disableProperty().set(true);
      miSalvar.disableProperty().set(false);
      miCancelar.disableProperty().set(false);
      miProcurar.disableProperty().set(true);
      // ajusta a interface para inclusão
      if (cadastro != null)
         cadastro.insertFocus();
   }

   private void changeStateUpdate() {
      // menu
      miIncluir.disableProperty().set(true);
      miAlterar.disableProperty().set(true);
      miExcluir.disableProperty().set(true);
      miSalvar.disableProperty().set(false);
      miCancelar.disableProperty().set(false);
      miProcurar.disableProperty().set(false);
      // ajusta a entrada de dados para alteração
      if (cadastro != null)
        cadastro.updateFocus();
   }

   private void changeStateDelete() {
      // menu
      miIncluir.disableProperty().set(true);
      miAlterar.disableProperty().set(true);
      miExcluir.disableProperty().set(false);
      miSalvar.disableProperty().set(true);
      miCancelar.disableProperty().set(true);
      miProcurar.disableProperty().set(true);
   }

   private void changeStateSave() {
      ;
   }

   private void changeStateCancel() {
      ;
   }

   private void changeStateFind() {
      ;
   }

   private boolean showAlert(AlertType alertType, String title,
                             String content) {
      return showAlert(alertType,title,null,content);
   }

   private boolean showAlert(AlertType alertType, String title, String header,
                             String content) {
      Alert alert = new Alert(alertType);

      alert.titleProperty().set(title);
      alert.headerTextProperty().set(header);
      alert.contentTextProperty().set(content);

      // mudar o botão padrão
      // 1:
      //Deactivate Defaultbehavior for yes-Button:
      Button button =
                  (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
      button.setDefaultButton(false);

      //Activate Defaultbehavior for no-Button:
      button = (Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL);
      button.setDefaultButton(true);

      // 2:
//      ButtonBar buttonBar =
//                        (ButtonBar) alert.getDialogPane().lookup(".button-bar");
//      buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);

      // impedir que o diálogo seja fechado até estar "pronto"
//      final Button btOk =
//                     (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
//
//      btOk.addEventFilter(ActionEvent.ACTION,new EventHandler<ActionEvent> () {
//         @Override
//         public void handle(ActionEvent ev) {
//            if (false/*ALGUM PROCESSAMENTO É FALSO*/)
//               ev.consume();
//         }
//      });
/*
               (event) -> {
        if (!validateAndStore()) {
          event.consume();
        }
      });
*/

//      Optional<ButtonType> result = alert.showAndWait();
      ButtonType           btn    = alert.showAndWait().get();

      if (btn == ButtonType.CANCEL)
         if (lastFocused != null)
            lastFocused.requestFocus();

      return btn == ButtonType.OK;
   }

   // ==========================================================================
   // === MÉTODOS DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   public FocusPropertyChangeListener getFocusPropertyChangeListener() {
      return new FocusPropertyChangeListener();
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - FIM =============================================
   // ==========================================================================

   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private class FocusPropertyChangeListener implements ChangeListener<Node> {
      @Override
      public void changed(ObservableValue<? extends Node> observable,
                          Node oldValue, Node newValue) {
//         System.out.println("ObservableValue " + observable);
//         System.out.println("       oldValue " + oldValue);
//         System.out.println("       newValue " + newValue);
         lastFocused = oldValue;
      }
   }
   // ==========================================================================
   // === CLASSES DE SUPORTE - FIM =============================================
   // ==========================================================================
}
