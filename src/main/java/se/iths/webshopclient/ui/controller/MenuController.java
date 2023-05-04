package se.iths.webshopclient.ui.controller;

import se.iths.webshopclient.business.model.Product;
import se.iths.webshopclient.business.service.WebClientService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    static Scanner scanner;
    static WebClientService webClientService;

    public MenuController(Scanner scanner, WebClientService webClientService) {
        this.scanner = scanner;
        this.webClientService = webClientService;
    }

    public static void mainMenu() {
        while (true) {
            String choice = showMainMenu();
            switch (choice) {
                case "1" -> viewMenu();
                case "2" -> addProduct();
                case "3" -> updateMenu();
                case "4" -> deleteProduct();
                case "!" -> System.out.println("Invalid input");
                case "e", "E" -> exit();
            }
        }
    }

    private static String showMainMenu() {
        System.out.println("""
                [Menu]

                1. View Products
                2. Add Product
                3. Update Product
                4. Delete Product
                e. Exit""");
        String choice = scanner.nextLine();
        if (choice.trim().isEmpty()) {
            return "!";
        }
        return choice;
    }

    private static void viewMenu() {
        boolean loop = true;
        while(loop) {
            String choice = showViewMenu();
            switch (choice) {
                case "1" -> viewAllProducts();
                case "2" -> findProductByID();
                case "3" -> searchProductByName();
                case "4" -> searchProductByCategory();
                case "!" -> System.out.println("Invalid input");
                case "e", "E", "" -> loop = false;
            }
        }
    }

    private static String showViewMenu() {
        System.out.println("""
                [View Products]

                1. View All Products
                2. Find Product with ID
                3. Search by Name
                4. Search by Category
                e. Back""");
        String choice = scanner.nextLine();
        if (choice.trim().isEmpty()) {
            choice = "!";
        }
        return choice;
    }

    private static void viewAllProducts() {
        List<Product> result = webClientService.findAll();
        System.out.println("All products:\n");
        result.forEach(p -> System.out.println("ID: " + p.getId() + " " + p.getName() + " " + p.getCategory() + " " + p.getPrice() + " SEK"));
        System.out.print("Press enter to return");
        scanner.nextLine();
    }

    private static void findProductByID() {
        System.out.println("[Search Product]\nEnter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Product product = webClientService.findByID(id);
        if (product == null) {
            System.out.println("Could not find product");
        } else {
            System.out.println("ID: " + product.getId() + " " + product.getName() + " " + product.getCategory() + " " + product.getPrice() + " SEK");
        }
        System.out.print("Press enter to return");
        scanner.nextLine();
    }

    private static void searchProductByName() {
        System.out.println("[Search Product]\nEnter product name: ");
        String name = "";
        boolean loop = true;
        while (loop) {
            name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Invalid input");
            } else {
                loop = false;
            }
        }

        List<Product> result = webClientService.findByName(name);

        if (result.isEmpty()) {
            System.out.println("No matching products found\n");
        } else {
            System.out.println("Search result:\n");
            result.forEach(p -> System.out.println("ID: " + p.getId() + " " + p.getName() + " " + p.getCategory() + " " + p.getPrice() + " SEK"));
        }
        System.out.print("Press enter to return");
        scanner.nextLine();

    }

    private static void searchProductByCategory() {
        System.out.println("[Search Product]\nEnter product category: ");
        String category = "";
        boolean loop = true;
        while (loop) {
            category = scanner.nextLine().trim();
            if (category.equals("")) {
                System.out.println("Invalid input");
            } else {
                loop = false;
            }
        }

        List<Product> result = webClientService.findByCategory(category);

        if (result.isEmpty()) {
            System.out.println("No matching category found\n");
        } else {
            System.out.println("Search result:\n");
            result.forEach(p -> System.out.println("ID: " + p.getId() + " " + p.getName() + " " + p.getCategory() + " " + p.getPrice() + " SEK"));
        }
        System.out.print("Press enter to return");
        scanner.nextLine();
    }

    private static void addProduct() {
        String name = "";
        String category = "";
        Double price = null;
        String description = "";
        boolean loop = true;
        while (loop) {
            System.out.println("Name: ");
            name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Invalid input");
            } else {
                loop = false;
            }
        }
        loop = true;
        while (loop) {
            System.out.println("Category: ");
            category = scanner.nextLine().trim();
            if (category.equals("")) {
                System.out.println("Invalid input");
            }  else {
                loop = false;
            }
        }
        loop = true;
        while (loop) {
            System.out.println("Price: ");
            price = scanner.nextDouble();
            scanner.nextLine();
            if (price.equals(null)) {
                System.out.println("Invalid input");
            } else {
                loop = false;
            }
        }
        loop = true;
        while (loop) {
            System.out.println("Description: ");
            description = scanner.nextLine().trim();
            if (description.equals("")) {
                System.out.println("Invalid input");
            } else {
                loop = false;
            }
        }

        Product product = webClientService.createProduct(name, category, price, description);

        System.out.println("ID: " + product.getId() + " " + product.getName() + " has been added.");
        System.out.print("Press enter to return");
        scanner.nextLine();
    }

    private static void updateMenu() {
        boolean loop = true;
        while (loop) {
            String choice = showUpdateMenu();
            switch (choice) {
                case "1" -> changeName();
                case "2" -> changeCategory();
                case "3" -> changePrice();
                case "4" -> changeDescription();
                case "!" -> System.out.println("Invalid input");
                case "e", "E" -> loop = false;
            }
        }
    }

    private static String showUpdateMenu() {
        System.out.println("""
                [Update Product]

                1. Update Name
                2. Update Category
                3. Update Price
                4. Update Description
                e. Back""");
        String choice = scanner.nextLine();
        if (choice.trim().isEmpty()) {
            choice = "!";
        }
        return choice;
    }

    private static void changeName() {
        System.out.println("Enter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        boolean loop = true;
        while (loop) {
            System.out.println("Change name: ");
            String name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Invalid input");
            } else {
                Product product = webClientService.updateName(id, name);

                if (product == null) {
                    System.out.println("Could not find product");
                } else {
                    System.out.println("ID: " + product.getId() + " " + product.getName() + " has been updated.");

                }
                System.out.print("Press enter to return");
                scanner.nextLine();
                loop = false;
            }
        }
    }

    private static void changeCategory() {
        System.out.println("Enter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        boolean loop = true;
        while (loop) {
            System.out.println("Change category: ");
            String category = scanner.nextLine().trim();
            if (category.equals("")) {
                System.out.println("Invalid input");
            } else {
                Product product = webClientService.updateCategory(id, category);

                if (product == null) {
                    System.out.println("Could not find product");
                } else {
                    System.out.println("ID: " + product.getId() + " " + product.getName() + " has been updated.");
                    System.out.println("New category: " + product.getCategory());
                }
                System.out.print("Press enter to return");
                scanner.nextLine();
                loop = false;
            }
        }
    }

    private static void changePrice() {

        System.out.println("Enter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        boolean loop = true;
        while (loop) {
            try {
                System.out.println("Change price: ");
                Double price = scanner.nextDouble();
                scanner.nextLine();

                Product product = webClientService.updatePrice(id, price);

                if (product == null) {
                    System.out.println("Could not find product");
                } else {
                    System.out.println("ID: " + product.getId() + " " + product.getName() + " has been updated.");
                    System.out.println("New price: " + product.getPrice());
                }
                System.out.print("Press enter to return");
                scanner.nextLine();
                loop = false;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                scanner.nextLine();
            }
        }
    }

    private static void changeDescription() {
        System.out.println("Enter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        boolean loop = true;
        while (loop) {
            System.out.println("Change description: ");
            String description = scanner.nextLine().trim();
            if (description.equals("")) {
                System.out.println("Invalid input");
            } else {
                Product product = webClientService.updateDescription(id, description);

                if (product == null) {
                    System.out.println("Could not find product");
                } else {
                    System.out.println("ID: " + product.getId() + " " + product.getName() + " has been updated.");
                    System.out.println("New description: " + product.getDescription());
                }
                System.out.print("Press enter to return");
                scanner.nextLine();
                loop = false;
            }
        }
    }

    private static void deleteProduct() {
        System.out.println("[Delete Product]\nEnter product ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        List<Product> products = webClientService.deleteProduct(id);
        if (products.isEmpty()) {
            System.out.println("Could not find product");
        }  else {
            System.out.println("Product " + id + " has been removed.\n");
        }
        System.out.print("Press enter to return");
        scanner.nextLine();
    }

    private static void exit() {
        System.exit(0);
    }
}
