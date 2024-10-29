package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Start extends TelegramLongPollingBot {
    public Start() {
        super("7340475513:AAErxw3bBdIZ7yJlDCW87fodZ-A1VhDRIfg");
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {
            var message = new SendMessage();
            message.setChatId(chatId);

            switch (text) {
                case "/start" -> sendMessage(chatId, "Hello!");
                case "btc" -> {
                    sendPicture(chatId, "Bitcoin.png");
                    sendPrice(chatId, "BTC");
                }
                case "eth" -> {
                    sendPicture(chatId, "Ethereum.png");
                    sendPrice(chatId, "ETH");
                }
                case "doge" -> {
                    sendPicture(chatId, "Dogecoin.png");
                    sendPrice(chatId, "DOGE");
                }
                case "/all" -> {
                    sendPrice(chatId, "BTC");
                    sendPrice(chatId, "ETH");
                    sendPrice(chatId, "DOGE");
                }
                default -> sendMessage(chatId, "Unknown command!");
            }

//            execute(message);
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }

    void sendPrice(long chatId, String name) throws Exception {
        var price = CryptoPrice.spotPrice(name);
        sendMessage(chatId, name + "price: " + price.getAmount().doubleValue());
    }

    void sendPicture(long chatId, String name) throws Exception {
        var photo = getClass().getClassLoader().getResourceAsStream(name);
        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setPhoto(new InputFile(photo, name));
        execute(message);
    }


    void sendMessage(long chatId, String text) throws Exception {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }


    @Override
    public String getBotUsername() {
        return "startbot1234_bot";
    }
}


