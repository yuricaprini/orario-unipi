package it.unipi.adm.gap.orariounipi;

import java.util.ArrayList;

import android.app.Application;

public class GlobalVariable extends Application 
{
      private ArrayList<String> myState;

      public ArrayList<String> getState()
      {
        return myState;
      }//End method

      public void setState(ArrayList<String> s)
      {
        myState = s;
      }//End method
}//End Class