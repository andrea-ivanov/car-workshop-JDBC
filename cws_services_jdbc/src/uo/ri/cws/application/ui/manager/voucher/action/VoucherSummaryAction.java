package uo.ri.cws.application.ui.manager.voucher.action;

import java.util.List;

import uo.ri.conf.Factories;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService;
import uo.ri.cws.application.service.paymentmean.VoucherGenerationService.VoucherSummaryDto;
import uo.ri.cws.application.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class VoucherSummaryAction implements Action {

    private VoucherGenerationService service = Factories.service.forVoucherService();

    @Override
    public void execute() throws Exception {
        List<VoucherSummaryDto> summaries = service.getVoucherSummary();

        Console.println("Resumen de bonos por cliente:");
        for (VoucherSummaryDto dto : summaries) {
            Printer.printVoucherSummary(dto);
        }
    }

}
