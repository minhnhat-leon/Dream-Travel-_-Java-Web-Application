/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.tour;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author NhatBPM;
 */
public class TourSearch implements Serializable{
    private int tourId;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private double price;
    private String place;
    private int quota;
    private String imageLink;
    private int quotaRemaining;
    private int amount;

    public TourSearch(int tourId, String name, Date dateFrom, Date dateTo, double price, String place, int quota, String imageLink) {
        this.tourId = tourId;
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.price = price;
        this.place = place;
        this.quota = quota;
        this.imageLink = imageLink;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(int quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    
}
