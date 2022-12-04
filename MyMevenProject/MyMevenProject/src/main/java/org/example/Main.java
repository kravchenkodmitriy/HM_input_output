package org.example;

import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, org.json.simple.parser.ParseException {
        ClientLog clientLog = new ClientLog();
        File jsonFile = new File("basket.json");
        File xmlFile = new File("shop.xml");
        Scanner scanner = new Scanner(System.in);
        Basket shoppingCart = new Basket(new String[]{"Хлеб", "Яблоки", "Молоко"}, new int[]{100, 200, 300});

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(String.valueOf(xmlFile)));

        //Загрузка
        WorkWithFile load = new WorkWithFile(document.getElementsByTagName("load").item(0));
        if (load.enabled == true){
            if (load.format.equals("json")){
                readJson(new File(load.fileName));
            } else {
                Basket.loadFromTxtFile(new File(load.fileName));
            }
        } else {
            System.out.println("Не загружаем");
        }

        //Сохранение
        WorkWithFile save = new WorkWithFile(document.getElementsByTagName("save").item(0));
        if (save.enabled == true) {
            if (load.format.equals("json")){
                shoppingCart.saveJson(new File(save.fileName));
            } else if (save.format.equals("txt")){
                shoppingCart.saveTxt(new File(save.fileName));
            }
        }else {
            System.out.println("Не сохранили");
        }


        try{
            shoppingCart = Basket.loadFromJson(jsonFile);
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
        shoppingCart.saveJson(jsonFile);
        clientLog.exportAsCSV(new File("log.csv"));

        System.out.println("Программа завершена!");
    }

    public static void readJson(File fileJson) throws org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonParser.parse(new FileReader(fileJson));
        } catch (IOException e){
            System.out.println("Файл не найден");
        }
        System.out.println(obj);
    }
}
