package com.example.phonestore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.MyMethod.InFoStatic;
import com.example.phonestore.OBJ.MyLocation;
import com.example.phonestore.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LHolder>
{
  List<MyLocation> DsLocation;
  Context context;
  public static int index = 0;
  public LocationClick click;

  public interface LocationClick{
      public void clickEdit();
  }

    public LocationAdapter(List<MyLocation> dsLocation, Context context, LocationClick click) {
        DsLocation = dsLocation;
        this.context = context;
        this.click = click;
    }

    @SuppressLint("NotifyDataSetChanged")
 public void setData(List<MyLocation> Ds)
 {
     DsLocation = Ds;
     notifyDataSetChanged();
 }
    @NonNull
    @Override
    public LHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent,false);
        return new LHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LHolder holder
            , @SuppressLint("RecyclerView") int position)
    {
        MyLocation location = DsLocation.get(position);
        String city, quan, xa, DiaChi, name;
        city = location.getCity();
        quan = location.getQuan();
        xa   = location.getXa();
        RadioButton radio = holder.radio;
        String sdt = location.getSdt();
        DiaChi = location.getDiachi();
        name = location.getNamekh();
        holder.DiaChi.setText(DiaChi+", "+xa + ", " + quan +", " + city);
        holder.sdt.setText(sdt);
        holder.nameKH.setText(name);
        radio.setChecked(index == position);
        holder.menu.setOnClickListener(view -> ShowMenu(view, position));
        radio.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });
    }

    private void ShowMenu(View view, int position)
    {
        LocationClick locationClick = this.click;
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu_setting_location);
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.m_Delete)
                {
                    XoaLocation(position);
                }
                else
                {
                    locationClick.clickEdit();
                }
                return false;
            }
        });
    }

    public void XoaLocation(int index)
    {
        InFoStatic.DsLocation.remove(index);
        DatabaseReference put = FirebaseDatabase.getInstance()
                .getReference("Location/"+ InFoStatic.kh.getId());
        put.setValue(DsLocation);
        if(LocationAdapter.index == InFoStatic.DsLocation.size() )
         LocationAdapter.index = LocationAdapter.index - 1;
        setData(DsLocation);
    }
    @Override
    public int getItemCount() {
        if(DsLocation != null)
            return DsLocation.size();
        return 0;
    }

    public static class LHolder extends RecyclerView.ViewHolder
    {
       TextView DiaChi, sdt, nameKH;
       ImageView menu;
       RadioButton radio;
        public LHolder(@NonNull View itemView) {
            super(itemView);
            DiaChi = itemView.findViewById(R.id.tDiaChi);
            nameKH = itemView.findViewById(R.id.nameKhachHang);
            sdt = itemView.findViewById(R.id.phoneNumber);
            radio = itemView.findViewById(R.id.radio_select);
            menu = itemView.findViewById(R.id.menu_location);
        }
    }

}
