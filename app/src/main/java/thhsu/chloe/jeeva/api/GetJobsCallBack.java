package thhsu.chloe.jeeva.api;

import java.util.ArrayList;

import thhsu.chloe.jeeva.api.model.Jobs;

/**
 * Created by Chloe on 5/7/2018.
 */

public interface GetJobsCallBack {
     void onCompleted(ArrayList<Jobs> jobs);
     void onError(String errorMessage);
}
