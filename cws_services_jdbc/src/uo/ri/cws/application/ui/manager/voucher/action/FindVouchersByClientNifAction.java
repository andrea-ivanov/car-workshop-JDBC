package uo.ri.cws.application.ui.manager.voucher.action;

import java.math.BigDecimal;
import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FindVouchersByClientNifAction implements Action {

    private VoucherGenerationService service = Factories.service.forVoucherService();

    @Override
    public void execute() throws Exception {
        String nif = Console.readString("Nif del cliente:");
        List<VoucherDto> vouchers = service.findVouchersByClientNif(nif);

        BigDecimal totalAvailable = BigDecimal.ZERO;
        BigDecimal totalConsumed = BigDecimal.ZERO;

        for (VoucherDto dto : vouchers) {
            Printer.printVoucher(dto);
            totalAvailable = totalAvailable.add(dto.availableAmount);
            totalConsumed = totalConsumed.add(dto.accumulatedAmount);
        }

        BigDecimal totalAmount = totalAvailable.add(totalConsumed);
        Console.printf("\tTotal Bonos: %d \tImporte total: %.2f € \tCosumido: %.2f € \tDisponible: %.2f €",
            vouchers.size(), totalAmount, totalConsumed, totalAvailable);
    }

}
