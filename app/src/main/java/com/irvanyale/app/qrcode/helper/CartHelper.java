package com.irvanyale.app.qrcode.helper;

import com.irvanyale.app.qrcode.model.Item;

import java.util.List;
import java.util.Vector;

/**
 * Created by WINDOWS 10 on 10/05/2017.
 */

public class CartHelper {

    private static List<Item> order;

    public static List<Item> getOrder(){
        if (order == null){
            order = new Vector<>();
        }
        return order;
    }
}
