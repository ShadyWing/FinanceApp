package edu.hdu.pjjfinalapp.frag;

import java.util.List;

import edu.hdu.pjjfinalapp.db.DBManager;
import edu.hdu.pjjfinalapp.db.TypeItem;

// 收入页特化fragment
public class IncomeFragment extends BaseAddAccountFragment {

    @Override
    public void loadDataToGV() {
        super.loadDataToGV();

        List<TypeItem> inlist = DBManager.getTypeList(1);
        typeList.addAll(inlist);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void saveAccountToDB() {
        accountItem.setKind(1);
        DBManager.insertItemToAccounttb(accountItem);
    }
}