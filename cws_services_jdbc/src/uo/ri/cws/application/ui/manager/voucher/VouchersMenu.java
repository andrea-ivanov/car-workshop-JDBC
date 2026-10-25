package uo.ri.cws.application.ui.manager.voucher;

import uo.ri.cws.application.ui.manager.voucher.action.FindVouchersByClientNifAction;
import uo.ri.cws.application.ui.manager.voucher.action.GenerateVouchersAction;
import uo.ri.cws.application.ui.manager.voucher.action.VoucherSummaryAction;
import uo.ri.util.menu.BaseMenu;

public class VouchersMenu extends BaseMenu {

    public VouchersMenu() {
        menuOptions = new Object[][] {
                { "Manager > Voucher manager", null },
                { "Generate vouchers", GenerateVouchersAction.class },
                { "List vouchers by client nif", FindVouchersByClientNifAction.class },
                { "Get voucher summary", VoucherSummaryAction.class } };
    }
}
