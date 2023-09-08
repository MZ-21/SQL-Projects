package se2203b.assignments.ifinance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

// will be complete in assignment 4
    public class AccountGroupsController implements Initializable {
    // will be complete in assignment 4
    private AccountCategoryAdapter accountCategoryAdapter;
    private GroupAdapter group;

    private TreeItem<String> assets;
    private TreeItem<String> liabilities;
    private TreeItem<String> income;
    private TreeItem<String> expense;
    private TreeItem dummy;
    @FXML
    private MenuItem addGroup;
    @FXML
    private MenuItem deleteGroup;

    @FXML
    private MenuItem updateGroup;

    @FXML
    private TextField txtF;

    @FXML
    private Button saveB;

    private Integer parent;

    private String accountCategory;

    private String groupNameProperty;


    @FXML
    private TreeView view;

    public void setModel(AccountCategoryAdapter add, GroupAdapter g) throws SQLException {
        accountCategoryAdapter = add;
        group = g;

        initiallyPopulate();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        assets = new TreeItem("Assets");
        liabilities = new TreeItem("Liabilities");
        income = new TreeItem("Income");
        expense = new TreeItem("Expenses");
        dummy = new TreeItem("dummy");
        dummy.getChildren().addAll(assets, liabilities, income, expense);
        view.setRoot(dummy);
        view.setShowRoot(false);
        disable();
    }

    public void addGroup() throws SQLException {
        ObservableList<String> namesOfGroups = group.getName();
//            System.out.println(namesOfGroups);
//            System.out.println(groupNameProperty);
//            System.out.println(view.getSelectionModel().getSelectedItem());
        TreeItem treeItemSelected = (TreeItem) view.getSelectionModel().getSelectedItem();
        if (namesOfGroups.contains(groupNameProperty) || dummy.getChildren().contains(treeItemSelected)) {
            TreeItem treeItems = new TreeItem(txtF.getText());
            treeItemSelected.getChildren().add(treeItems);


        }
//        if (group.findCategory(groupNameProperty).equals("Liabilities")) {
//            TreeItem treeItems = new TreeItem(txtF.getText());
//            treeItemSelected.getChildren().add(treeItems);
//
//        }

    }

//
//            }
//        public void newGroup(Integer num,String n, TreeItem nP){
//            TreeItem item= new TreeItem<>(n);
//            nP.getChildren().add(item);
//            while (num!=0){
//                newGroup(num,,item);
//                num--;
//            }
//        }

    //ObservableList<TreeItem<String>> treeItems = dummy.getChildren();

    private void recursiveChildren(TreeItem<String> root, String parentOfChild, String nameChild) {
        for (TreeItem<String> child : root.getChildren()) {
            if (child.getValue().equals(parentOfChild)) {
                TreeItem<String> item = new TreeItem<>(nameChild);
                child.getChildren().add(item);
            } else {
                recursiveChildren(child, parentOfChild, nameChild);
            }
        }
    }

    public void initiallyPopulate() throws SQLException {

        ObservableList<String> namesOfGroup = group.getName();
        ObservableList<String> allGroupInfo = group.getDataBaseInfo();


        for (int i = 0; i < namesOfGroup.size(); i++) {
            String[] splitInfo = allGroupInfo.get(i).split("-");
            //ObservableList<Integer> listG = FXCollections.observableArrayList();
            //ObservableList<Integer> allIds = group.getCHILDRENGroups(Integer.valueOf(splitInfo[0]), listG);
            if (Integer.valueOf(splitInfo[2]).equals(0)) {
                TreeItem<String> items = new TreeItem<>(splitInfo[1]);
                //treeItems.add(treeItem);
                if (splitInfo[3].equals("Assets")) {
                    assets.getChildren().add(items);
                } else if (splitInfo[3].equals("Liabilities")) {
                    liabilities.getChildren().add(items);
                } else if (splitInfo[3].equals("Expenses")) {
                    expense.getChildren().add(items);
                } else if (splitInfo[3].equals("Income")) {
                    income.getChildren().add(items);
                }
            } else {
                //System.out.println(group.getGroupsName(Integer.valueOf(splitInfo[2])));
                recursiveChildren(assets, group.getGroupsName(Integer.valueOf(splitInfo[2])), splitInfo[1]);
                recursiveChildren(liabilities, group.getGroupsName(Integer.valueOf(splitInfo[2])), splitInfo[1]);
                recursiveChildren(income, group.getGroupsName(Integer.valueOf(splitInfo[2])), splitInfo[1]);
                recursiveChildren(expense, group.getGroupsName(Integer.valueOf(splitInfo[2])), splitInfo[1]);
            }
            // System.out.println(allIds);
        }

    }


//            System.out.println(allGroupInfo);
//            for (int i = 0; i < namesOfGroup.size(); i++) {
//                String[] splitInfo = allGroupInfo.get(i).split("-");
//                if (Integer.valueOf(splitInfo[2]).equals(0)) {
//                    ObservableList<Integer> allIds = group.getCHILDRENGroups(Integer.valueOf(splitInfo[0]), listG);
//                    System.out.println(allIds);
//                    if (splitInfo[3].equals("Assets")) {
//                        for (int k = 1; k < allIds.size(); k++) {
//                            TreeItem item = new TreeItem<>(group.getGroupsName(allIds.size() - 1));
//                            // System.out.println(item);
//                            assets.getChildren().add(item);
//                            item.getChildren().add(new TreeItem(group.getGroupsName(allIds.size() - 2)));
//
//                        }
//                    }
//
//
//                }
//            }

//            for (int i=0;i<namesOfGroup.size();i++) {
//                String[] splitInfo = allGroupInfo.get(i).split("-");
//                ObservableList<Integer> listG = FXCollections.observableArrayList();
//                ObservableList<Integer> allIds= group.getCHILDRENGroups(Integer.valueOf(splitInfo[0]),listG);
//                for(int k = allIds.size()-1;k>0;k--){
//                    //if the parent is zero
////                if(Integer.valueOf(splitInfo[2]).equals(0)){
//                    if(splitInfo[3].equals("Assets")){
//                        TreeItem item = new TreeItem<>(splitInfo[1]);
//                        assets.getChildren().add(item);
//                        initiallyPopulate(item);
//                    }
//                    else if(splitInfo[3].equals("Liabilities")){
//                        TreeItem item = new TreeItem<>(splitInfo[1]);
//                        liabilities.getChildren().add(item);
//                    }
//                    else if(splitInfo[3].equals("Expenses")){
//                        TreeItem item = new TreeItem<>(splitInfo[1]);
//                        expense.getChildren().add(item);
//                    }
//                    else if(splitInfo[3].equals("Income")){
//                        TreeItem item = new TreeItem<>(splitInfo[1]);
//                        income.getChildren().add(item);
//                    }
//
//               // }
//              //  else {
////
////                    for(int k = allIds.size()-1;k>0;k--){
//
//
//                //    }
//
//                }
//
//            }
//
////                TreeItem treeItems = new TreeItem(namesOfGroup.get(i));
////                assets.getChildren().add(treeItems);

    public void updateGroupName() throws SQLException {

        group.updateGInfo(txtF.getText(),groupNameProperty);
        //TreeItem t = <TreeItem> view.getSelectionModel().getSelectedItem();
        TreeItem treeItemSelected = (TreeItem) view.getSelectionModel().getSelectedItem();
        treeItemSelected.setValue(txtF.getText());

    }
    public void deleteGroupN() throws SQLException {
        TreeItem treeItemSelected = (TreeItem) view.getSelectionModel().getSelectedItem();
        ObservableList<TreeItem> treeItemSelectedParentsChildren = treeItemSelected.getParent().getChildren();
        if (treeItemSelected.getChildren().isEmpty()) {
            group.deleteInfo(groupNameProperty);
            for (int i = 0; i < treeItemSelectedParentsChildren.size(); i++) {
                if (treeItemSelectedParentsChildren.get(i).equals(treeItemSelected)) {
                    treeItemSelectedParentsChildren.remove(treeItemSelectedParentsChildren.get(i));
                }
            }
        }
        else {deleteGroup.setVisible(false);}
    }

    public void saveInfo() throws SQLException {

        String[] splitParentInfo = group.getParentInfo(groupNameProperty).toString().replace("[", "").replace("]", "").split("-");
        TreeItem treeItemSelected = (TreeItem) view.getSelectionModel().getSelectedItem();
        ObservableList<String> namesOfGroups = group.getName();
        Integer idOfParent = 0;
        TreeItem treeItems = new TreeItem(txtF.getText());
        String accountNameOfParent = "";
        String checkParentExistence = "";
        String groupName = txtF.getText();

        if (!txtF.getText().equals(null)) {
            if (namesOfGroups.contains(groupNameProperty)) {
                accountNameOfParent = splitParentInfo[2];
                idOfParent = Integer.valueOf(splitParentInfo[0]);

                Group group1 = new Group(group.getMax(), groupName, group.recursionGroup(idOfParent).getParent(), accountCategoryAdapter.parentAccount(accountNameOfParent));
                group.insertRecord(group1);
                group.updateGroupNotNull(idOfParent, txtF.getText());
            } else if (dummy.getChildren().contains(treeItemSelected)) {
                accountNameOfParent = groupNameProperty;
                //System.out.println(group.parentAccount(accountNameOfParent).getType());
                Group group1 = new Group(group.getMax(), groupName, new Group().getParent(), accountCategoryAdapter.parentAccount(accountNameOfParent));
                group.insertRecord(group1);
                group.updateGroupNull();
            }
        }

    }


    public void contextMenuDisplayed() {
        ContextMenu menu = new ContextMenu();
        menu.getItems().addAll(addGroup = new MenuItem("Add Group"), deleteGroup = new MenuItem("Delete Group"), updateGroup = new MenuItem("Update Group"));

        TreeItem treeItemSelected = (TreeItem) view.getSelectionModel().getSelectedItem();

            if (treeItemSelected.getChildren().isEmpty()) {
                deleteGroup.setDisable(false);
            } else if (!treeItemSelected.getChildren().isEmpty()) {
                deleteGroup.setDisable(true);
                //System.out.println("in tru");
            }


        view.setContextMenu(menu);

        //if the selectedItem has children, don't let them delete

        addGroup.setOnAction(actionEvent -> {
            enable();

            saveB.setOnAction(actionEvent1 -> {
                try {
                    addGroup();
                    saveInfo();
                    txtF.setText("");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

        });
        updateGroup.setOnAction(actionEvent ->{
            enable();
            saveB.setOnAction(actionEvent1 -> {
                try {
                    updateGroupName();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        });
        deleteGroup.setOnAction(actionEvent ->{
            enable();
            try {
                saveB.setDisable(true);
                txtF.setDisable(true);
                deleteGroupN();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        String groupNExtra = view.getSelectionModel().getSelectedItem().toString();
        String groupN = groupNExtra.substring(groupNExtra.indexOf(":") + 2, groupNExtra.length() - 2);
        groupNameProperty = groupN;
        //System.out.println(groupNameProperty);
    }

    public void enable() {
        txtF.setDisable(false);
        saveB.setDisable(false);
        deleteGroup.setDisable(false);
    }

    public void disable() {
        txtF.setDisable(true);
        saveB.setDisable(true);

    }


}

