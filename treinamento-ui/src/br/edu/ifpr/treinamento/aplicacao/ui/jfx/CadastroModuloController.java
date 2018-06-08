package br.edu.ifpr.treinamento.aplicacao.ui.jfx;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx.utils.ScreenManager;
import br.edu.ifpr.treinamento.fxbeans.InstrutorFXBean;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.fxbeans.converters.InstrutorEntityConverter;
import br.edu.ifpr.treinamento.fxbeans.converters.jfx.IntegerConverterFX;
import br.edu.ifpr.treinamento.modelo.Instrutor;
import br.edu.ifpr.treinamento.modelo.Modulo;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import br.edu.ifpr.treinamento.modelo.service.command.JpaPersistenceDAOType;
import br.edu.ifpr.treinamento.modelo.service.command.impl.InstrutorPersistenceDAO;
import br.edu.ifpr.treinamento.modelo.service.command.impl.ModuloPersistenceDAO;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import patterns.strategy.DataEntryState;

public class CadastroModuloController {
   private static final Logger LOGGER =
                     Logger.getLogger(CadastroModuloController.class.getName());

   @FXML private MenuItem miIncluir;
   @FXML private MenuItem miAlterar;
   @FXML private MenuItem miExcluir;
   @FXML private MenuItem miSalvar;
   @FXML private MenuItem miCancelar;
   @FXML private MenuItem miProcurar;
   @FXML private MenuItem miSair;

   @FXML private Button   btnIncluir;
   @FXML private Button   btnAlterar;
   @FXML private Button   btnExcluir;
   @FXML private Button   btnSalvar;
   @FXML private Button   btnCancelar;
   @FXML private Button   btnProcurar;

   // componentes da interface de entrada de dados
   @FXML private GridPane                  gpDados;
   @FXML private TextField                 txfNome;
   @FXML private TextField                 txfDuração;
   @FXML private DatePicker                dpkInício;
   @FXML private ComboBox<InstrutorFXBean> cbxInstrutor;

   private ScreenManager           screenManager;
   private JpaService              jpaService;
   private ModuloPersistenceDAO    moduloDao;
   private InstrutorPersistenceDAO instrutorDao;

   private ModuloFXBean fxbModulo;
   private Modulo       modulo;
   // usada somente quando o botão "Incluir" é clicado, pois o sistema precisa
   // saber se o estado anterior era 'INIT' ou 'VIEW'
   private ModuloFXBean lastFXBModulo;

   private List<InstrutorFXBean> fxbInstrutores;

   // INCLUSÃO OU ALTERAÇÃO: true; VISUALIZAR: false
   private BooleanProperty dataEntryActiveProperty;
   // INCLUSÃO: true; ALTERAÇÃO: false
   private BooleanProperty dataEntryInsertingProperty;

   // esta propriedade indica como esta janela de entrada de dados foi invocada:
   //  true: direta (de um menu principal, por exemplo)
   // false: indireta (da janela de entrada de dados de Curso)
   private BooleanProperty direta;

   // estado para botões/menus quando a janela de entrada de dados é iniciada
   // a partir da janela de entrada de dados de Curso, caso contrário 'null'
   private DataEntryState lastState;
   private DataEntryState state;
   private Node           lastFocused;

   @FXML
   private void onMenuButtonBarAction(ActionEvent ev) {
      Object component = ev.getSource();

      if (component == miIncluir || component == btnIncluir)
         changeState(DataEntryState.INSERT);
      else
         if (component == miAlterar || component == btnAlterar)
            changeState(DataEntryState.EDIT);
         else
            if (component == miExcluir || component == btnExcluir)
               doDelete();
            else
               if (component == miSalvar || component == btnSalvar)
                  doSave();
               else
                  if (component == miCancelar || component == btnCancelar)
                     doCancel();
                  else
                     if (component == miProcurar || component == btnProcurar)
                        doFind();
                     else
                        if (component == miSair)
                           doExit();
   }

   public void initState(ScreenManager screenManager, JpaService jpaService) {
      initState(DataEntryState.INIT,screenManager,jpaService);
   }

   public void initState(DataEntryState state, ScreenManager screenManager,
                         JpaService jpaService) {
      this.direta        =
                        new SimpleBooleanProperty(state == DataEntryState.INIT);
      this.state         = state;
      this.lastFocused   = null;
      this.screenManager = screenManager;
      this.jpaService    = jpaService;
      this.instrutorDao  = (InstrutorPersistenceDAO)
          jpaService.persistenceCommandFactory(JpaPersistenceDAOType.INSTRUTOR);

      this.fxbInstrutores = new ArrayList<>();
      populateInstrutorOList();

      this.fxbModulo     = new ModuloFXBean();
      this.lastFXBModulo = new ModuloFXBean();
      this.modulo        = new Modulo();

      dataEntryActiveProperty    = new SimpleBooleanProperty(false);
      dataEntryInsertingProperty = new SimpleBooleanProperty(false);

      initBindings();
      initBindingDataEntry();

      changeState(state);

      // alinha a entrada de dados da duração para a direita
      txfDuração.setAlignment(Pos.CENTER_RIGHT);
      init();
   }

