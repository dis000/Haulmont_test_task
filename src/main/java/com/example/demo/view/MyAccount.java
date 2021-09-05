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

    Client client = new Client();
    TextField nameField = new TextField("ФИО");
    TextField phoneField = new TextField("Телефон");
    TextField passportField = new TextField("Номер паспорта");
    TextField bankField = new TextField("Ваш банк");
    BigDecimalField textField5 = new BigDecimalField();
    TextField textField6 = new TextField();
    TextField textField7 = new TextField();
    TextField textField8 = new TextField();
    Select<Client> clientSelect = new Select<>();
    Select<CreditOffer> creditOfferSelect = new Select<>();

    Button button = new Button("Изменить");
    Button button1 = new Button("Сохранить");
    @Autowired
    public MyAccount(
            IBankService bankService,
            IClientService clientService,
            ICreditService creditService,
            ICreditOfferService creditOfferService,
            IPaymentScheduleService paymentScheduleService) {



        configureSelects(clientService,creditOfferService,paymentScheduleService);

        configureFields();

        configureButtons(clientService);


        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        HorizontalLayout horizontalLayout = new HorizontalLayout(nameField, phoneField, button);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(passportField, bankField, button1);

        add(clientSelect,horizontalLayout,horizontalLayout1,creditOfferSelect,textField5);

    }



    private void configureSelects(IClientService clientService, ICreditOfferService creditOfferService, IPaymentScheduleService paymentScheduleService) {
        creditOfferSelect.setVisible(false);

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
            button.setVisible(true);
        });

        creditOfferSelect.addValueChangeListener(select -> {
            List<PaymentSchedule> scheduleList = paymentScheduleService.getByPassportID(client.getPassportID());

            List<PaymentSchedule> resScheduleList = scheduleList.stream().filter(paymentSchedule -> paymentSchedule.getPaymentDate().isBefore(LocalDate.now().plusMonths(4))).collect(Collectors.toList());
            BigDecimal bigDecimal = new BigDecimal(0);
            for (PaymentSchedule schedule:
                 resScheduleList) {
                bigDecimal = bigDecimal.add(schedule.getAmountOfPayment());
            }

            textField5.setValue(bigDecimal);
            textField5.setLabel("нужно оплатить до " + LocalDate.now().plusMonths(4));
        });
    }
    private void configureFields() {
        textField5.setWidth(6F, Unit.CM);
        creditOfferSelect.setWidth(6F, Unit.CM);
        clientSelect.setWidth(6F, Unit.CM);

        nameField.setReadOnly(true);
        phoneField.setReadOnly(true);
        passportField.setReadOnly(true);
        button1.setVisible(false);
        button.setVisible(false);



    }

    private void configureButtons(IClientService clientService) {
        button.addClickListener(buttonClickEvent -> {
            nameField.setReadOnly(false);
            phoneField.setReadOnly(false);
            passportField.setReadOnly(false);
            button.setVisible(false);
            button1.setVisible(true);
        });

        button1.addClickListener(buttonClickEvent -> {
            nameField.setReadOnly(true);
            phoneField.setReadOnly(true);
            passportField.setReadOnly(true);
            button.setVisible(true);
            button1.setVisible(false);

            client.setPhone(phoneField.getValue());
            client.setPassportID(passportField.getValue());
            client.setName(nameField.getValue());

            clientService.update(client);

        });
    }
}
