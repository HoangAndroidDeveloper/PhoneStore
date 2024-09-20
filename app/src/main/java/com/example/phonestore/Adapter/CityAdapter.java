package com.example.phonestore.Adapter;


import static com.example.phonestore.Adapter.CityAdapter.HeaderHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonestore.Activity.SelectLocation;
import com.example.phonestore.OBJ.City;
import com.example.phonestore.R;
import com.google.android.material.tabs.TabLayout;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.security.SecureRandom;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder>
    implements StickyRecyclerHeadersAdapter<HeaderHolder>
{
    List<City> DsCity;
    Context context;
    public static String nCity, nQuan, nXa; // lưu thông tin quận, huyện, xã
    TabLayout tabLayout;
    LocationClick locationClick;
    public static int id = 0, dauCity;
    public interface LocationClick
    {
        public void back(String city,String quan, String xa);
        public void updateLocation(int id);
    }

    public CityAdapter(List<City> dsCity, Context context, TabLayout tabLayout
    ,LocationClick locationClick)
    {
        DsCity = dsCity;
        this.context = context;
        this.tabLayout = tabLayout;
        this.locationClick = locationClick;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city,parent,false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, @SuppressLint("RecyclerView") int position) {
     TextView nameCity = holder.nameCity;
     City city = DsCity.get(position);
     String name = city.getCity();
     int id = city.getCuoi();
     nameCity.setText(name);
     nameCity.setOnClickListener(view -> {

         if(SelectLocation.indexTab == 0)
         {
             dauCity = id;
             nCity = name;
         }
         else if(SelectLocation.indexTab == 1)
         {
             nQuan = name;
         }
         else if(SelectLocation.indexTab == 2)
         {
             nXa = name;
         }
         SelectLocation.indexTab ++;
         CityAdapter.id = id;
         locationClick.updateLocation(id);
         if(SelectLocation.indexTab == 3)
         {
             locationClick.back(nCity,nQuan,nXa);
         }
     });
    }


    @Override
    public long getHeaderId(int position) {
        return DsCity.get(position).getCity().charAt(0);

    }

    @Override
    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_location,parent,false);
        return new HeaderHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(HeaderHolder holder, int position) {
        String H = DsCity.get(position).getCity().substring(0,1);
        holder.header.setText(H);
        holder.header.setBackgroundColor(randomColor());
    }



    @Override
    public int getItemCount() {
        if(DsCity != null)
            return DsCity.size();
        return 0;
    }

    public static class CityHolder extends RecyclerView.ViewHolder
    {
     TextView nameCity;
        public CityHolder(@NonNull View itemView) {
            super(itemView);
            nameCity = itemView.findViewById(R.id.nameCity_item);
        }
    }
    public static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView header;
        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }
   public int randomColor() // random màu nền của header
   {
      SecureRandom src = new SecureRandom();
      return Color.HSVToColor(150, new float[]{
              src.nextInt(359),1,1
      });

   }
}
