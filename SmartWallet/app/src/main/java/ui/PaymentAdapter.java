package ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upt.cti.smartwallet.R;

import java.util.List;

import model.Payment;
import model.PaymentType;


public class PaymentAdapter extends ArrayAdapter<Payment> {

    private Context context;
    private List<Payment> payments;
    private int layoutResID;

    public  PaymentAdapter(Context context, int layoutResID, List<Payment> payments)
    {
        super(context, layoutResID, payments);
        this.context = context;
        this.payments = payments;
        this.layoutResID = layoutResID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PaymentHolder itemHolderInfo;
        View view = convertView;

        if(view == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            itemHolderInfo = new PaymentHolder();

            view = inflater.inflate(layoutResID, parent, false);
            itemHolderInfo.tIndex = (TextView) view.findViewById(R.id.tIndex);
            itemHolderInfo.tName = (TextView) view.findViewById(R.id.tName);
            itemHolderInfo.lHeader = (RelativeLayout) view.findViewById(R.id.lHeader);
            itemHolderInfo.tDate = (TextView) view.findViewById(R.id.tDate);
            itemHolderInfo.tCost = (TextView) view.findViewById(R.id.tCost);
            itemHolderInfo.tType = (TextView) view.findViewById(R.id.tType);

            view.setTag(itemHolderInfo);
        }
        else
        {
            itemHolderInfo = (PaymentHolder) view.getTag();
        }

        final  Payment pItem = payments.get(position);

        itemHolderInfo.tIndex.setText(String.valueOf(position+1));
        itemHolderInfo.tName.setText(pItem.getName());
        itemHolderInfo.lHeader.setBackgroundColor(PaymentType.getColorFromPaymentType(pItem.getType()));
        itemHolderInfo.tCost.setText(pItem.getCost() + "LEI");
        itemHolderInfo.tType.setText(pItem.getType());
//        itemHolderInfo.tDate.setText("Date: " + pItem.timestamp.substring(0,10));
//        itemHolderInfo.tTime.setText("Time: " + pItem.timestamp.substring(11));

        return  view;
    }

    public class PaymentHolder{

        public TextView tIndex, tName, tDate, tTime, tCost, tType;
        RelativeLayout lHeader;

    }
}