   private void init() {
      clearData();

      dataEntryActiveProperty.set(false);
      dataEntryInsertingProperty.set(false);
   }

   public FocusPropertyChangeListener getFocusPropertyChangeListener() {
      return new FocusPropertyChangeListener();
   }

   private void initBindings() {
      btnIncluir.disableProperty().bind(miIncluir.disableProperty());
      btnAlterar.disableProperty().bind(miAlterar.disableProperty());
      btnExcluir.disableProperty().bind(miExcluir.disableProperty());
      btnSalvar.disableProperty().bind(miSalvar.disableProperty());
      btnCancelar.disableProperty().bind(miCancelar.disableProperty());
      btnProcurar.disableProperty().bind(miProcurar.disableProperty());
   }

   private void initBindingDataEntry() {
      // habilitar GridPane SOMENTE se entrada de dados habilitada (INCLUSÃO ou
      // ALTERAÇÃO)
      gpDados.disableProperty().bind(dataEntryActiveProperty.not());

      txfNome.textProperty().bindBidirectional(fxbModulo.nomeProperty());
      Bindings.bindBidirectional(txfDuração.textProperty(),
                                 fxbModulo.duracaoProperty(),
                                 new IntegerConverterFX());
      dpkInício.valueProperty().bindBidirectional(fxbModulo.inicioProperty());
      Bindings.bindBidirectional(cbxInstrutor.valueProperty(),
                                 fxbModulo.instrutorProperty()/*,
                                 new InstrutorConverterFX()*/);
      initBindingInstrutores();
   }

   private void initBindingInstrutores() {
      // acrescenta os dados ao ComboBox
      cbxInstrutor.itemsProperty().get().addAll(fxbInstrutores);
      // responsável por "renderizar" (mostrar) um item (ListCell) na lista
      // do ComboBox e a seleção (área do "button") feita pelo usuário
      cbxInstrutor.buttonCellProperty().set(new InstrutorComboBoxListCell());
      // responsável por "renderizar" (mostrar) o conteúdo (lista - ListView
      // do ComboBox
      cbxInstrutor.cellFactoryProperty().set(new InstrutorComboBoxCallback());
   }

   private void changeState(DataEntryState newState) {
      this.lastState = this.state;
      this.state     = newState;

      switch (state) {
         case INIT      :
            changeStateInit();
            break;
         case VIEW   :
            changeStateView();
            break;
         case INSERT :
            changeStateInsert();
            break;
         case EDIT   :
            changeStateUpdate();
            break;
         case DELETE  :
            changeStateDelete();
            break;
         case SAVE    :
            changeStateSave();
            break;
         case CANCEL :
            changeStateCancel();
            break;
         case SEARCH :
            changeStateFind();
            break;
      }
   }

