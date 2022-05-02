package com.example.art.display;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.art.R;
import com.example.art.info.info8;

import java.util.Date;
import java.util.List;

public class displayAdapter extends RecyclerView.Adapter<displayAdapter.ViewHolder> {
    private List<info8> list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        TextView title;
        TextView grade;
        TextView time;
        TextView people;
        TextView location;
        TextView distance;
        TextView pay;

        ImageView grade1;
        ImageView grade2;
        ImageView grade3;
        ImageView grade4;
        ImageView grade5;

        ViewHolder(View view) {
            super(view);
            cover = view.findViewById(R.id.cover);
            title = view.findViewById(R.id.title);
            grade = view.findViewById(R.id.grade);
            time = view.findViewById(R.id.time);
            people = view.findViewById(R.id.people);
            pay = view.findViewById(R.id.pay);
            location = view.findViewById(R.id.location);
            distance = view.findViewById(R.id.distance);

            grade1 = view.findViewById(R.id.grade1);
            grade2 = view.findViewById(R.id.grade2);
            grade3 = view.findViewById(R.id.grade3);
            grade4 = view.findViewById(R.id.grade4);
            grade5 = view.findViewById(R.id.grade5);
        }
    }

    public displayAdapter(List<info8> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.display_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        info8 i = list.get(position);
        holder.cover.setBackground(context.getResources().getDrawable(R.drawable.artappreciation));
        holder.title.setText(i.getTitle());
        holder.grade.setText(i.getGrade() + " points");
        //距离结束还多少时间
        holder.time.setText(getTime_Str(i.getTime()));
        //万人/低于万人
        holder.people.setText(getPeople_Str(i.getPeople()));
        holder.location.setText(i.getLocation());
        holder.pay.setText(i.getPay());
        holder.distance.setText(i.getDistance() + "km");

//        if (i.getGrade() > 0.5) {
//            holder.grade1.setBackground(context.getResources().getDrawable(R.drawable.stars));
//            if (i.getGrade() > 1.5) {
//                holder.grade2.setBackground(context.getResources().getDrawable(R.drawable.stars));
//                if (i.getGrade() > 2.5) {
//                    holder.grade3.setBackground(context.getResources().getDrawable(R.drawable.stars));
//                    if (i.getGrade() > 3.5) {
//                        holder.grade4.setBackground(context.getResources().getDrawable(R.drawable.stars));
//                        if (i.getGrade() > 4.5) {
//                            holder.grade5.setBackground(context.getResources().getDrawable(R.drawable.stars));
//                        } else {
//                            holder.grade5.setBackground(context.getResources().getDrawable(R.drawable.halfstar));
//                        }
//                    } else {
//                        holder.grade4.setBackground(context.getResources().getDrawable(R.drawable.halfstar));
//                    }
//                } else {
//                    holder.grade3.setBackground(context.getResources().getDrawable(R.drawable.halfstar));
//                }
//            } else {
//                holder.grade2.setBackground(context.getResources().getDrawable(R.drawable.halfstar));
//            }
//        } else {
//            holder.grade1.setBackground(context.getResources().getDrawable(R.drawable.halfstar));
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String getTime_Str(Date time) {
        int year = time.getYear() + 1900;
        int month = time.getMonth() + 1;
        int day = time.getDate();
        int hour = time.getHours();
        int minute = time.getMinutes();
        int second = time.getSeconds();
        Date curDate = new Date(System.currentTimeMillis());
        if (curDate.getYear() - year > 0) {
            return "in " + (curDate.getYear() - year) + " year";
        } else {
            if ((curDate.getMonth() + 1) - month > 0) {
                return "in " + ((curDate.getMonth() + 1) - month) + " month";
            } else {
                if (curDate.getDate() - day > 0) {
                    return "in " + (curDate.getDate() - day) + " day";
                } else {
                    if (curDate.getHours() - hour > 0) {
                        return "in " + (curDate.getHours() - hour) + " hour";
                    } else {
                        if (curDate.getMinutes() - minute > 0) {
                            return "in " + (curDate.getMinutes() - minute) + " minute";
                        } else {
                            if (curDate.getSeconds() - second >= 0) {
                                return "in " + (curDate.getSeconds() - second) + " second";
                            } else {
                                return "";
                            }
                        }
                    }
                }
            }
        }
    }

    private String getPeople_Str(int people) {
        if (people > 1000000) {
            return 0.1 + people / 1000000 + "Million Visiting";
        } else if (people > 1000) {
            return 0.1 + people / 1000 + "Thousand Visiting";
        } else {
            return people + " Visiting";
        }
    }
}