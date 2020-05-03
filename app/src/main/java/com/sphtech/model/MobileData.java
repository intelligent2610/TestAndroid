package com.sphtech.model;

import android.net.ParseException;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MobileData implements Serializable {

    @SerializedName("records")
    private List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public void filterRange(int bottom, int top) {
        try {
            List<Record> newRecords = new ArrayList<>();
            for (Record record : records) {
                int year = (Integer.parseInt(record.getQuarter().split("-")[0]));
                if (year >= bottom && year <= top) {
                    record.setYear(year);
                    newRecords.add(record);
                }
            }
            records.clear();
            records.addAll(newRecords);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

    public void filterDecrease() {
        if (records == null || records.size() == 0) {
            return;
        }
        try {
            List<Record> newRecords = new ArrayList<>();
            int prefix = records.get(0).getYear();
            float volumeThreshHold = records.get(0).volume_of_mobile_data;

            Record recordTemp = new Record();
            recordTemp.setYear(prefix);

            for (Record record : records) {
                if (record.year == prefix) {
                    float vl = record.getVolume_of_mobile_data();
                    recordTemp.setDecrease(recordTemp.isDecrease() || volumeThreshHold > vl);
                    recordTemp.setVolume_of_mobile_data(vl + recordTemp.volume_of_mobile_data);
                    volumeThreshHold = vl;
                } else {
                    prefix = record.year;
                    volumeThreshHold = record.volume_of_mobile_data;

                    newRecords.add(recordTemp);

                    recordTemp = new Record();
                    recordTemp.setVolume_of_mobile_data(volumeThreshHold);
                    recordTemp.setYear(prefix);
                }
            }
            newRecords.add(recordTemp);
            records.clear();
            records.addAll(newRecords);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    public class Record {

        @SerializedName("volume_of_mobile_data")
        private float volume_of_mobile_data;

        private boolean decrease = false;

        private int year = 0;

        @SerializedName("quarter")
        private String quarter;


        @SerializedName("_id")
        private String _id;

        public float getVolume_of_mobile_data() {
            return volume_of_mobile_data;
        }

        public void setVolume_of_mobile_data(float volume_of_mobile_data) {
            this.volume_of_mobile_data = volume_of_mobile_data;
        }

        public String getQuarter() {
            return quarter;
        }

        public void setQuarter(String quarter) {
            this.quarter = quarter;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public boolean isDecrease() {
            return decrease;
        }

        void setDecrease(boolean decrease) {
            this.decrease = decrease;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}
