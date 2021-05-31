package com.example.mda_project.util;

public class Server {
    public static String localhost = "192.168.0.4";
    public static String linkTypePro = "http://" + localhost + "/server/getTypeOfProduct.php";
    public static String linkNewestPro = "http://" + localhost + "/server/getNewestProduct.php";
    public static String linkItem = "http://" + localhost + "/server/getProduct.php?page=";
    public static String linkOrder = "http://" + localhost + "/server/infocustomer.php";
    public static String linkDetailOrder = "http://" + localhost + "/server/detailOrder.php";
}
