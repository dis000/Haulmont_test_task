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

    private final Button changeButton = new Button("Изменить");
    private final Button saveChangesButton = new Button("Сохранить");
    private final Button deleteCreditOfferButton = new Button("Удалить кредит(для теста)");
    private final Button deleteClientButton = new Button("Удалить аккаунт");



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

        HorizontalLayout horizontalLayout = new HorizontalLayout(clientSelect, deleteClientButton);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout(nameField, phoneField, changeButton);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout(passportField, bankField, saveChangesButton);

        add(horizontalLayout,horizontalLayout1,horizontalLayout2,creditOfferSelect, indebtedness, deleteCreditOfferButton);

    }



    private void configureSelects() {
        creditOfferSelect.setVisible(false);
        creditOfferSelect.setLabel("Просмотр задолжности");

        clientSelect.setLabel("Тестовое поле");

        clientSelect.setHelperText("для удаления акка не должно быть кредитов");

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
            changeButton.setEnabled(true);
            deleteCreditOfferButton.setVisible(false);

            deleteClientButton.setEnabled(creditOffer.isEmpty());


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


            deleteCreditOfferButton.setVisible(true);
        });
    }
    private void configureFields() {
        indebtedness.setWidth(6F, Unit.CM);
        creditOfferSelect.setWidth(6F, Unit.CM);
        clientSelect.setWidth(6F, Unit.CM);
        deleteCreditOfferButton.setWidth(6F, Unit.CM);

        bankField.setReadOnly(true);
        indebtedness.setReadOnly(true);
        nameField.setReadOnly(true);
        phoneField.setReadOnly(true);
        passportField.setReadOnly(true);
        saveChangesButton.setEnabled(false);
        changeButton.setEnabled(false);
        deleteCreditOfferButton.setVisible(false);
        deleteClientButton.setEnabled(false);




    }

    private void configureButtons() {
        changeButton.addClickListener(buttonClickEvent -> {

            nameField.setReadOnly(false);
            phoneField.setReadOnly(false);
            passportField.setReadOnly(false);
            changeButton.setEnabled(false);
            saveChangesButton.setEnabled(true);
        });

        saveChangesButton.addClickListener(buttonClickEvent -> {
            nameField.setReadOnly(true);
            phoneField.setReadOnly(true);
            passportField.setReadOnly(true);
            changeButton.setEnabled(true);
            saveChangesButton.setEnabled(false);


            client.setPhone(phoneField.getValue());
            client.setPassportID(passportField.getValue());
            client.setName(nameField.getValue());

            clientService.update(client);

        });

        deleteCreditOfferButton.addClickListener(buttonClickEvent -> {
            creditOfferService.delete(creditOfferSelect.getValue());
        });

        deleteClientButton.addClickListener(buttonClickEvent -> {
            clientService.delete(clientSelect.getValue());
        });
    }
}
