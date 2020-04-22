package top.cyixlq.addresspickerdialoglibrary.adapter;

import android.view.View;

import top.cyixlq.addresspickerdialoglibrary.R;
import top.cyixlq.addresspickerdialoglibrary.base.BaseAddressAdapter;
import top.cyixlq.addresspickerdialoglibrary.base.BaseViewHolder;
import top.cyixlq.addresspickerdialoglibrary.bean.AddressItem;

public class SimpleAddressAdapter extends BaseAddressAdapter<AddressItem> {

    public SimpleAddressAdapter() {
        super(R.layout.user_item_address_bottom_sheet_dialog);
    }

    @Override
    public void convert(BaseViewHolder holder, AddressItem item) {
        holder.setText(R.id.user_tv_address_dialog, item.getAddress());
        if(item.isChecked()) {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff1F83FF)
                    .setVisibility(R.id.user_iv_address_dialog, View.VISIBLE);
        } else {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff000000)
                    .setVisibility(R.id.user_iv_address_dialog, View.GONE);
        }
    }

    @Override
    public Object getAddressId(AddressItem item) {
        return item.getId();
    }

    @Override
    public String getAddressName(AddressItem item) {
        return item.getAddress();
    }

    @Override
    public void setChecked(AddressItem item, boolean isChecked) {
        item.setChecked(isChecked);
    }

    /*private ArrayList<AddressItem> list = new ArrayList<>();
    private ItemClickListener listener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item_address_bottom_sheet_dialog, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        AddressItem item = list.get(i);
        if (item.isChecked()) {
            myViewHolder.tvAddress.setText(item.getAddress());
            myViewHolder.tvAddress.setTextColor(Color.parseColor("#1F83FF"));
            myViewHolder.ivChecked.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.tvAddress.setText(item.getAddress());
            myViewHolder.tvAddress.setTextColor(Color.BLACK);
            myViewHolder.ivChecked.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.list == null ? 0 : list.size();
    }

    public void setList(List<AddressItem> list) {
        if (this.list != null && list != null) {
            this.list.clear();
            this.list.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(@NonNull ItemClickListener listener) {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvAddress;
        ImageView ivChecked;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.user_tv_address_dialog);
            ivChecked = itemView.findViewById(R.id.user_iv_address_dialog);
            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(getAdapterPosition());
                    }
                });
            }
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }*/
}
