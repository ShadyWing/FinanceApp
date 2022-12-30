package edu.hdu.pjjfinalapp.frag;

import java.util.List;

import edu.hdu.pjjfinalapp.db.DBManager;
import edu.hdu.pjjfinalapp.db.TypeItem;

// 支出页特化fragment
public class OutcomeFragment extends BaseAddAccountFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();

        List<TypeItem> outlist = DBManager.getTypeList(0);
        typeList.addAll(outlist);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void saveAccountToDB() {
        accountItem.setKind(0);
        DBManager.insertItemToAccounttb(accountItem);
    }
}
