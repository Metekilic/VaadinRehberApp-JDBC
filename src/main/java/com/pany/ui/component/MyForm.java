package com.pany.ui.component;

import com.pany.core.dao.PersonDao;
import com.pany.core.domain.Person;
import com.pany.ui.MyUI;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

import java.sql.SQLException;
import java.util.List;

public class MyForm extends FormLayout {


    private Person selectedPerson;
    private TextField idField;
    private TextField name;
    private TextField number;
    private Button btnGuncelle;
    private Button btnSil;
    PersonDao personDao = new PersonDao();

    public MyForm() {
        idField = new TextField("Id");
        name = new TextField("Name");
        number = new TextField("Number");
        btnGuncelle = new Button("Güncelle");
        btnSil = new Button("Sil");
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(btnGuncelle, btnSil);

        addComponents(idField, name, number, horizontalLayout);

        updateFormData();
        buildPersonDeleteButton();
    }

    public void setItemDataSource(Person person) {
        idField.setValue(String.valueOf(person.getId()));
        name.setValue(person.getName());
        number.setValue(person.getNumber());
    }

    private void buildLayout(){

        Button btnSil = buildPersonDeleteButton();
        addComponent(btnSil);
    }

    private void updateFormData(){

        getBtnGuncelle().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                if (idField.getValue() != null) {

                }

                Person p2 = new Person();
                p2.setName(getName().getValue());
                p2.setNumber(getNumber().getValue());
                p2.setId(Integer.parseInt(getIdField().getValue()));
                try {
                    boolean isUpdated = personDao.updatePerson(p2);
                    System.out.println(isUpdated);
                    ((MyUI) MyUI.getCurrent()).fillContainer();



                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private Button buildPersonDeleteButton(){

        getBtnSil().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Person p3 = new Person();
                p3.setName(getName().getValue());
                try {
                    boolean isDeleted = personDao.deleteEmployee(p3);
                    System.out.println(isDeleted);
                    ((MyUI) MyUI.getCurrent()).fillContainer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        return btnSil;
    }

    public TextField getIdField() {
        return idField;
    }

    public void setIdField(TextField idField) {
        this.idField = idField;
    }

    public TextField getName() {
        return name;
    }

    public void setName(TextField name) {
        this.name = name;
    }

    public TextField getNumber() {
        return number;
    }

    public void setNumber(TextField number) {
        this.number = number;
    }

    public Button getBtnGuncelle() {
        return btnGuncelle;
    }

    public void setBtnGuncelle(Button btnGuncelle) {
        this.btnGuncelle = btnGuncelle;
    }

    public Button getBtnSil() {
        return btnSil;
    }

    public void setBtnSil(Button btnSil) {
        this.btnSil = btnSil;
    }

    private void buildMyForm() {

    }



    public Person getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }
}