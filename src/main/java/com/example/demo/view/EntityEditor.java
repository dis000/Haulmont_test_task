package com.example.demo.view;

import com.example.demo.entities.Bank;
import com.example.demo.service.interfaces.*;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "entity_editor", layout = Menu.class)
public class EntityEditor extends VerticalLayout {


    @Autowired
    public EntityEditor(
            IBankService bankService,
            IClientService clientService,
            ICreditService creditService,
            ICreditOfferService creditOfferService,
            IPaymentScheduleService paymentScheduleService) {

        System.out.println(bankService.getBankClients(bankService.getAll().get(0).getID()).getClients());


/*


        ComboBox<Bank> comboBox = new ComboBox<>();

        comboBox.setItems(bankService.getAll());

        Button button = new Button("Удалить");
        button.addClickListener(buttonClickEvent -> {
            System.out.println(bankService.getBankClients(comboBox.getValue().getID()).getClients());
        });

        Button button1 = new Button("Редактировать");
        HorizontalLayout horizontalLayout = new HorizontalLayout(comboBox,button,button1);



        add(horizontalLayout);
*/


    }
}
