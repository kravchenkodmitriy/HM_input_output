import java.io.*;

public class Basket {
    private int[] prices;
    private String[] products;
    private int[] productsBasket;

    public static void main(String[] args) {
        File textfile = new File("basket.txt");
    }

    public Basket(String[] products, int[] prices) {
        this.prices = prices;
        this.products = products;
        this.productsBasket = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        productsBasket[productNum] += amount;
    }

    public void printCart() {
        int sumProducts = 0;
        int sumCount = 0;
        for (int i = 0; i < productsBasket.length; i++) {
            int priceProduct = (int) (productsBasket[i] * prices[i]);
            if (productsBasket[i] != 0) {
                System.out.println(products[i] + ": " + productsBasket[i] + "шт "
                        + prices[i] + " шт/руб; " + priceProduct + " руб." + " " + "в итоге");
            }
            sumProducts += productsBasket[i] * prices[i];
            sumCount += productsBasket[i];
        }
        System.out.println("Количество продуктов в корзине " + sumCount + " штук");
        System.out.println("Сумма всех покупок " + sumProducts + " руб");
    }

    public void saveTxt(File textFile) throws IOException {
        try (
                PrintWriter out = new PrintWriter(textFile);
        ) {
            for (String product : getProducts())
                out.print(product + " ");
            out.print("\n");
            for (int price : getPrices())
                out.print(price + " ");
            out.print("\n");
            for (long e : getProductsBasket()) {
                out.print(e + " ");
            }
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException, NullPointerException {
        try (
                BufferedReader in = new BufferedReader(new FileReader(textFile));
        ) {
            String[] products = in.readLine().split(" ");
            String[] pricesLine = in.readLine().split(" ");
            int[] prices = new int[pricesLine.length];


            for (int i = 0; i < prices.length; i++) {
                prices[i] = Integer.parseInt(pricesLine[i]);
            }

            Basket basket = new Basket(products, prices);

            String[] amountLine = in.readLine().split(" ");

            for (int i = 0; i < amountLine.length; i++) {
                basket.productsBasket[i] = Integer.parseInt(amountLine[i]);
            }
            return basket;
        }

    }

    public int[] getPrices() {
        return prices;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getProductsBasket() {
        return productsBasket;
    }
}
