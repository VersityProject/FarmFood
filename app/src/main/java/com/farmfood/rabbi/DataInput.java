package com.farmfood.rabbi;

public class DataInput  {
    private String productId;
   private String username;
   private String  userdescription;
   private String price;
    private String mimageUrl;

    public  DataInput()
    {

    }

    public DataInput(String productId, String username, String userdescription,String pricetext, String imageurl) {
        this.productId = productId;
        this.username = username;
        this.userdescription = userdescription;
        this.price=pricetext;
        this.mimageUrl=imageurl;
    }

    public String getProductId() {
        return productId;
    }

    public String getUsername() {
        return username;
    }

    public String getUserdescription() {
        return userdescription;
    }

    public String getPrice() {
        return price;
    }

    public String getMimageUrl() {
        return mimageUrl;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserdescription(String userdescription) {
        this.userdescription = userdescription;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setMimageUrl(String mimageUrl) {
        this.mimageUrl = mimageUrl;
    }
}
