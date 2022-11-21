import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File textfile = new File("basket.txt");
        Scanner scanner = new Scanner(System.in);
        Basket shoppingCart = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});
        try{
            shoppingCart = Basket.loadFromTxtFile(textfile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Список возможных товаров для покупки: ");
        for (int i = 0; i < shoppingCart.getProducts().length; i++) {
            System.out.println(shoppingCart.getProducts()[i] + " " + shoppingCart.getPrices()[i] + " руб/шт");
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите `end`");
            String input = scanner.nextLine();
            if ("end".equals(input)) {
                break;
            }
            String[] parts = input.split(" ");
            shoppingCart.addToCart(Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]));
        }
        shoppingCart.printCart();
        shoppingCart.saveTxt(textfile);


        System.out.println("Программа завершена!");
    }
}
