package com.example.demo.view;

import com.example.demo.entities.Bank;
import com.example.demo.entities.Credit;
import com.example.demo.service.interfaces.IBankService;
import com.example.demo.service.interfaces.ICreditService;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "create_credit", layout = Menu.class)
public class CreateCredit extends VerticalLayout  {


    Select<Bank> banksComboBox;
    BigDecimalField creditLimit = new BigDecimalField("Макс сумма кредита");
    BigDecimalField percentRate = new BigDecimalField("Процент кредита");


    @Autowired
    public CreateCredit(IBankService bankService, ICreditService creditService) {


        banksComboBox = createComboBox(bankService);

        setWidthFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(banksComboBox);

        add(new HorizontalLayout(creditLimit, percentRate));

        add(createButton(creditService));


    }
    private Select<Bank> createComboBox(IBankService bankService) {
        Select<Bank> banksComboBox = new Select<>();
        banksComboBox.setPlaceholder("выбрать Банк");


        try{
            banksComboBox.setItems(bankService.getAll());
        }catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return banksComboBox;

    }

    private Component createButton(ICreditService creditService) {
        Button button = new Button();

        button.addClickListener(buttonClickEvent -> {
            if (banksComboBox.getValue()==null | creditLimit.getValue() == null | percentRate.getValue() == null) {
                Notification.show("Все поля должны быть заполнены");
                return;
            }
            Credit credit = new Credit(
                    banksComboBox.getValue(),
                    creditLimit.getValue(),
                    percentRate.getValue());

            creditService.update(credit);
        });
        return button;
    }
}
