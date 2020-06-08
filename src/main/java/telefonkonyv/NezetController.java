package telefonkonyv;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.util.converter.LongStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static telefonkonyv.EmailHitelesito.ezHitelesEmail;
import static telefonkonyv.NevHitelesito.ezHitelesNev;
import static telefonkonyv.TelefonszamHitelesito.ezHitelesTelefonszam;


@Slf4j
public class NezetController implements Initializable {

    @FXML
    AnchorPane anchor;

    @FXML
    SplitPane mainSplit;

    @FXML
    StackPane menuPane;

    @FXML
    Pane contactPane;
    @FXML
    Pane newPane;
    @FXML
    Pane savePane;
    @FXML
    Pane alertPane;
    @FXML
    Pane alert2Pane;
    @FXML
    Button addNewContactButton;
    @FXML
    Button saveButton;
    @FXML
    Button alertButton;
    @FXML
    TextField inputLastName;
    @FXML
    TextField inputFirstName;
    @FXML
    TextField inputPhoneNumber;
    @FXML
    TextField inputSaveName;
    @FXML
    Label alertText;
    @FXML
    Label errorLabel;

    @FXML
    TableView<Person> table;
    @FXML
    TableColumn<Person, Long> id;
    @FXML
    TableColumn<Person,String> firstNameCol;
    @FXML
    TableColumn<Person,String> lastNameCol;
    @FXML
    TableColumn<Person,String> phoneNumCol;
    @FXML
    TableColumn<Person,Person> deleteCol;

    private static Logger logger = LoggerFactory.getLogger("NezetController");

    private final String MENU_CONTACTS = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_NEW = "Új";
    private final String MENU_SAVE = "Mentés";
    private final String MENU_QUIT = "Kilépés";

    @FXML
    private void addContact(ActionEvent event) {
        String lastname = inputLastName.getText();
        String firstname = inputFirstName.getText();
        String phonenumber = inputPhoneNumber.getText();
        lastname = lastname.replaceAll("\\s+", "");
        firstname = firstname.replaceAll("\\s+", "");
        phonenumber = phonenumber.replaceAll("\\s+", "");

        if(ezHitelesNev(firstname) && ezHitelesNev(lastname) && ezHitelesTelefonszam(phonenumber)){
            Person tmp = new Person(lastname,firstname,phonenumber);
            DB.create(tmp);

            table.getItems().add(tmp);

            inputLastName.clear();
            inputFirstName.clear();
            inputPhoneNumber.clear();
            errorLabel.setVisible(false);
        }else {
            logger.info("Hibás Adatokkal Próbál meg Kontaktot Generálni!");
            errorLabel.setText("Hibás Adatokkal Próbál meg Kontaktot Generálni!");
            errorLabel.setVisible(true);
        }

    }

    @FXML
    private void handleAlertButton(ActionEvent event) {
        alertPane.setVisible(false);
        menuPane.setOpacity(1);
        savePane.setOpacity(1);
        newPane.setOpacity(1);
    }

    @FXML
    private void handleAlert2Button(ActionEvent event) {
        alert2Pane.setVisible(false);
        menuPane.setOpacity(1);
        savePane.setOpacity(1);
        newPane.setOpacity(1);
    }

