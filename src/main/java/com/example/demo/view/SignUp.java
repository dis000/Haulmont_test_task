package com.example.demo.view;

import com.example.demo.entities.Bank;
import com.example.demo.entities.Client;
import com.example.demo.service.interfaces.IBankService;
import com.example.demo.service.interfaces.IClientService;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "sign_up", layout = Menu.class)
public class SignUp extends VerticalLayout {

    private TextField name = new TextField("ФИО Полностью");
    private TextField phone = new TextField("Номер телефона");
    private TextField passportID = new TextField("Номер паспорта");
    private Select<Bank> banksComboBox = new Select<>();

    private Button save = new Button("Save");

    @Autowired
    public SignUp(IClientService clientService, IBankService bankService) {

        createComboBox(bankService);


        name.setWidth(8F, Unit.CM);
        phone.setWidth(8F, Unit.CM);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(name, phone);
        passportID.setWidth(8F, Unit.CM);
        banksComboBox.setWidth(8F, Unit.CM);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout(banksComboBox, passportID);


        setDefaultHorizontalComponentAlignment(Alignment.CENTER);


        add(createTitle());
        add(horizontalLayout1, horizontalLayout2);
        add(createButtonLayout(clientService));
    }



    private Component createTitle() {
        return new H3("Регистрация");
    }


    private Component createButtonLayout(IClientService clientService) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);

        buttonLayout.addClickListener(clickEvent -> clientService
                .update(new Client(
                        name.getValue(),
                        phone.getValue(),
                        passportID.getValue(),
                        banksComboBox.getValue()
                )));

        return buttonLayout;
    }


    private void createComboBox(IBankService bankService) {
        banksComboBox.setPlaceholder("Банк");
        banksComboBox.setLabel("Выбор банка");
        try{
            banksComboBox.setItems(bankService.getAll());
        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }
    }

}
