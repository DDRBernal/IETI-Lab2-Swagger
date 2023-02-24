package org.adaschool.api.service.product.productpesistance;


import org.adaschool.api.repository.product.Product;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProductPersistence {

    private static HashMap<String,Product> products = new HashMap<String,Product>();

    public Product save(Product p) {
        String id = p.getId();
        return products.put(id, p);
    }
    public Product getProductById(String id){
        return products.get(id);
    }

    public List<Product> getAllProducts(){
        return (List<Product>) products.values();
    }

    public void deleteProductById(String id){
        products.remove(id);
    }

    public Product updateProduct(Product p, String id){
        products.remove(id);
        products.put(id,p);
        return p;
    }
}
