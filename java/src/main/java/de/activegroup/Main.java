package de.activegroup;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        var product1 = new ShowerProduct.Soap(7);
        var product2 = new ShowerProduct.Shampoo(HairType.OILY);
        var product3 = new ShowerProduct.ShowerGel(product1, product2);
        var product4 = new ShowerProduct.Soap(9);
        var product5 = new ShowerProduct.ShowerGel(product3, product4);

    }
}