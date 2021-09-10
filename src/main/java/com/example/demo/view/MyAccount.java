package com.example.demo.view;

import com.example.demo.entities.Client;
import com.example.demo.entities.CreditOffer;
import com.example.demo.entities.PaymentSchedule;
import com.example.demo.service.interfaces.*;
import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = "my_account", layout = Menu.class)
public class MyAccount extends VerticalLayout {

    IClientService clientService;
    ICreditOfferService creditOfferService;
    IPaymentScheduleService paymentScheduleService;

    private Client client = new Client();
    private final TextField nameField = new TextField("ФИО");
    private final TextField phoneField = new TextField("Телефон");
    private final TextField passportField = new TextField("Номер паспорта");
    private final TextField bankField = new TextField("Ваш банк");
    private final BigDecimalField indebtedness = new BigDecimalField();
    private final Select<Client> clientSelect = new Select<>();
    private final Select<CreditOffer> creditOfferSelect = new Select<>();

    private final Button button = new Button("Изменить");
    private final Button button1 = new Button("Сохранить");

    public MyAccount(
            @Autowired IClientService clientS,
            @Autowired ICreditOfferService creditOfferS,
            @Autowired IPaymentScheduleService scheduleS) {

        this.clientService = clientS;
        this.creditOfferService = creditOfferS;
        this.paymentScheduleService = scheduleS;




        configureSelects();

        configureFields();

        configureButtons();


        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(nameField, phoneField, button);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(passportField, bankField, button1);

        add(clientSelect,horizontalLayout,horizontalLayout1,creditOfferSelect, indebtedness);

    }



    private void configureSelects() {
        creditOfferSelect.setVisible(false);
        creditOfferSelect.setLabel("Просмотр задолжности");

        clientSelect.setLabel("Тестовое поле");

        clientSelect.setItems(clientService.getAll());

        clientSelect.addValueChangeListener(select -> {
            client = select.getValue();
            nameField.setValue(client.getName());
            phoneField.setValue(client.getPhone());
            passportField.setValue(client.getPassportID());
            bankField.setValue(client.getBank().toString());
            List<CreditOffer> creditOffer = creditOfferService.getByClientID(client.getID());
            creditOfferSelect.setItems(creditOffer);
            creditOfferSelect.setVisible(true);
            button.setEnabled(true);
        });

        creditOfferSelect.addValueChangeListener(select -> {
            List<PaymentSchedule> scheduleList = paymentScheduleService.getByPassportID(client.getPassportID());

            List<PaymentSchedule> resScheduleList = scheduleList.stream().filter(paymentSchedule ->
                    paymentSchedule.getPaymentDate().isBefore(LocalDate.now().plusMonths(4))
                            &
                            creditOfferSelect.getValue().getID().equals(paymentSchedule.getCreditOffer().getID()))
                    .collect(Collectors.toList());

            BigDecimal bigDecimal = new BigDecimal(0);
            for (PaymentSchedule schedule:
                 resScheduleList) {
                bigDecimal = bigDecimal.add(schedule.getAmountOfPayment());
            }

            indebtedness.setValue(bigDecimal);
            indebtedness.setLabel("нужно оплатить до " + LocalDate.now().plusMonths(4));
        });
    }
    private void configureFields() {
        indebtedness.setWidth(6F, Unit.CM);
        creditOfferSelect.setWidth(6F, Unit.CM);
        clientSelect.setWidth(6F, Unit.CM);

        bankField.setReadOnly(true);
        indebtedness.setReadOnly(true);
        nameField.setReadOnly(true);
        phoneField.setReadOnly(true);
        passportField.setReadOnly(true);
        button1.setEnabled(false);
        button.setEnabled(false);



    }

    private void configureButtons() {
        button.addClickListener(buttonClickEvent -> {
            nameField.setReadOnly(false);
            phoneField.setReadOnly(false);
            passportField.setReadOnly(false);
            button.setEnabled(false);
            button1.setEnabled(true);
        });

        button1.addClickListener(buttonClickEvent -> {
            nameField.setReadOnly(true);
            phoneField.setReadOnly(true);
            passportField.setReadOnly(true);
            button.setEnabled(true);
            button1.setEnabled(false);





            client.setPhone(phoneField.getValue());
            client.setPassportID(passportField.getValue());
            client.setName(nameField.getValue());

            clientService.update(client);

        });
    }
}
