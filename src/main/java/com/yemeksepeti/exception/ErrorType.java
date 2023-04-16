package com.yemeksepeti.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorType {

    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4100, "Kullancı adı veya şifre hatalı", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4200, "Şifreler aynı değil", HttpStatus.BAD_REQUEST),
    INVALID_FUND(4300, "Geçerli bir bakiye miktarı giriniz.", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(4400, "Böyle bir customer bulunamadı", HttpStatus.NOT_FOUND),
    ACTIVATE_CODE_ERROR(4500, "Aktivasyon kod hatası", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_ACTIVE(4600,"Account aktif durumda değil",HttpStatus.BAD_REQUEST),
    INVALID_CARD(4700, "Geçersiz kart bilgisi girildi", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(4800,"Böyle bir product bulunamadı",HttpStatus.NOT_FOUND),
    DIFFERENT_RESTAURANT(4900,"Tek seferde, bir restaurant'tan sipariş yapabilirsiniz",HttpStatus.BAD_REQUEST),
    RESTAURANT_NOT_FOUND(5000,"Restaurant bulunamadı.",HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(5100,"Böyle bir order bulunamadı.",HttpStatus.NOT_FOUND),
    RESTAURANT_NO_ORDER(5200,"Bu restorandan bir order alınmadı",HttpStatus.NOT_FOUND),
    DUPLICATE_PRODUCT(5300,"Bu ürün, bu restorant için zaten tanımlanmıştır.",HttpStatus.BAD_REQUEST),
    POINT_ERROR(5400,"Geçerli point aralığı 0-5 arasındadır.",HttpStatus.BAD_REQUEST),
    DUPLICATE_ADRESS(5500,"Mevcut address başka bir restoran içni kullanılmıştır.",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    HttpStatus httpStatus;
}
