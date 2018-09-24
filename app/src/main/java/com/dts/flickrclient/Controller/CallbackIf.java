package com.dts.flickrclient.Controller;

import java.util.ArrayList;

public interface CallbackIf {
    void onStartLoading();
    void onSucess(String s, ArrayList list);
    void onComplite(ArrayList arrayList);
}
