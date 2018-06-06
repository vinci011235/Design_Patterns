package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import br.edu.ifpr.treinamento.fxbeans.CursoFXBean;
import br.edu.ifpr.treinamento.fxbeans.ModuloFXBean;
import br.edu.ifpr.treinamento.modelo.Curso;
import br.edu.ifpr.treinamento.modelo.service.JpaService;
import br.edu.ifpr.treinamento.modelo.service.command.JpaPersistenceDAOType;
import br.edu.ifpr.treinamento.modelo.service.command.impl.CursoPersistenceDAO;
import br.edu.ifpr.treinamento.modelo.types.CursoSituacaoType;
import br.edu.ifpr.treinamento.utils.date.DateTimeUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class CadastroCurso extends AnchorPane {
   private static final Logger LOGGER =
                      Logger.getLogger(CadastroCurso.class.getName());

   private static final DateTimeFormatter DATE_FORMATTER =
                                      DateTimeFormatter.ofPattern("dd/MM/yyyy");

   private static final String CADASTRO_CURSO_FXML = "CadastroCurso.fxml";

   // dados para o ChoiceBox "Situação"
   private static final List<String> CURSO_SITUACOES = Arrays.asList(
                                         CursoSituacaoType.ABERTO.toString(),
                                         CursoSituacaoType.ANDAMENTO.toString(),
                                         CursoSituacaoType.ENCERRADO.toString(),
                                         CursoSituacaoType.CANCELADO.toString()
                                     );

   // componentes da interface de entrada de dados - INÍCIO
   @FXML private GridPane          gpDados;
   @FXML private TextField         txfCodigo;
   @FXML private TextField         txfNome;
   @FXML private ChoiceBox<String> chbSituacao;
   @FXML private DatePicker        dpkInicio;

   @FXML private Label                          lbModulos;
   @FXML private TableView<ModuloFXBean>              tvwModulos;
   @FXML private TableColumn<ModuloFXBean, LocalDate> tclInicio;
   @FXML private TableColumn<ModuloFXBean, Integer>   tclDuracao;
   @FXML private TableColumn<ModuloFXBean, String>    tclNome;

   @FXML private Button btnIncluirModulo;
   @FXML private Button btnAlterarModulo;
   @FXML private Button btnExcluirModulo;
   @FXML private Button btnProcurarModulo;
   @FXML private Button btnSelecionarTodosModulo;
   @FXML private Button btnLimparSelecaoModulo;
   // componentes da interface de entrada de dados - FIM

   // INCLUSÃO OU ALTERAÇÃO: true; VISUALIZAR: false
   private BooleanProperty dataEntryActiveProperty;
   // INCLUSÃO: true; ALTERAÇÃO: false
   private BooleanProperty dataEntryInsertingProperty;

   private CursoPersistenceDAO dao;
   private CursoFXBean         fxbCurso;

   public CadastroCurso(JpaService jpaService) {
      FXMLLoader loader = new FXMLLoader(
                                   getClass().getResource(CADASTRO_CURSO_FXML));
      // o método initialize() NÃO é chamado
      loader.setRoot(this);
      loader.setController(this);

      try { loader.load(); }
      catch (IOException ex) {
//         LOGGER.severe(ex.getMessage());
         ex.printStackTrace();
      }
      dataEntryActiveProperty    = new SimpleBooleanProperty(false);
      dataEntryInsertingProperty = new SimpleBooleanProperty(false);

      this.dao = (CursoPersistenceDAO) jpaService
                        .persistenceCommandFactory(JpaPersistenceDAOType.CURSO);
      // cria o objeto CursoFXBean
      // ************************************************************
      // ESTE OBJETO DEVE EXISTIR ANTES DOS MÉTODOS A SEGUIR, POIS OS
      // MESMOS INTERAGEM COM ELE
      // ************************************************************
      fxbCurso = new CursoFXBean();

      initBindingDataEntry();
      initBindingModulos();

      clearData();
   }

//   @FXML
//   private void initialize() {
//      dataEntryActiveProperty    = new SimpleBooleanProperty(false);
//      dataEntryInsertingProperty = new SimpleBooleanProperty(false);
//
//      // cria o objeto CursoFXBean
//      fxbCurso = new CursoFXBean();
//      // cria a coleção de módulos de um fxbCurso
//      modulos = FXCollections.observableArrayList();
//
//      initBindingDataEntry();
//      initBindingControls();
//      initState();
//   }

   @FXML
   private void onModulosButtonAction(ActionEvent ev) {
      Button btn = (Button) ev.getSource();

      if (btn == btnIncluirModulo)
         ;
      else
         if (btn == btnAlterarModulo)
            ;
         else
            if (btn == btnExcluirModulo)
               ;
            else
               if (btn == btnProcurarModulo)
                  ;
               else
                  if (btn == btnSelecionarTodosModulo) {
                     // SelectionMode = MULTIPLE
                     tvwModulos.getSelectionModel().selectAll();
                     tvwModulos.requestFocus();
                  }
                  else
                     if (btn == btnLimparSelecaoModulo)
                        tvwModulos.getSelectionModel().clearSelection();
   }

   public void initState() {
      clearData();

      dataEntryActiveProperty.set(false);
      dataEntryInsertingProperty.set(false);
   }

   public void insertFocus() {
      clearData();

      dataEntryActiveProperty.set(true);
      dataEntryInsertingProperty.set(true);
      // o foco deve ir ao primeiro componente gráfico para entrada de dados
      txfCodigo.requestFocus();
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

   public void showData() {
      // PARA TESTE "PROCURAR"
      fxbCurso.codigoProperty().set("AAA");
      fxbCurso.nomeProperty().set("CursoFXBean AAA");
      fxbCurso.situacaoProperty().set(CursoSituacaoType.ABERTO);
      fxbCurso.inicioProperty().set(DateTimeUtils.localDateNowPlusDays(10));

      fxbCurso.modulosProperty().clear();
      fxbCurso.modulosProperty().add(
                              new ModuloFXBean("MÓDULO AAA",DateTimeUtils.getDate()));
      fxbCurso.modulosProperty().add(
                new ModuloFXBean("MÓDULO BBB",DateTimeUtils.localDateNowPlusDays(2)));
      fxbCurso.modulosProperty().add(
                new ModuloFXBean("MÓDULO CCC",DateTimeUtils.localDateNowPlusDays(4)));
      fxbCurso.modulosProperty().add(
                new ModuloFXBean("MÓDULO DDD",DateTimeUtils.localDateNowPlusDays(6)));
      fxbCurso.modulosProperty().add(
                new ModuloFXBean("MÓDULO EEE",DateTimeUtils.localDateNowPlusDays(8)));
   }

   public void clearData() {
      txfCodigo.clear();
      txfNome.clear();
      initChoiceBoxSituacao();
      initDatePickerInicio();

      tvwModulos.itemsProperty().get().clear();
   }

   public void insert() {
      System.out.println("===> insert()<===");
      Curso c = new Curso(fxbCurso.getCodigo(),fxbCurso.getNome(),
                          fxbCurso.getDuracao(),
                          DateTimeUtils.localDateToDate(fxbCurso.getInicio()));

      dao.insert(c);
   }

   public void update() {
      System.out.println("===> update() <===");
      System.out.println(fxbCurso);
   }

   public void delete() {
      System.out.println("===> delete() <===");
      System.out.println(fxbCurso);
   }

   public void find() {
      CadastroProcurarDialog<CursoFXBean> procurar =
                                                 new CadastroProcurarDialog<>();
      procurar.showAndWait();
   }

   // ==========================================================================
   // === MÉTODOS DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private void initBindingDataEntry() {
      // vincula TextField <-> objeto do modelo
      txfCodigo.textProperty().bindBidirectional(fxbCurso.codigoProperty());
      // habilitar entrada de dados para o(s) campo(s) que identifica(m) os
      // dados SOMENTE se INCLUSÃO
      txfCodigo.disableProperty().bind(dataEntryInsertingProperty.not());

      // se o código do fxbCurso aceitar somente valores numéricos descomentar
      // a linha a seguir
//      txfCodigo.textFormatterProperty().set(
//                                      new TextFormatter<>(new IntegerFilter()));
      txfCodigo.textProperty().addListener(new CodigoChangeListener());

      // vincula TextField <-> objeto do modelo
      txfNome.textProperty().bindBidirectional(fxbCurso.nomeProperty());
      txfNome.textProperty().addListener(new NomeChangeListener());

      // vincula TextField <-> objeto do modelo
      Bindings.bindBidirectional(chbSituacao.valueProperty(),
                                 fxbCurso.situacaoProperty(),
                                 new CursoSituacaoConverter());

      // vincula TextField <-> objeto do modelo
      dpkInicio.valueProperty().bindBidirectional(fxbCurso.inicioProperty());
      dpkInicio.valueProperty().addListener(new InicioChangeListener());
      // descomentar a linha a seguir para restringir datas menores que a data
      // atual
//      dpkInicio.dayCellFactoryProperty().set(new RestrictDaysCallback());

      // vincula TextField <-> objeto do modelo
      tvwModulos.itemsProperty().bind(fxbCurso.modulosProperty());
      // indica para cada coluna a qual o atributo do objeto do modelo está
      // vinculado
      tclInicio.cellValueFactoryProperty().set(
                         new PropertyValueFactory<ModuloFXBean, LocalDate>("inicio"));
      tclInicio.cellFactoryProperty().set(new InicioFormatterCallback());

      tclDuracao.cellValueFactoryProperty().set(
                          new PropertyValueFactory<ModuloFXBean, Integer>("duracao"));
      tclNome.cellValueFactoryProperty().set(
                              new PropertyValueFactory<ModuloFXBean, String>("nome"));
   }

   private void initBindingModulos() {
      // habilitar GridPane SOMENTE se entrada de dados habilitada (INCLUSÃO ou
      // ALTERAÇÃO)
      gpDados.disableProperty().bind(dataEntryActiveProperty.not());
      // permite múltiplas seleções e o botão "Selecionar Todos"
      tvwModulos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
      // habilitar grid somente se INCLUSÃO ou ALTERAÇÃO
      tvwModulos.disableProperty().bind(gpDados.disableProperty());
      // habilitar INCLUSÃO de dados detalhes SOMENTE se entrada de dados
      // habilitada
      btnIncluirModulo.disableProperty().bind(gpDados.disableProperty());
      // habilitar botões de alterar, excluir, procurar, selecionar tudo e
      // limpar seleção SOMENTE se entrada de dados estiver habilitada E houver
      // dados no TableView
      ObservableList<ModuloFXBean> items = tvwModulos.itemsProperty().get();

      BooleanBinding disableIfEmpty = gpDados.disableProperty().or(
                                             Bindings.size(items).isEqualTo(0));
      btnAlterarModulo.disableProperty().bind(
                                 gpDados.disableProperty().or(disableIfEmpty));
      btnExcluirModulo.disableProperty().bind(
                                 gpDados.disableProperty().or(disableIfEmpty));
      btnProcurarModulo.disableProperty().bind(
                                 gpDados.disableProperty().or(disableIfEmpty));

      BooleanBinding noItems = Bindings.size(items).isEqualTo(0);
      btnSelecionarTodosModulo.disableProperty().bind(
                                        gpDados.disableProperty().or(noItems));

      ReadOnlyIntegerProperty selectedIndex =
                                       tvwModulos.selectionModelProperty()
                                                 .get().selectedIndexProperty();
      BooleanBinding          selected      = selectedIndex.greaterThan(-1);
      btnLimparSelecaoModulo.disableProperty().bind(selected.not());
   }

   private void initChoiceBoxSituacao() {
      chbSituacao.itemsProperty().get().clear();
      chbSituacao.itemsProperty().get().addAll(CURSO_SITUACOES);
      chbSituacao.selectionModelProperty().get().selectFirst();
   }

   private void initDatePickerInicio() {
      dpkInicio.valueProperty().set(LocalDate.now());
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - FIM =============================================
   // ==========================================================================

   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private class CodigoChangeListener implements ChangeListener<String> {
      @Override
      public void changed(ObservableValue<? extends String> observable,
                          String oldValue, String newValue) {
         ;
      }
   }

   private class NomeChangeListener implements ChangeListener<String> {
      @Override
      public void changed(ObservableValue<? extends String> observable,
                          String oldValue, String newValue) {
         ;
      }
   }

   private class InicioChangeListener implements ChangeListener<LocalDate> {
      @Override
      public void changed(ObservableValue<? extends LocalDate> observable,
                          LocalDate oldValue, LocalDate newValue) {
         ;
      }
   }

   private class CursoSituacaoConverter
                                    extends StringConverter<CursoSituacaoType> {
      @Override
      public String toString(CursoSituacaoType object) {
         if (object == null) return null;

         return object.toString();
      }

      @Override
      public CursoSituacaoType fromString(String string) {
         if (string == null || string.isEmpty()) return null;

         return CursoSituacaoType.valueOf(string.toUpperCase());
      }
   }

   // PARA TESTES!!!
//   private class DateToLocalDateConverter extends StringConverter<Date> {
//      private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//
//      @Override
//      public String toString(Date object) {
//         if (object == null) return "";
//
//         return sdf.format(object);
//      }
//
//      @Override
//      public Date fromString(String string) {
//         if (string == null || string.isEmpty()) return null;
//
//         Date date = null;
//         try { date = sdf.parse(string); }
//         catch (ParseException ex) {}
//
//         return date;
//      }
//   }

   // PARA TESTES!!!
   // filtro que permite somente valores numéricos nos TextField
//   private class IntegerFilter implements UnaryOperator<TextFormatter.Change> {
//      @Override
//      public Change apply(Change change) {
//         String text = change.getText();
//
//         // se qualquer caractere em 'text' NÃO for numérico, a função é
//         // cancelada e o valor digitado é descaratado no TextField
//         for (int i = 0; i < text.length(); i++)
//            if (!Character.isDigit(text.charAt(i)))
//               return null;
//
//         return change;
//      }
//   }

   // PARA TESTES!!!
//   private class InicioFormatterCallback
//                 implements Callback<CellDataFeatures<ModuloFXBean, LocalDate>,
//                                     ObservableValue<LocalDate>> {
//      @Override
//      public ObservableValue<LocalDate> call(
//                                     CellDataFeatures<ModuloFXBean, LocalDate> cell) {
//         ObjectProperty<LocalDate> property = new SimpleObjectProperty<>();
//
//
//         return property;
//      }
//   }

   private class InicioFormatterCallback
                 implements Callback<TableColumn<ModuloFXBean, LocalDate>,
                                     TableCell<ModuloFXBean, LocalDate>> {
      @Override
      public TableCell<ModuloFXBean, LocalDate> call(
                                          TableColumn<ModuloFXBean, LocalDate> cell) {
         return new InicioFormatterTableCell();
      }
   }

   private class InicioFormatterTableCell extends TableCell<ModuloFXBean, LocalDate> {
      @Override
      protected void updateItem(LocalDate item, boolean empty) {
         super.updateItem(item,empty);

         if (item == null) setText(null);
         else setText(DATE_FORMATTER.format(item));
      }
   }

   // PARA TESTES!!!
   // restringe DatePicker a datas iguais ou maiores que a data atual
//   private class RestrictDaysCallback
//                 implements Callback<DatePicker, DateCell> {
//      @Override
//      public DateCell call(DatePicker param) {
//         return new RestrictDaysDateCell();
//      }
//   }
//
//   private class RestrictDaysDateCell extends DateCell {
//      @Override
//      public void updateItem(LocalDate item, boolean empty) {
//         super.updateItem(item,empty);
//
//         if (item.isBefore(dpkInicio.getValue())) {
//            disableProperty().set(true);
//            this.styleProperty().set("-fx-background-color: #ffc0cb"); // pink
//         }
//      }
//   }
   // ==========================================================================
   // === CLASSES DE SUPORTE - FIM =============================================
   // ==========================================================================
}
