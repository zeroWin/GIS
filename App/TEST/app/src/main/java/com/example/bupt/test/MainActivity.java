package com.example.bupt.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        CellLocation cel = mTelephonyManager.getCellLocation();
        int nPhoneType = mTelephonyManager.getPhoneType();
        //移动联通 GsmCellLocation
        if (nPhoneType == 1 && cel instanceof GsmCellLocation) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
            int nGSMCID = gsmCellLocation.getCid();
            int nPsc = gsmCellLocation.getPsc();
            int nmLac = gsmCellLocation.getLac();
            if (nGSMCID > 0) {
                if (nGSMCID != 65535) {
                    System.out.print(nGSMCID);
                }
            }
        }

        List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
        for(NeighboringCellInfo info : infos){
            int cid = info.getCid();

            int lac = info.getLac();
            int type = info.getNetworkType();
            int psc = info.getPsc();

            int Rssi = info.getRssi();
        }


        List<CellInfo>cellInfoList = mTelephonyManager.getAllCellInfo();
        StringBuilder stringBuilder = new StringBuilder("总数 : " + cellInfoList.size() + "\n");

    }
}
