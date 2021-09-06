package com.example.demo.view;


import com.example.demo.entities.Client;
import com.example.demo.entities.Credit;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.IBankService;
import com.example.demo.service.interfaces.IClientService;
import com.example.demo.service.interfaces.IPaymentScheduleService;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(value = "database", layout = Menu.class)
public class Database extends VerticalLayout  {


    Grid <Client> clientsGrid = new Grid<>(Client.class);
    Grid <PaymentSchedule> scheduleGrid = new Grid<>(PaymentSchedule.class);
    Grid <Client> bankClientsGrid = new Grid<>(Client.class);
    Grid <Credit> bankCreditsGrid = new Grid<>(Credit.class);


    Tabs tabs = new Tabs();
    Tab tab = new Tab("Клиенты");
    Tab tab2 = new Tab("График платежей");
    Tab tab3 = new Tab("Клиенты банка");
    Tab tab4 = new Tab("Кредиты банка");

    TextField searchField = new TextField();

    Button button = new Button("search");

    @Autowired
    public Database(
            IBankService bankService,
            IClientService clientService,
            IPaymentScheduleService paymentScheduleService) {

        searchField.setHelperText("поиск по паспорту");
        searchField.setWidth(8, Unit.CM);

        scheduleGrid.setVisible(false);

        clientsGrid.removeColumnByKey(clientsGrid.getColumns().get(2).getKey());

        bankClientsGrid.removeColumnByKey(bankClientsGrid.getColumns().get(2).getKey());
        bankClientsGrid.setVisible(false);


        bankCreditsGrid.removeColumnByKey(bankCreditsGrid.getColumns().get(3).getKey());
        bankCreditsGrid.setVisible(false);


        initializeTabs();

        initializeButton(clientService,paymentScheduleService,bankService);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        button.getElement().getStyle().set("margin-left", "auto");
        horizontalLayout.add(tabs, button, searchField);

        add(horizontalLayout);
        setHorizontalComponentAlignment(Alignment.END,horizontalLayout);
        add(clientsGrid,scheduleGrid,bankClientsGrid,bankCreditsGrid);

    }


    private void gridVisibleChanger(String tabString, Boolean bool) {


        switch (tabString) {
            case "Клиенты" : {
                clientsGrid.setVisible(bool);
                searchField.setHelperText("поиск по паспорту");
                break;
            }
            case "График платежей" :{
                scheduleGrid.setVisible(bool);
                searchField.setHelperText("поиск по паспорту");
                break;
            }
            case "Клиенты банка" :{
                bankClientsGrid.setVisible(bool);
                searchField.setHelperText("поиск по имени банка(default testBank)");
                break;
            }
            case "Кредиты банка" :{
                bankCreditsGrid.setVisible(bool);
                searchField.setHelperText("поиск по имени банка(default testBank)");
                break;
            }
        }
    }

    private void initializeTabs () {

        tabs.add(tab,tab2,tab3,tab4);

        tabs.addSelectedChangeListener(selectedChangeEvent -> {
            if (selectedChangeEvent.getSelectedTab().equals(selectedChangeEvent.getPreviousTab()))
                return;

            gridVisibleChanger(selectedChangeEvent.getPreviousTab().getLabel(),false);
            gridVisibleChanger(selectedChangeEvent.getSelectedTab().getLabel(), true);
        });
    }

    private void initializeButton (
            IClientService clientService,
            IPaymentScheduleService paymentScheduleService,
            IBankService bankService) {

        button.addClickListener(buttonClickEvent -> {


            switch (tabs.getSelectedTab().getLabel()) {
                case "Клиенты": {
                    List<Client> clientList = new ArrayList<>();
                    clientList.add(clientService.getByPassportID(searchField.getValue()));
                    clientsGrid.setItems(clientList);
                    break;
                }
                case "График платежей": {
                    Collection<PaymentSchedule> scheduleCollection = paymentScheduleService.getByPassportID(searchField.getValue());
                    scheduleGrid.setItems(scheduleCollection);
                    break;
                }
                case "Клиенты банка": {
                    Collection<Client> clientCollection = bankService.getBankClients(bankService
                            .getByName(searchField.getValue()).getID()).getClients();
                    bankClientsGrid.setItems(clientCollection);
                    break;
                }
                case "Кредиты банка": {
                    Collection<Credit> creditCollection = bankService.getBankCredits(bankService
                            .getByName(searchField.getValue()).getID()).getCredits();
                    bankCreditsGrid.setItems(creditCollection);
                    break;
                }
            }

        });
    }

}

