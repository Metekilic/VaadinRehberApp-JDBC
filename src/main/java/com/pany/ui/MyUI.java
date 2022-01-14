package com.pany.ui;

import com.pany.core.dao.PersonDao;
import com.pany.core.domain.Person;
import com.pany.ui.component.MyForm;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MultiSelectMode;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.pany.MyAppWidgetset")
public class MyUI extends UI {

    private HorizontalLayout mainLayout;
    private Table table;
    private IndexedContainer indexedContainer;
    private Panel panel;
    private Panel buyukPanel;
    private FormLayout content;
    private TextField tfAd;
    private TextField tfNumara;
    private VerticalLayout panelContent;
    private Button btnEkle;
    private MyForm myform;
    PersonDao personDao = new PersonDao();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildMainLayout();
        setContent(mainLayout);
    }

    private void buildMainLayout() {
        mainLayout = new HorizontalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        buildPanel();
        mainLayout.addComponent(panel);

        buildBuyukPanel();
        mainLayout.addComponent(buyukPanel);
    }



    private void buildAddButton() {
        btnEkle = new Button("Ekle");
        btnEkle.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Person person = new Person();
                person.setName(tfAd.getValue());
                person.setNumber(tfNumara.getValue());
                boolean isInserted = personDao.savePerson(person);
                System.out.println(isInserted);
                fillContainer();
            }
        });
    }

    private void buildPanelContent() {
        panelContent = new VerticalLayout();
        panelContent.setSizeFull();
        panelContent.setMargin(true);

        buildContainer();
        buildTable();
        panelContent.addComponent(table);
        panelContent.setExpandRatio(table, 1.0f);

        myform = new MyForm();
        myform.setVisible(false);
        panelContent.addComponent(myform);

        /*myform.getBtnGuncelle().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                Person p2 = new Person();
                p2.setName(myform.getName().getValue());
                p2.setNumber(myform.getNumber().getValue());
                p2.setId(Integer.parseInt(myform.getIdField().getValue()));

                try {
                    boolean isUpdated = personDao.updatePerson(p2);
                    System.out.println(isUpdated);

                    fillContainer();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });*/


        /*myform.getBtnSil().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Person p3 = new Person();
                p3.setName(myform.getName().getValue());
                try {
                    boolean isDeleted = personDao.deleteEmployee(p3);
                    System.out.println(isDeleted);
                    fillContainer();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });*/

    }

    private void buildBuyukPanel() {
        buyukPanel = new Panel("Rehber");
        buyukPanel.setWidth("600px");
        buyukPanel.setHeight("500px");

        buildPanelContent();
        buyukPanel.setContent(panelContent);
    }

    private void buildContentFormLayout() {
        //içeriği oluşturduğum kısım
        content = new FormLayout();
        content.addStyleName("Rehbere Ekleme");
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);

        tfAd = new TextField("Adı");
        content.addComponent(tfAd);

        tfNumara = new TextField("Numarası");
        content.addComponent(tfNumara);

        buildAddButton();
        content.addComponent(btnEkle);
    }

    private void buildPanel() {
        panel = new Panel();
        panel.addStyleName("mypanelexample");
        panel.setSizeUndefined(); // Shrink to fit content

        buildContentFormLayout();
        panel.setContent(content);
    }

    public void fillContainer() {
        getIndexedContainer().removeAllItems();

        List<Person> personList = personDao.findAllPerson();
        for (Person person : personList) {
            Item item = getIndexedContainer().addItem(person);
            item.getItemProperty("id").setValue(person.getId());
            item.getItemProperty("name").setValue(person.getName());
            item.getItemProperty("number").setValue(person.getNumber());
        }
    }

    private void buildContainer() {
        indexedContainer = new IndexedContainer();

        indexedContainer.addContainerProperty("id", Integer.class, null);
        indexedContainer.addContainerProperty("name", String.class, null);
        indexedContainer.addContainerProperty("number", String.class, null);
    }

    private void buildTable() {

        table = new Table();
        table.setImmediate(true);
        table.setContainerDataSource(indexedContainer);
        table.setSizeFull();

        table.setSelectable(true);
        table.setMultiSelectMode(MultiSelectMode.SIMPLE);
        table.setMultiSelect(false);

        table.setColumnWidth("id", 45);
        table.setColumnWidth("name", 300);
        table.setColumnWidth("number", 200);
        table.setColumnAlignment("number", Table.Align.LEFT);

        table.setVisibleColumns("id", "name", "number");
        table.setColumnHeaders("ID", "NAME", "NUMBER");
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Person selectedItemId =  (Person) event.getProperty().getValue();
                if (selectedItemId != null) {
                    myform.setVisible(true);
                    myform.setItemDataSource(selectedItemId);
                    if (!myform.isVisible()) {
                        table.setCurrentPageFirstItemId(selectedItemId);


                    }
                } else
                    myform.setVisible(false);
            }
        });
    }

    private void fillColumns(Person person, Item item) {
        item.getItemProperty("id").setValue(person.getId());
        item.getItemProperty("name").setValue(person.getName());
        item.getItemProperty("number").setValue(person.getName());
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    public IndexedContainer getIndexedContainer() {
        return indexedContainer;
    }

    public void setIndexedContainer(IndexedContainer indexedContainer) {
        this.indexedContainer = indexedContainer;
    }
}