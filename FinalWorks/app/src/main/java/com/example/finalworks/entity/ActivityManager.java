package com.example.finalworks.entity;

import android.app.Activity;
import android.app.Application;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 *Class ActivityManager provide a method to kill all the Activities, then exit the program.
 */

public class ActivityManager extends Application {

    /**
     * Member Variable
     * list: provide a collection to contains all Activities.
     *
     * instance: instance of ActivityManager, it can be pass from Activity to Activity.
     *
     * */
    private List<Activity> list = new LinkedList<Activity>();

    private static ActivityManager instance;

    /**
     * Method of get a instance of ActivityManager
     * */

    public synchronized static ActivityManager getInstance(){
        if(instance == null)
            instance = new ActivityManager();
        return instance;
    }



    /**
     * Method of add an Activity to the list while creating a new Activity;
     * @param activity which add to the list;
     * */
    public void add(Activity activity){
        if(activity != null)
            this.list.add(activity);
    }

    /**
     * Method of remove an Activity from the list while finish an Activity
     * @param activity which will be removed from the list;
     * */
    public void remove(Activity activity){
        if(activity != null)
            if(this.list.contains(activity))
                this.list.remove(activity);
    }


    /**
     * Kill the program
     * */
    public void exit(){
        Iterator<Activity> iter = this.list.iterator();
        while(iter.hasNext()){
            Activity activity = iter.next();
            if(activity != null)
                activity.finish();
            iter.remove();
        }
        System.exit(0);
    }


}
