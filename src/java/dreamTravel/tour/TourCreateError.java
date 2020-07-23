/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.tour;

import java.io.Serializable;

/**
 *
 * @author NhatBPM;
 */
public class TourCreateError implements Serializable{
    private String name;
    private String nameError;
    private String place;
    private String placeError;
    private String dateFrom;
    private String dateFromError;
    private String dateTo;
    private String dateToError;
    private String price;
    private String priceError;
    private String qouta;
    private String qoutaError;
    private String imageError;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameError() {
        return nameError;
    }

    public void setNameError(String nameError) {
        this.nameError = nameError;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceError() {
        return placeError;
    }

    public void setPlaceError(String placeError) {
        this.placeError = placeError;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateFromError() {
        return dateFromError;
    }

    public void setDateFromError(String dateFromError) {
        this.dateFromError = dateFromError;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getDateToError() {
        return dateToError;
    }

    public void setDateToError(String dateToError) {
        this.dateToError = dateToError;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceError() {
        return priceError;
    }

    public void setPriceError(String priceError) {
        this.priceError = priceError;
    }

    public String getQouta() {
        return qouta;
    }

    public void setQouta(String qouta) {
        this.qouta = qouta;
    }

    public String getQoutaError() {
        return qoutaError;
    }

    public void setQoutaError(String qoutaError) {
        this.qoutaError = qoutaError;
    }

    public String getImageError() {
        return imageError;
    }

    public void setImageError(String imageError) {
        this.imageError = imageError;
    }
    
    
}