   private void changeStateInit() {
      // menu
      miIncluir.disableProperty().set(false);
      miAlterar.disableProperty().set(true);
      miExcluir.disableProperty().set(true);
      miSalvar.disableProperty().set(true);
      miCancelar.disableProperty().set(true);
      miProcurar.disableProperty().set(false);
      // inicializações adicionais
      if (direta.get())
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
      // ajusta a interface com o usuário para inclusão
      insertFocus();
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
      updateFocus();
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
         // EXCLUIR DADOS
         String key = modulo.getNome();
//         int deleted = moduloDao.delete(key);
         // LIMPAR ENTRADA DE DADOS
         // ALTERAR ESTADO "INICIAL" (os dados são "limpos" aqui)
         init();
         changeState(DataEntryState.INIT);
      }
   }

   private void doSave() {
      boolean dadosValidos = true;
      // VALIDAR ENTRADA DE DADOS
      // SE DADOS NÃO VÁLIDOS
      if (dadosValidos) {
//         ModuloEntityConverter conv = new ModuloEntityConverter();
//
//         toEntity();
//         if (state == DataEntryState.INSERT)
//            moduloDao.insert(modulo);
//         else
//            moduloDao.update(modulo);
         if (!direta.get())
            screenManager.previous();
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
         changeState(DataEntryState.VIEW);
         viewFocus();
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
         if (!direta.get())
            screenManager.previous();
         else {
            if (lastState == DataEntryState.INIT ||
                lastState == DataEntryState.INSERT) {
               changeState(DataEntryState.INIT);
               init();
            }
            else {
               // COPIAR 'lastFXBCurso' PARA 'fxbCurso'
               copy(fxbModulo,lastFXBModulo);
               changeState(DataEntryState.VIEW);
               viewFocus();
            }
         }
      }
   }

   private void doFind() {
      final String CURSOS[] = { "AAA", "BBB", "CCC", "DDD", "EEE" };
      Random       r        = new Random();
      String       codigo   = CURSOS[r.nextInt(CURSOS.length)];
      // ENTRAR NO MODO DE BUSCA
      // ALTERAR ESTADO "VISUALIZAÇÃO"
//      modulo = moduloDao.select(codigo);
      if (modulo != null) {
//         fromEntity();

         // COPIAR 'fxbCurso' PARA 'lastFXBCurso'
         copy(lastFXBModulo,fxbModulo);

         viewFocus();
         changeState(DataEntryState.VIEW);
      }
   }

   private void doExit() {
      // SE HOUVER EDIÇÃO PENDENTE
      //    ENTÃO SE CANCELAR EDIÇÃO
      if (showAlert(AlertType.CONFIRMATION,"Pedido de Fechamento",
                    "Deseja realmente Sair?"))
         screenManager.previous();
      //             ENTÃO SAIR
      //          FIM-SE
      // FIM-SE
   }

   public void insertFocus() {
      clearData();

      dataEntryActiveProperty.set(true);
      dataEntryInsertingProperty.set(true);
      // o foco deve ir ao primeiro componente gráfico para entrada de dados
      txfNome.requestFocus();
   }

   public void viewFocus() {
      dataEntryActiveProperty.set(false);
      dataEntryInsertingProperty.set(false);
   }

   public void updateFocus() {
      showData();

      dataEntryActiveProperty.set(true);
      dataEntryInsertingProperty.set(false);
      // o foco deve ir ao primeiro componente gráfico que não descreva o(s)
      // identificador(es) dos dados exibidos
      txfNome.requestFocus();
   }

   private void showData() {
      ;
   }

   private void clearData() {
      // limpar o campo do nome
      txfNome.clear();
      // limpa o campo da duração com 0 (zero)
      txfDuração.textProperty().set("0");
      // limpar o campo início com a data atual
      dpkInício.valueProperty().set(LocalDate.now());
      // limpa a seleção do ComboBox
      cbxInstrutor.selectionModelProperty().get().clearSelection();
   }

   private void copy(ModuloFXBean target, ModuloFXBean source) {
      source.nomeProperty().set(target.nomeProperty().get());
      source.duracaoProperty().set(target.duracaoProperty().get());
      source.inicioProperty().set(target.inicioProperty().get());
      source.instrutorProperty().set(target.instrutorProperty().get());
   }

   private void populateInstrutorOList() {
      // objeto converter do Modelo de Objetos para o JavaFX Bean
      InstrutorEntityConverter conv = new InstrutorEntityConverter();
      // recupera os instrutores da fonte de dados
      Collection<Instrutor>    temp = instrutorDao.select();
      // converte de 'Instrutor' para 'InstrutorFXBean'
      for (Instrutor i : temp) {
         fxbInstrutores.add(conv.fromEntity(i));
      }

      temp = null;
      conv = null;
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

  private class InstrutorConverterFX extends StringConverter<InstrutorFXBean> {
      @Override
      public String toString(InstrutorFXBean object) {
         if (object == null) return null;
         // o valor retornado aqui está com concordância com o valor retornardo
         // pelo "else" em InstrutorComboBoxListCell.updateItem() (abaixo)
         return object.nomeProperty().get();
      }

      @Override
      public InstrutorFXBean fromString(String string) {
         if (string == null || string.isEmpty()) return null;

         for (InstrutorFXBean fxbInstrutor : fxbInstrutores)
            if (fxbInstrutor.nomeProperty().equals(string))
               return fxbInstrutor;

         return null;
      }
   }

   // classe responsável por retornar as informações a serem exibidas na lista
   // (ListVew) do ComboBox
   private class InstrutorComboBoxCallback
                 implements Callback<ListView<InstrutorFXBean>,
                                     ListCell<InstrutorFXBean>> {
      @Override
      public ListCell<InstrutorFXBean> call(ListView<InstrutorFXBean> param) {
         return new InstrutorComboBoxListCell();
      }
   }
   // classe responsável por retornar uma informação a ser exibida (ListCell)
   // na lista (ListVew) do ComboBox
   private class InstrutorComboBoxListCell extends ListCell<InstrutorFXBean> {
      @Override
      public void updateItem(InstrutorFXBean item, boolean empty) {
         super.updateItem(item,empty);

         if (item == null) textProperty().set(null);
         // o valor retornado aqui está com concordância com o valor retornardo
         // por InstrutorConverterFX.toString() (acima)
         else textProperty().set(item.nomeProperty().get());
      }
   }
   // ==========================================================================
   // === CLASSES DE SUPORTE - FIM =============================================
   // ==========================================================================
}
