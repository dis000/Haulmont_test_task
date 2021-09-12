package com.example.demo.view;

import com.example.demo.entities.Client;
import com.example.demo.entities.Credit;
import com.example.demo.service.interfaces.IClientService;
import com.example.demo.service.interfaces.ICreditOfferService;
import com.example.demo.service.interfaces.ICreditService;
import com.example.demo.service.interfaces.IPaymentScheduleService;
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

@Route(value = "calculate", layout = Menu.class)
public class CalculateCredit extends VerticalLayout {

        IClientService clientService;
        ICreditService creditService;
        ICreditOfferService creditOfferService;
        IPaymentScheduleService paymentScheduleService;



        private final TextField passportField = new TextField("Номер паспорта");
        private final BigDecimalField creditAmount = new BigDecimalField("Сумма кредита");
        private final NumberField creditTime = new NumberField("Срок кредита в годах");
        private final BigDecimalField fullLoanAmountField = new BigDecimalField("Сумма возврата");
        private final BigDecimalField resultPercentField = new BigDecimalField("Сумма процента");
        private final BigDecimalField paymentPerMonth = new BigDecimalField("Сумма оплаты в меню");
        private final BigDecimalField paymentPerMonthBody = new BigDecimalField("Тело кредита в месяц");
        private final BigDecimalField paymentPerMonthPercent = new BigDecimalField("Процент кредита в месяц");

        private Select<Credit> creditsComboBox;

        private final Button buttonEnterCreditOffer = new Button("Оформить");




        public CalculateCredit(@Autowired IClientService clientS,
                               @Autowired ICreditService creditS,
                               @Autowired ICreditOfferService creditOfferS,
                               @Autowired IPaymentScheduleService scheduleS) {

                this.clientService = clientS;
                this.creditService = creditS;
                this.creditOfferService = creditOfferS;
                this.paymentScheduleService = scheduleS;


                buttonEnterCreditOffer.setVisible(false);
                buttonEnterCreditOffer.addClickListener(buttonClickEvent
                        -> createCreditAmount());


                HorizontalLayout horizontalLayout1 = new HorizontalLayout(createComboBox(), passportField);

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

                        paymentPerMonth.setValue(divideByMonth(getFullLoanAmount(), creditTime.getValue()));
                        paymentPerMonthBody.setValue(divideByMonth(creditAmount.getValue(), creditTime.getValue()));
                        paymentPerMonthPercent.setValue(divideByMonth(getPercentOfLoan(), creditTime.getValue()));

                        buttonEnterCreditOffer.setVisible(true);
                });
                return button;
        }


        private BigDecimal divideByMonth(BigDecimal value, double creditTime) {
                return value.divide(BigDecimal.valueOf(creditTime*12), 2, RoundingMode.HALF_DOWN);
        }

        //дублирование кода с paymentScheduleService чтобы не нагружать сервер лишний раз

        private BigDecimal getPercentOfLoan() {
                BigDecimal dividedPercent = creditsComboBox.getValue().getPercentRate().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_DOWN);
                BigDecimal percentOfLoan = creditAmount.getValue().multiply(dividedPercent);
                return percentOfLoan.multiply(BigDecimal.valueOf(creditTime.getValue()));
        }


        private BigDecimal getFullLoanAmount() {
                return getPercentOfLoan().add(creditAmount.getValue());
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


        private void createCreditAmount() {
                Client client = clientService.getByPassportID(passportField.getValue());
                if (client == null) {
                        return;
                }

                paymentScheduleService.save(
                        creditTime.getValue().intValue(),
                        creditAmount.getValue(),
                        passportField.getValue(),
                        creditsComboBox.getValue());

        }

        private Component createComboBox() {
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