    @FXML
    private void saveList(ActionEvent event) {
        String fileName = inputSaveName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            PdfGeneration pdfCreator = new PdfGeneration();
            pdfCreator.pdfGeneration(fileName, table.getItems());
        } else {
            menuPane.setOpacity(0.1);
            savePane.setOpacity(0.1);
            alertPane.setVisible(true);
            logger.info("Nem lett megadva fájlnév");
        }
        inputSaveName.clear();
    }

    private void setMenu() {
        TreeItem<String> treeItemRoot = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot);
        treeView.setShowRoot(false);

        TreeItem<String> contactMenu = new TreeItem<>(MENU_CONTACTS);
        Node quitPicture = new ImageView(new Image(getClass().getResourceAsStream("/pictures/quit.png")));
        TreeItem<String> quitMenu = new TreeItem<>(MENU_QUIT, quitPicture);

        Node listPicture = new ImageView(new Image(getClass().getResourceAsStream("/pictures/contacts.png")));
        TreeItem<String> contactMenuList = new TreeItem<>(MENU_LIST, listPicture);
        Node newPicture = new ImageView(new Image(getClass().getResourceAsStream("/pictures/new.png")));
        TreeItem<String> contactMenuNew = new TreeItem<>(MENU_NEW, newPicture);
        Node savePicture = new ImageView(new Image(getClass().getResourceAsStream("/pictures/save.png")));
        TreeItem<String> contactMenuSave = new TreeItem<>(MENU_SAVE, savePicture);

        contactMenu.getChildren().addAll(contactMenuList, contactMenuNew, contactMenuSave);
        treeItemRoot.getChildren().addAll(contactMenu, quitMenu);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CONTACTS:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPane.setVisible(true);
                            newPane.setVisible(false);
                            savePane.setVisible(false);
                            break;
                        case MENU_NEW:
                            contactPane.setVisible(false);
                            newPane.setVisible(true);
                            savePane.setVisible(false);
                            break;
                        case MENU_SAVE:
                            contactPane.setVisible(false);
                            newPane.setVisible(false);
                            savePane.setVisible(true);
                            break;
                        case MENU_QUIT:
                            System.exit(0);
                            DB.closeEmf();
                            break;
                    }
                }
            }
        });
    }

    private void initTableCols(){
        id.setCellValueFactory(new PropertyValueFactory<Person, Long>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
        phoneNumCol.setCellValueFactory(new PropertyValueFactory<Person,String>("phoneNumber"));
        deleteCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        makeItEditable();
    }

    private void makeItEditable() {
        table.setEditable(true);

        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(editEvent -> {
            Person tmp = editEvent.getTableView().getItems().
                    get(editEvent.getTablePosition().getRow());
            if(ezHitelesNev(editEvent.getNewValue())){
                tmp.setFirstName(editEvent.getNewValue());
                DB.edit(tmp);

            }else {
                editEvent.getTableView().getItems().set(editEvent.getTablePosition().getRow(), tmp);
                errorLabel.setVisible(true);
                logger.info("Érvénytelen Adat!");

            }

        });

        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(editEvent -> {
            Person tmp = editEvent.getTableView().getItems().
                    get(editEvent.getTablePosition().getRow());
            if(ezHitelesNev(editEvent.getNewValue())){
                tmp.setFirstName(editEvent.getNewValue());
                DB.edit(tmp);
                errorLabel.setVisible(false);
            }else {
                editEvent.getTableView().getItems().set(editEvent.getTablePosition().getRow(), tmp);
                errorLabel.setVisible(true);
                logger.info("Érvénytelen Adat!");

            }
        });
        phoneNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumCol.setOnEditCommit(editEvent -> {
            Person tmp = editEvent.getTableView().getItems().
                    get(editEvent.getTablePosition().getRow());
            if(ezHitelesTelefonszam(editEvent.getNewValue())){
                tmp.setFirstName(editEvent.getNewValue());
                DB.edit(tmp);
                errorLabel.setVisible(false);
            }else {
                editEvent.getTableView().getItems().set(editEvent.getTablePosition().getRow(), tmp);
                errorLabel.setVisible(true);
                logger.info("Érvénytelen Adat!");

            }
        });
        deleteCol.setCellFactory(param -> new TableCell<Person, Person>() {
            private final Button deleteButton = new Button("Töröl");

            @Override
            protected void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);

                if (person == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(deleteButton);
                deleteButton.setOnAction(actionEvent -> deleteFromTable(getTableView(), person)
                );}
        });

    }
    void deleteFromTable (TableView<Person> tableView, Person person){
        tableView.getItems().remove(person);
        DB.delete(person);
    }

    private void loadData(){
        ObservableList<Person> tableData = FXCollections.observableArrayList(DB.getAllAsList());
        table.setItems(tableData);
    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        initTableCols();
        loadData();
        setMenu();
    }
}


