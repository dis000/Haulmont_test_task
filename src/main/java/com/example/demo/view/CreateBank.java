package com.example.demo.view;

import com.example.demo.entities.Bank;
import com.example.demo.service.interfaces.IBankService;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "create_bank", layout = Menu.class)
public class CreateBank  {

    @Autowired
    public CreateBank(IBankService bankService) {
    }
}
