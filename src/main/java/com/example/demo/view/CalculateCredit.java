package com.example.demo.view;

import com.example.demo.entities.*;
import com.example.demo.service.interfaces.*;

import com.example.demo.view.menu.Menu;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Route(value = "calculate", layout = Menu.class)
public class CalculateCredit extends VerticalLayout {
        TextField passportField = new TextField("Номер паспорта");
        BigDecimalField creditAmount = new BigDecimalField("Сумма кредита");
        NumberField creditTime = new NumberField("Срок кредита в годах");
        BigDecimalField fullLoanAmountField = new BigDecimalField("Сумма возврата");
        BigDecimalField resultPercentField = new BigDecimalField("Сумма процента");
        BigDecimalField paymentPerMonth = new BigDecimalField("Сумма оплаты в меню");
        BigDecimalField paymentPerMonthBody = new BigDecimalField("Тело кредита в месяц");
        BigDecimalField paymentPerMonthPercent = new BigDecimalField("Процент кредита в месяц");

        Select<Credit> creditsComboBox;

        Button buttonEnterCreditOffer = new Button("Оформить");



        @Autowired
        public CalculateCredit(
                IClientService clientService,
                ICreditService creditService,
                ICreditOfferService creditOfferService,
                IPaymentScheduleService paymentScheduleService) {




                buttonEnterCreditOffer.setVisible(false);
                buttonEnterCreditOffer.addClickListener(buttonClickEvent
                        -> createCreditAmount(clientService, creditOfferService, paymentScheduleService));


                HorizontalLayout horizontalLayout1 = new HorizontalLayout(createComboBox(creditService), passportField);

                HorizontalLayout horizontalLayout2 = new HorizontalLayout(creditAmount,creditTime);

                HorizontalLayout resultHorizontalLayout1 = new HorizontalLayout(fullLoanAmountField,resultPercentField);

                HorizontalLayout resultHorizontalLayout2 = new HorizontalLayout(paymentPerMonthBody, paymentPerMonthPercent);


                setSettingsOnFields();

                setWidthFull();

                VerticalLayout verticalLayout = new VerticalLayout();
                verticalLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
                verticalLayout.add(
                        horizontalLayout1,
                        horizontalLayout2,
                        createButtonCalculate(),
                        resultHorizontalLayout1,
                        resultHorizontalLayout2,
                        buttonEnterCreditOffer
                );
                add(verticalLayout);
        }


        private Button createButtonCalculate() {
                Button button = new Button("Вычислить");

                button.addClickListener(buttonClickEvent -> {
                        if (creditAmount.getValue().compareTo(BigDecimal.valueOf(1000)) < 0
                                |
                                creditAmount.getValue().compareTo(creditsComboBox.getValue().getCreditLimit()) > 0) {
                                Notification.show("Введена некорректная сумма");
                                clearResultFields();
                                buttonEnterCreditOffer.setVisible(false);
                                return;
                        }

                        if (passportField.getValue() == null) {
                                Notification.show("Введите номер паспорта");
                                buttonEnterCreditOffer.setVisible(false);
                                return;
                        }

                        resultPercentField.setValue(getPercentOfLoan());

                        fullLoanAmountField.setValue(getFullLoanAmount());

                        paymentPerMonth.setValue(getFullLoanAmount()
                                .divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN));
                        paymentPerMonthBody.setValue(creditAmount.getValue()
                                .divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN));
                        paymentPerMonthPercent.setValue(getPercentOfLoan()
                                .divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN));

                        buttonEnterCreditOffer.setVisible(true);
                });
                return button;
        }


        private BigDecimal getPercentOfLoan () {
                BigDecimal percent = creditsComboBox.getValue().getPercentRate();
                BigDecimal percentOfLoan = percent.divide(BigDecimal.valueOf(100), 5, RoundingMode.HALF_DOWN);
                percentOfLoan = creditAmount.getValue().multiply(percentOfLoan);
                percentOfLoan = percentOfLoan.multiply(BigDecimal.valueOf(creditTime.getValue().intValue())).setScale(3, RoundingMode.HALF_DOWN);
                return percentOfLoan;
        }

        private BigDecimal getFullLoanAmount() {
                return getPercentOfLoan().add(creditAmount.getValue());
        }

        private BigDecimal getCreditTimeInMonth () {
                return BigDecimal.valueOf(12 * creditTime.getValue());
        }


        private void clearResultFields() {
                fullLoanAmountField.clear();
                // итоговый процент от кредита
                resultPercentField.clear();
                // помесячная оплата
                paymentPerMonth.clear();
                // помесячная оплата тело кредита
                paymentPerMonthBody.clear();
                // помесячная оплата проценты кредита
                paymentPerMonthPercent.clear();
        }

        private void setSettingsOnFields() {

                creditsComboBox.setWidth(6F, Unit.CM);
                passportField.setWidth(6F, Unit.CM);
                creditAmount.setWidth(6F, Unit.CM);
                creditTime.setWidth(6F, Unit.CM);
                fullLoanAmountField.setWidth(6F, Unit.CM);
                resultPercentField.setWidth(6F, Unit.CM);
                paymentPerMonth.setWidth(6F, Unit.CM);
                paymentPerMonthBody.setWidth(6F, Unit.CM);
                paymentPerMonthPercent.setWidth(6F, Unit.CM);

                creditAmount.setSuffixComponent(new Icon(VaadinIcon.MONEY));

                creditTime.setHasControls(true);
                creditTime.setValue(1d);
                creditTime.setMin(1);
                creditTime.setMax(5);

                fullLoanAmountField.setReadOnly(true);

                resultPercentField.setReadOnly(true);

                paymentPerMonth.setReadOnly(true);

                paymentPerMonthBody.setReadOnly(true);

                paymentPerMonthPercent.setReadOnly(true);
        }


        private void createCreditAmount(IClientService clientService, ICreditOfferService creditOfferService, IPaymentScheduleService paymentScheduleService) {
                Client client = clientService.getByPassportID(passportField.getValue());
                if (client == null) {
                        return;
                }

                Set<PaymentSchedule> scheduleList = new HashSet<>();

                CreditOffer creditOffer = new CreditOffer(creditsComboBox.getValue(), client, creditAmount.getValue());


                for (int i = 0; i < getCreditTimeInMonth().intValue(); i++) {
                        PaymentSchedule paymentSchedule = new PaymentSchedule(
                                LocalDate.now().plusMonths(i+1),
                                getFullLoanAmount().divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN),
                                creditAmount.getValue().divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN),
                                getPercentOfLoan().divide(getCreditTimeInMonth(), 2, RoundingMode.HALF_DOWN)
                                , creditOffer
                                );

                        scheduleList.add(paymentSchedule);
                }


                creditOfferService.save(creditOffer);
                  paymentScheduleService.saveAll(scheduleList);
        }

        private Component createComboBox(ICreditService creditService) {
                creditsComboBox = new Select<>();
                creditsComboBox.setLabel("Кредит");
                creditsComboBox.setPlaceholder("выбрать кредит");


                try{
                        creditsComboBox.setItems(creditService.getAll());
                }catch (NullPointerException exception) {
                        exception.printStackTrace();
                }
                return creditsComboBox;
        }


}
