package com.lorenzo.summer.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name="date")
    private Date date;

    @Column(name="vendor")
    private String vendor;

    @Column(name="price")
    private Double price;

    @Column(name="pay_method")
    private int pay_method;

    @Column(name="scan")
    private byte[] scan;

    public Expense() {
    }

    public Expense(Date date, String vendor, Double price, int pay_method, byte[] scan) {
        this.date = date;
        this.vendor = vendor;
        this.price = price;
        this.pay_method = pay_method;
        this.scan = scan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getPay_method() {
        return pay_method;
    }

    public void setPay_method(int pay_method) {
        this.pay_method = pay_method;
    }

    public byte[] getScan() {
        return scan;
    }

    public void setScan(byte[] scan) {
        this.scan = scan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return id == expense.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", date=" + date +
                ", vendor='" + vendor + '\'' +
                ", price=" + price +
                ", pay_method=" + pay_method +
                ", scan=" + Arrays.toString(scan) +
                '}';
    }
}
