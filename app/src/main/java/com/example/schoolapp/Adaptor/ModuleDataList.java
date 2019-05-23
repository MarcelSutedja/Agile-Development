package com.example.schoolapp.Adaptor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schoolapp.Extra.ModuleData;
import com.example.schoolapp.R;

import java.util.List;

public class ModuleDataList extends ArrayAdapter<ModuleData> {

    private Activity context;
    private List<ModuleData> moduleDataList;

    public ModuleDataList(Activity context,List<ModuleData> moduleDataList){
        super(context, R.layout.module_data_list_layout, moduleDataList);
        this.context = context;
        this.moduleDataList = moduleDataList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.module_data_list_layout,null,true);

        ModuleData moduleData = moduleDataList.get(position);

        TextView textViewModuleName = (TextView) listViewItem.findViewById(R.id.textViewModuleName);
        TextView textViewExamGrade = (TextView) listViewItem.findViewById(R.id.textViewExamGrade);
        TextView textViewCourseWorkGrade = (TextView) listViewItem.findViewById(R.id.textViewCourseWorkGrade);
        TextView textViewExamPercentage = (TextView) listViewItem.findViewById(R.id.textViewExamPercentage);
        TextView textViewModuleCredit = (TextView) listViewItem.findViewById(R.id.textViewModuleCredit);
        TextView textViewMark = (TextView) listViewItem.findViewById(R.id.textViewMark);
        textViewModuleName.setText(moduleData.getModuleName());
        textViewExamGrade.setText("Exam Grade                 :   "+String.valueOf(moduleData.getExamGrade()));
        textViewCourseWorkGrade.setText("Coursework Grade      :   " + String.valueOf(moduleData.getCwGrade()));
        textViewExamPercentage.setText("Exam %                         :   " +String.valueOf(moduleData.getExamPercentage()));
        textViewModuleCredit.setText("Module Credit              :   " + String.valueOf(moduleData.getCreditModule()));
        textViewMark.setText("Mark                              :  "+String.valueOf(moduleData.getFinalMark()));

        return listViewItem;
    }
}
