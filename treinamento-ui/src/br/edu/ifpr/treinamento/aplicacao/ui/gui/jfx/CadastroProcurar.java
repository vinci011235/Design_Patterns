package br.edu.ifpr.treinamento.aplicacao.ui.gui.jfx;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class CadastroProcurar extends AnchorPane {
   private static final Logger LOGGER =
            Logger.getLogger(CadastroFrameController.class.getName());

   private static final String FXML_FILE_NAME = "CadastroProcurar.fxml";

   @FXML private BorderPane                                  bpnEntradaDados;
   @FXML private ListView<CadastroProcurarDados>             lvwEntradaDados;
   @FXML private Button                                      btnProcurar;
   @FXML private TableView<ObservableList<String>>           tvwDados;
   @FXML private TableColumn<ObservableList<String>, String> tclSequencia;
//   @FXML private Button                                      btnRecuperar;
//   @FXML private Button                                      btnCancelar;

   private ObservableList<CadastroProcurarDados> dadosProcura;

//   private CadastroProcurarDados cadastroProcurarDados;

   public CadastroProcurar() { loadFXML(); }

   @FXML
   public void initialize() {
//      cadastroProcurarDados = new CadastroProcurarDados(this);

      dadosProcura = FXCollections.observableArrayList();
//      addCadastroProcurarDados();

      lvwEntradaDados.itemsProperty().set(dadosProcura);
//      lvwEntradaDados.styleProperty().set(".list-cell:empty {-fx-opacity: 0;}");
//      lvwEntradaDados.styleProperty().set(
//         ".list-cell:even {-fx-background-color: teal;}" +
//         ".list-cell:odd {-fx-background-color: chocolate;}"
//      );
//      lvwEntradaDados.styleProperty().set(".list-cell:filled:selected {-fx-background-color: teal;}");

      tclSequencia.setCellValueFactory(new SequenciaColumnCellValue());
//      tclSequencia.cellFactoryProperty().set(
//                                        new SequenciaColumnFormatterCallback());

      btnProcurar.onActionProperty().set(new ProcurarButtonActionHandler());
   }

   public ReadOnlyIntegerProperty selectedIndex() {
      return new SimpleIntegerProperty(0);
   }

//   public void initialFocus() { cadastroProcurarDados.initialFocus(); }

   public void addCadastroProcurarDados() {
      CadastroProcurarDados temp = new CadastroProcurarDados(this);
System.out.println("==> add(" + temp + ")");
      dadosProcura.add(temp);
   }

   public boolean removeCadastroProcurarDados(CadastroProcurarDados aRemover) {
      if (dadosProcura.size() == 1) return false;
System.out.println("==>  ANTES DE REMOVER: " + dadosProcura.size());
System.out.println("==> remove(" + aRemover + ")");
      dadosProcura.remove(aRemover);
System.out.println("==> DEPOIS DE REMOVER: " + dadosProcura.size());
      return true;
   }

   // ==========================================================================
   // === MÉTODOS DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private void loadFXML() {
      FXMLLoader loader = new FXMLLoader(
                                        getClass().getResource(FXML_FILE_NAME));

      loader.setRoot(this);
      loader.setController(this);

      try { loader.load(); }
      catch (IOException ex) {
         throw new RuntimeException(ex);
      }
   }
   // ==========================================================================
   // === MÉTODOS DE SUPORTE - FIM =============================================
   // ==========================================================================

   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
   private class ProcurarButtonActionHandler
                 implements EventHandler<ActionEvent> {
      @Override
      public void handle(ActionEvent ev) {
         boolean dadosCompletos = true;

//         for (CadastroProcurarDados procurar :
//              lvwEntradaDados.itemsProperty().get()) {
//            dadosCompletos = procurar.haDadosProperty().get();
//            if (!dadosCompletos)
//               break;
//         }

//         TableColumn<ObservableList<String>, String> c = new TableColumn<>("Coluna");
//         c.setCellValueFactory(null);
         // o usuário preencheu os dados de procura corretamente. Agora as
         // colunas do 'grid' serão criadas, de acordo com a quantidade de
         // informações recuperadas e depois e, a seguir, o 'grid' é preenchido
         if (dadosCompletos) {
            DataGenerator generator   = new DataGenerator();
            List<String>  columnNames = generator.getNext(5);

            tvwDados.getColumns().clear();
            tvwDados.getColumns().add(tclSequencia);
            // CRIA AS COLUNAS DO GRID
            for (int i = 0; i < columnNames.size(); i++) {
               final int ind = i;

               TableColumn<ObservableList<String>, String> column =
                                          new TableColumn<>(columnNames.get(i));

               column.setCellValueFactory(param ->
                  new ReadOnlyObjectWrapper<String>(param.getValue().get(ind)));

               tvwDados.getColumns().add(column);
            }
            // popula o 'grid' com os dados encontrados, se houverem
            for (int i = 0; i < 500; i++) {
               ObservableList<String> dados =
                                            FXCollections.observableArrayList();

               dados.add(String.valueOf(i + 1));
               dados.addAll(generator.getNext(5));

               tvwDados.getItems().add(dados);
            }
         }
         else {
            // o usuário não preencheu os dados de procura corretamente:
            // evita a propagação do evento de volta ao JavaFX e avisa o usuário
            // da falta de informações
            ev.consume();
            Alert alert = new Alert(AlertType.ERROR,
                                    "Preencha todos os dados para procura",
                                    ButtonType.OK);

            alert.titleProperty().set("Erro");
            alert.headerTextProperty().set("Dados Incompletos!");
            alert.showAndWait();
         }
      }
   }

   private static class DataGenerator {
      private static final String[] LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc tempus cursus diam ac blandit. Ut ultrices lacus et mattis laoreet. Morbi vehicula tincidunt eros lobortis varius. Nam quis tortor commodo, vehicula ante vitae, sagittis enim. Vivamus mollis placerat leo non pellentesque. Nam blandit, odio quis facilisis posuere, mauris elit tincidunt ante, ut eleifend augue neque dictum diam. Curabitur sed lacus eget dolor laoreet cursus ut cursus elit. Phasellus quis interdum lorem, eget efficitur enim. Curabitur commodo, est ut scelerisque aliquet, urna velit tincidunt massa, tristique varius mi neque et velit. In condimentum quis nisi et ultricies. Nunc posuere felis a velit dictum suscipit ac non nisl. Pellentesque eleifend, purus vel consequat facilisis, sapien lacus rutrum eros, quis finibus lacus magna eget est. Nullam eros nisl, sodales et luctus at, lobortis at sem.".split(" ");

      private int curWord = 0;

      List<String> getNext(int nWords) {
         List<String> words = new ArrayList<>();

         for (int i = 0; i < nWords; i++) {
            if (curWord == Integer.MAX_VALUE) curWord = 0;

            words.add(LOREM[curWord % LOREM.length]);
            curWord++;
         }

         return words;
      }
   }

   private class SequenciaColumnCellValue
         implements Callback<CellDataFeatures<ObservableList<String>, String>,
                             ObservableValue<String>> {
//      private final NumberFormat ZERO_LEFT_FORMATTER =
//                                              NumberFormat.getIntegerInstance();
      private final NumberFormat ZERO_LEFT_FORMATTER =
                                                    new DecimalFormat("##,##0");

      @Override
      public ObservableValue<String> call(
                      CellDataFeatures<ObservableList<String>, String> param) {
         Integer i = new Integer(param.getValue().get(0));

         String s = ZERO_LEFT_FORMATTER.format(i);
         System.out.println("A [" + s + "]");

         return new SimpleStringProperty(s);
      }
   }

   private class SequenciaColumnFormatterCallback
                implements Callback<TableColumn<ObservableList<String>, String>,
                                   TableCell<ObservableList<String>, String>> {
      @Override
      public TableCell<ObservableList<String>, String> call(
                            TableColumn<ObservableList<String>, String> cell) {
         return new SequenciaFormatterTableCell();
      }
   }

   private class SequenciaFormatterTableCell
                 extends TableCell<ObservableList<String>, String> {
      private final NumberFormat ZERO_LEFT_FORMATTER =
                                              NumberFormat.getIntegerInstance();

      @Override
      protected void updateItem(String item, boolean empty) {
         super.updateItem(item,empty);

         if (item == null) setText(null);
         else {
            String s = ZERO_LEFT_FORMATTER.format(item);
            System.out.println("B " + s);
            setText(s);
         }
      }
   }
   // ==========================================================================
   // === CLASSES DE SUPORTE - INÍCIO ==========================================
   // ==========================================================================
}
