package com.flashcash.services.form;



public class PayCardForm {


    private Double amount;

    public PayCardForm() {}

    public PayCardForm(Double amount) {
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}