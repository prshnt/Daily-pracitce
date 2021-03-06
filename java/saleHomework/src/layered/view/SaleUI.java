package layered.view;

import call.SalePrompt;
import layered.bl.SaleBL;
import layered.bl.UserBL;
import object.SaleDelegate;
import sharedByDifferentStyle.User;

import java.util.Scanner;

/**
 * Created by zzt on 1/22/16.
 * <p>
 * Usage:
 */
public class SaleUI {

    private final Scanner in;
    private SaleBL saleBL;
    private UserBL userBL;
    private SaleDelegate saleDelegate;

    public SaleUI(Scanner scanner) {
        in = scanner;
        saleBL = SaleBL.getInstance();
        userBL = UserBL.getInstance();
    }

    public void sale() {
        System.out.println("sale started");

        System.out.println("input user account and password for this sale:");
        User user = userBL.getUser(in.next(), in.next());
        saleDelegate = saleBL.getSale(user);
        in.nextLine();

        SalePrompt.addGoodsPrompt(saleDelegate.getSale(), in);
        System.out.println("sum: " + saleDelegate.getSum());
        System.out.println("after strategy: " + saleDelegate.getActual());
    }

    public void charge() {
        System.out.println("now pay how much?");
        int pay = in.nextInt();
        while (!saleDelegate.validPay(pay)) {
            System.out.println("you should pay more");
            System.out.println("now pay how much?");
            pay = in.nextInt();
        }
        System.out.println("now give you change: " + saleDelegate.change(pay));
    }

    public void bill() {
        saleDelegate.print();
    }
}
