package com.example.demo.view.menu;

import com.example.demo.view.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;

@Route("")
public class Menu extends AppLayout {

    Button button1 = new Button("Оформить Кредит");
    Button button2 = new Button("Регистрация");
    Button button3 = new Button("Создать кредит");
    Button button4 = new Button("База данных");
    Button button5 = new Button("Профиль");

    public Menu() {



        button1.setWidthFull();
        button2.setWidthFull();
        button3.setWidthFull();
        button4.setWidthFull();
        button5.setWidthFull();

        button1.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button2.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button3.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button4.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button5.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        button1.addClickListener(c -> UI.getCurrent().navigate(CalculateCredit.class));
        button2.addClickListener(c -> UI.getCurrent().navigate(SignUp.class));
        button3.addClickListener(c -> UI.getCurrent().navigate(CreateCredit.class));
        button4.addClickListener(c -> UI.getCurrent().navigate(Database.class));
        button5.addClickListener(c -> UI.getCurrent().navigate(MyAccount.class));


        addToDrawer(button1, button2, button3, button4, button5);

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());


    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        return new Header(toggle);
    }
}