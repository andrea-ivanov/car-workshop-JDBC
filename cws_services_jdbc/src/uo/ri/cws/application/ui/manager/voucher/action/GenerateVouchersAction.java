package uo.ri.cws.application.ui.manager.voucher.action;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class GenerateVouchersAction implements Action {

    private VoucherGenerationService service = Factories.service.forVoucherService();

    @Override
    public void execute() throws Exception {
        int[] generated = service.generateVouchers();

        Console.printf("Bonos generados: %d de 20€, %d de 25€, %d de 30€\n",
                generated[0], generated[1], generated[2]);
    }

}
