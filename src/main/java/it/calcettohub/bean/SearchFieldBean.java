package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

public class SearchFieldBean {
    private String address;
    private String city;

    public SearchFieldBean() {
        // empty
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!ValidationUtils.isNotEmpty(address)) {
            this.address = null;
            return;
        }

        if (ValidationUtils.isValidAddress(address)) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Indirizzo non valido. Inserire via e numero civico.");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = (ValidationUtils.isNotEmpty(city)) ? city : null;
    }

    public void validate() {
        if (city == null && address == null) {
            throw new IllegalArgumentException("Inserire almeno città/paese o indirizzo.");
        }
    }
}
