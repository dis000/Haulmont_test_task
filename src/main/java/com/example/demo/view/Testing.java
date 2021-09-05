package com.example.demo.view;


import com.example.demo.entities.Bank;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.*;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "test", layout = Menu.class)
public class Testing extends VerticalLayout  {




/*

    public MainView () {


        add(new Button("Click me", e -> Notification.show("2")));
    }
*/

    @Autowired
    public Testing(
            IBankService bankService,
            IClientService clientService,
            ICreditService creditService,
            ICreditOfferService creditOfferService,
            IPaymentScheduleService paymentScheduleService) {


        bankService.save(new Bank("testBank"));







    }

}

