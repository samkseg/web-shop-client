package se.iths.webshopclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import se.iths.webshopclient.ui.controller.MenuController;
import se.iths.webshopclient.business.service.WebClientService;

import java.util.Scanner;

@Configuration
public class WebShopClientRunner implements CommandLineRunner {

    static Scanner scanner = new Scanner(System.in);
    WebClientService webClientService = new WebClientService();
    MenuController menuController = new MenuController(scanner, webClientService);

    @Override
    public void run(String... args) throws Exception {
        menuController.mainMenu();
    }
}
