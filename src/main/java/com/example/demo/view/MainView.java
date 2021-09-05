package com.example.demo.view;


import com.example.demo.entities.Bank;
import com.example.demo.entities.Client;
import com.example.demo.entities.Credit;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.*;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.UUID;

@Route(value = "database", layout = Menu.class)
public class MainView extends VerticalLayout  {




/*

    public MainView () {


        add(new Button("Click me", e -> Notification.show("2")));
    }
*/

    @Autowired
    public MainView(
            IBankService bankService,
            IClientService clientService,
            ICreditService creditService,
            ICreditOfferService creditOfferService,
            IPaymentScheduleService paymentScheduleService) {


        TextField textField = new TextField();






   //     Client client = new Client("Nikita","8987211211","3413242",bankService.getAll().get(0));


        Tab tab = new Tab("Клиенты");
        Tab tab1 = new Tab("Кредиты");
        Tab tab2 = new Tab("График платежей");

        Tabs tabs = new Tabs(tab,tab1,tab2);



        Grid <PaymentSchedule > grid = new Grid<>(PaymentSchedule.class);





        Button button = new Button("search");
        button.addClickListener(buttonClickEvent -> {
            Collection<PaymentSchedule> bewohnerList = paymentScheduleService.getByPassportID(textField.getValue());
            grid.setItems(bewohnerList);

        });



        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        button.getElement().getStyle().set("margin-left", "auto");
        horizontalLayout.add(tabs, button, textField);

        add(horizontalLayout);
        setHorizontalComponentAlignment(Alignment.END,horizontalLayout);
 //       add();
        add(grid);










    }

}

